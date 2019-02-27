package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.core.constant.IntegralConstant;
import info.hanniu.hanniupk.backend.core.constant.ThreadContant;
import info.hanniu.hanniupk.backend.core.enums.AnswerEnum;
import info.hanniu.hanniupk.backend.core.enums.PkResponseVOEnum;
import info.hanniu.hanniupk.backend.core.enums.SocketResponseTypeEnum;
import info.hanniu.hanniupk.backend.core.enums.WinnerEnum;
import info.hanniu.hanniupk.backend.core.variable.CyclicBarrierMap;
import info.hanniu.hanniupk.backend.core.variable.PkCacheMap;
import info.hanniu.hanniupk.backend.core.variable.WebSocketMap;
import info.hanniu.hanniupk.backend.modular.consumer.SysUserServiceConsumer;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.entity.PlayerGrade;
import info.hanniu.hanniupk.backend.modular.service.IThreadTaskService;
import info.hanniu.hanniupk.backend.modular.vo.PKResponseVO;
import info.hanniu.hanniupk.backend.modular.vo.RoomVO;
import info.hanniu.hanniupk.backend.socket.MessageWebSocket;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.model.dto.PlayerResultDTO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: PkScoketService
 * @Author: tao
 * @Description: 1、根据roomId查询redis，获取题库，每次返回一条。
 * 2、根据出题编号返回正确答案
 * 3、根据出题编号和用户给出的答案，返回用户是否回答正确
 * 4、用户积分或者金币等的核算（正确性，时间等）
 * @Date: 2018/10/9 16:26
 * @Version: 1.0
 */
@Service
public class PkSocketServiceImpl<T, V> implements IThreadTaskService<T, V> {
    private static Logger logger = LoggerFactory.getLogger(PkSocketServiceImpl.class);

    @Autowired
    private RedisServiceImpl iRedisService;
    @Autowired
    private UserPlayerServiceImpl iUserPlayerService;
    @Autowired
    private PlayerGradeServiceImpl iPlayerGradeService;

    /**
     * 双方是否提交了第一题的请求
     *
     * @param roomId
     * @return
     */
    public Boolean isGetFirstQuestion(String roomId, Integer questionIndex) {
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(roomId);
        logger.info("--->isGetFirstQuestion,userIds={}<---",userIds);
        if (null != userIds && userIds.size() > 0) {
            Boolean result = true;
            for (Integer id : userIds) {
                if (null == PkCacheMap.getPkRequestForFirstQuestion(id, questionIndex)) {
                    result = false;
                }
            }
            return result;
        }
        return false;
    }

    /**
     * 双方是否提交了2-n题的请求
     *
     * @param roomId
     * @return
     */
    public Boolean isGetOtherQuestion(String roomId, Integer questionIndex) {
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(roomId);
        logger.info("--->isGetOtherQuestion,userIds={}<---",userIds);
        if (null != userIds && userIds.size() > 0) {
            boolean result = true;
            for (Integer id : userIds) {
                if (null == PkCacheMap.getPkRequestForOtherQuestion(id, questionIndex)) {
                    result = false;
                }
            }
            return result;
        }
        return false;
    }

    @Override
    public V run(T t) {
        PKRequestDTO pkRequest = (PKRequestDTO) t;
        logger.info("---->pk 请求参数：{}<----", pkRequest.toString());
        PkCacheMap.setRoomQuestionIndex(pkRequest.getRoomId(),pkRequest.getQuestionIndex());
        saveCyclicBarrier(pkRequest);
        if (isRequestFirstQuestion(pkRequest.getQuestionId())) {
            setFirstQuestionRequestRedis(pkRequest);
        } else {
            setOtherQuestionsRequestAndResultRedis(pkRequest);
        }
        return null;
    }

    /**
     * 保存CyclicBarrier
     *
     * @param pkRequest
     */
    private void saveCyclicBarrier(PKRequestDTO pkRequest) {
        Lock lock = PkCacheMap.getLockByRoomId(pkRequest.getRoomId());
        // 获取锁
        lock.lock();
        try {
            CyclicBarrier cyclicBarrier = CyclicBarrierMap.cyclicBarrierMap.get(pkRequest.getRoomId());
            if (null == cyclicBarrier) {
                logger.info("------>创建新的cyclicBarrier并保存进cyclicBarrierMap中<------");
                CyclicBarrierMap.cyclicBarrierMap.put(pkRequest.getRoomId(),
                        createCyclicBarrier(pkRequest.getUserId(),
                                pkRequest.getRoomId()));
            } else {
                if (cyclicBarrier.isBroken()) {
                    cyclicBarrier.reset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private CyclicBarrier createCyclicBarrier(Integer userId, String roomId) {
        logger.info("------>messageWebSocket:创建一个CyclicBarrier<------");
        return new CyclicBarrier(ThreadContant.PK_THREAD_PARTIES, () -> {
            Integer questionIndex = PkCacheMap.getRoomQuestionIndex(roomId);
            if (this.isGetFirstQuestion(roomId, questionIndex)) {
                logger.info("------>CyclicBarrier,runnable;处理请求第一题公共业务：<------");
                this.setFirstQuestionResult(PkCacheMap.getPkRequestForFirstQuestion(userId, questionIndex));
            } else if (this.isGetOtherQuestion(roomId, questionIndex)) {
                logger.info("------>CyclicBarrier,runnable;处理请求第2-n题公共业务：<------");
                this.setOtherQuestionsResult(PkCacheMap.getPkRequestForOtherQuestion(userId, questionIndex));
            } else {
                logger.error("！！！！发生错误，没有符合的条件");
            }
        });
    }

    /**
     * 是否在请求第一题
     *
     * @param questionId
     * @return
     */
    private boolean isRequestFirstQuestion(Integer questionId) {
        return null == questionId;
    }

    /**
     * 缓存第一题的请求参数，并且线程等待
     *
     * @param pkRequest
     */
    private void setFirstQuestionRequestRedis(PKRequestDTO pkRequest) {
        PkCacheMap.setFirstQuestionGetSign(pkRequest);
        cyclicBarrierWait(pkRequest.getRoomId(), pkRequest.getUserId(), pkRequest.getQuestionIndex(),pkRequest.getQuestionId());
        ResponseData responseData = PkCacheMap.getPkResponseData(pkRequest.getRoomId(), pkRequest.getUserId(),pkRequest.getQuestionIndex());
        if (null != responseData) {
            this.sendSingleMessage(responseData, pkRequest.getUserId());
        }
        PkCacheMap.deleteFirstQuestionGetSign(pkRequest.getUserId(), pkRequest.getQuestionIndex());
        PkCacheMap.deletePkResponseData(pkRequest.getRoomId(),pkRequest.getUserId() ,pkRequest.getQuestionIndex());
    }

    /**
     * 缓存2-n题的请求参数和答题结果，并且线程等待
     *
     * @param pkRequest
     */
    private void setOtherQuestionsRequestAndResultRedis(PKRequestDTO pkRequest) {
        PlayerResultDTO playerResult = getAnswerResult(pkRequest);
        PkCacheMap.setPlayerResultByUserId(pkRequest.getUserId(), pkRequest.getQuestionIndex(), playerResult);
        PkCacheMap.setOtherQuestionGetSign(pkRequest);
        cyclicBarrierWait(pkRequest.getRoomId(), pkRequest.getUserId(), pkRequest.getQuestionIndex(),pkRequest.getQuestionId());
        ResponseData responseData = PkCacheMap.getPkResponseData(pkRequest.getRoomId(), pkRequest.getUserId(),pkRequest.getQuestionIndex());
        if (null != responseData) {
            this.sendSingleMessage(responseData, pkRequest.getUserId());
        }
        PkCacheMap.deletePlayerResultByUserId(pkRequest.getUserId(), pkRequest.getQuestionIndex());
        PkCacheMap.deleteOtherQuestionGetSign(pkRequest.getUserId(), pkRequest.getQuestionIndex());
        PkCacheMap.deletePkResponseData(pkRequest.getRoomId(),pkRequest.getUserId() ,pkRequest.getQuestionIndex());
    }

    /**
     * 向小程序端发送第一个问题消息
     *
     * @param pkRequest
     */
    public void setFirstQuestionResult(PKRequestDTO pkRequest) {
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(pkRequest.getRoomId());
        userIds.forEach((userId)->{
            PkCacheMap.setPkResponseData(pkRequest.getRoomId(),userId,
                    pkRequest.getQuestionIndex(),
                    getFirstQuestion(pkRequest.getRoomId(),pkRequest.getQuestionId()));
        });

    }

    /**
     * 获取第一题内容并封装到responseData中
     * @param roomId
     * @param questionId
     * @return
     */
    public ResponseData getFirstQuestion(String roomId,Integer questionId){
        QuestionDTO question = getNextQuestion(roomId, questionId);
        PKResponseVO pkResponse = new PKResponseVO(Arrays.asList(), question, isHasNextQuestion(question));
        pkResponse.setResultCode(PkResponseVOEnum.RESULT_CODE_COMMON.getCode());
        return ResponseData.success(createPkResponse(pkResponse));
    }

    /**
     * 封装pk过程返回结果
     * @param pkResponseVO
     * @return
     */
    private PKResponseVO createPkResponse(PKResponseVO pkResponseVO){
       pkResponseVO.setType(SocketResponseTypeEnum.PK_RESPONSE.getCode());
       return pkResponseVO;
    }

    /**
     * 向小程序端发送2-n题消息
     *
     * @param pkRequest
     * @return
     */
    public void setOtherQuestionsResult(PKRequestDTO pkRequest) {
        QuestionDTO question = getNextQuestion(pkRequest.getRoomId(), pkRequest.getQuestionId());
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(pkRequest.getRoomId());
        PKResponseVO pkResponse = new PKResponseVO(getPlayersResult(pkRequest.getRoomId(), pkRequest.getQuestionIndex()),
                question,
                isHasNextQuestion(question),
                iRedisService.getRightAnswerContent(pkRequest.getQuestionId(), pkRequest.getRoomId()));
        settlement(pkResponse, pkRequest.getRoomId());
        pkResponse.setResultCode(PkResponseVOEnum.RESULT_CODE_COMMON.getCode());
        userIds.forEach((userId)->{
            PkCacheMap.setPkResponseData(pkRequest.getRoomId(),userId,
                    pkRequest.getQuestionIndex(),
                    ResponseData.success(createPkResponse(pkResponse)));
        });
    }

    /**
     * 线程等待
     *
     * @param roomId
     */
    private void cyclicBarrierWait(String roomId, Integer userId, Integer questionIndex,Integer questionNumber) {
        try {
            logger.info("------>wait(),roomId={}, userId={}, questionIndex={}, questionNumber={}<-----", roomId,userId, questionIndex, questionNumber);
            CyclicBarrierMap.cyclicBarrierMap.get(roomId).await(CyclicBarrierMap.timeOut, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.info("------>roomId={}线程中断,message:{}<------", roomId, e.getMessage());
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            logger.info("------>barrier损坏，roomId={},message:{}<------", roomId, e.getMessage());
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.info("------>wait超时，roomId={},message:{}<------", roomId, e.getMessage());
            e.printStackTrace();
            timeOutResult(roomId, userId, questionIndex,questionNumber);
        }


    }

    /**
     * @param pkRequest
     * @Method getMyAnswerResult
     * @Author tao
     * @Version 1.0
     * @Description 计算提交答案的用户的答题结果
     * @Return info.hanniu.hanniupk.backend.modular.dto.PlayerResultDTO
     * @Exception
     * @Date 2018/10/11 17:22
     */
    private PlayerResultDTO getAnswerResult(PKRequestDTO pkRequest) {
        Integer result = checkAnswer(pkRequest.getRoomId(), pkRequest.getQuestionId(), pkRequest.getQuestionAnswerId());
        Integer integral = getIntegral(result, pkRequest.getUseTime(), pkRequest.getRoomId(), pkRequest.getUserId());
        return new PlayerResultDTO(integral.toString(), pkRequest.getUserId(), result);
    }

    /**
     * @param roomId
     * @param questionNumber
     * @Method getNextQuestion
     * @Author tao
     * @Version 1.0
     * @Description 于redis中获取下一个问题
     * @Return info.hanniu.hanniupk.backend.modular.dto.QuestionDTO
     * @Exception
     * @Date 2018/10/11 17:25
     */
    private QuestionDTO getNextQuestion(String roomId, Integer questionNumber) {
        return iRedisService.getRoomNextQuestionByQuestionNumber(roomId, questionNumber);
    }

    /**
     * 向小程序发送消息
     * @param object
     * @param userId
     */
    public void sendSingleMessage(Object object, Integer userId) {
        MessageWebSocket messageWebSocket = WebSocketMap.webSocketMap.get(userId);
        if (null != messageWebSocket) {
            try {
                logger.info("------>向userId==>{}<==发送消息，内容：==>{}<==<------", userId, object.toString());
                messageWebSocket.session.getBasicRemote().sendObject(object);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("------>userId={},未能获取到messageWebSocket,发送消息失败！！!<------");
        }


    }

    /**
     * pk 结束进行结算，积分、等级和牛币实例化到数据库中
     *
     * @param pkResponse
     */
    private void settlement(PKResponseVO pkResponse, String roomId) {
        if (pkResponse.getHasNextQuestion().equals(AnswerEnum.ANSWER_RIGHT.getCode())) {
            return;
        }
        List<PlayerResultDTO> playerResults = pkResponse.getPlayerResults();
        playerResults = setWinner(playerResults);
        for (PlayerResultDTO playerResult : playerResults) {
            updatePlayerInfo(playerResult);
        }
        clearRedisQuestions(roomId);
        clearRedisPlayerIntegralPkIng(roomId, playerResults);
        PkCacheMap.deleteRoomQuestionIndex(roomId);
        deleteCyclicBarrierByRoomId(roomId);
        deletePkRoom(roomId, iRedisService);
        PkCacheMap.deleteLockByRoomId(roomId);
    }

    /**
     * 清除pk房间
     *
     * @param roomId
     */
    private void deletePkRoom(String roomId, RedisServiceImpl redisService) {
        redisService.deletePkRoom(roomId);
    }

    /**
     * 删除CyclicBarrier
     *
     * @param roomId
     */
    private void deleteCyclicBarrierByRoomId(String roomId) {
        logger.info("------>删除CyclicBarrier<------");
        if (null != CyclicBarrierMap.cyclicBarrierMap.get(roomId)) {
            CyclicBarrierMap.cyclicBarrierMap.remove(roomId);
        }
    }
    /**
     * 指定胜利玩家
     *
     * @param playerResults
     * @return
     */
    private List<PlayerResultDTO> setWinner(List<PlayerResultDTO> playerResults) {
        if (playerResults.size() < PkCacheMap.PK_PLAYERS_SIZE ) {
            for (PlayerResultDTO playerResult : playerResults) {
                playerResult.setWinner(WinnerEnum.win.getCode());
                playerResult.setCoin(IntegralConstant.REWARD_OF_COIN);
            }
            return playerResults;
        }
        if (isIntegralEquals(playerResults)) {
            for (PlayerResultDTO playerResult : playerResults) {
                playerResult.setWinner(WinnerEnum.draw.getCode());
                playerResult.setCoin(IntegralConstant.REWARD_OF_COIN);
            }
            return playerResults;
        }
        Integer winUserId = getWinUser(playerResults);
        for (PlayerResultDTO playerResult : playerResults) {
            if (winUserId.equals(playerResult.getUserId())) {
                playerResult.setWinner(WinnerEnum.win.getCode());
                playerResult.setCoin(IntegralConstant.REWARD_OF_COIN);
            } else {
                playerResult.setWinner(WinnerEnum.lose.getCode());
                playerResult.setCoin(IntegralConstant.LOSER_OF_COIN);
            }
        }
        return playerResults;
    }

    /**
     * 此次pk得到的积分是否相等
     *
     * @param playerResults
     * @return
     */
    private boolean isIntegralEquals(List<PlayerResultDTO> playerResults) {
        logger.info("------>isIntegralEquals,playerResult={}<------", playerResults.toString());
        if (null == playerResults || playerResults.size() < PkCacheMap.PK_PLAYERS_SIZE) {
            return false;
        }
        return Integer.valueOf(playerResults.get(0).getIntegral()).equals(Integer.valueOf(playerResults.get(1).getIntegral())) ? true : false;
    }

    /**
     * 获取取胜玩家id
     *
     * @param playerResults
     * @return
     */
    private Integer getWinUser(List<PlayerResultDTO> playerResults) {
        return Integer.valueOf(playerResults.get(0).getIntegral()) > (Integer.valueOf(playerResults.get(1).getIntegral())) ?
                playerResults.get(0).getUserId() : playerResults.get(1).getUserId();
    }

    /**
     * 清除房间问题缓存
     *
     * @param roomId
     */
    private void clearRedisQuestions(String roomId) {
        iRedisService.clearQuestionsByRoomId(roomId);
    }

    /**
     * 清除redis中缓存的双方玩家此次pk的当前积分
     *
     * @param roomId
     * @param playerResults
     */
    private void clearRedisPlayerIntegralPkIng(String roomId, List<PlayerResultDTO> playerResults) {
        for (PlayerResultDTO playerResult : playerResults) {
            PkCacheMap.deletePlayerIntegralPkIng(roomId, playerResult.getUserId());
        }
    }

    /**
     * 获取缓存中的双方玩家的答题结果
     *
     * @param roomId
     * @return
     */
    public List<PlayerResultDTO> getPlayersResult(String roomId, Integer questionIndex) {
        List<PlayerResultDTO> playerResults = new ArrayList<>();
        List<Integer> ids = iRedisService.getUserIdsByRoomId(roomId);
        ids.forEach((id) -> {
            PlayerResultDTO playerResultDTO = PkCacheMap.getPlayerResultByUserId(id, questionIndex);
            if (null != playerResultDTO) {
                playerResults.add(playerResultDTO);
            }
        });
        logger.info("------>缓存中获取玩家本次答题结果：{}<------", playerResults.toString());
        return playerResults;
    }

    public void updatePlayerInfo(PlayerResultDTO playerResult) {
        UserPlayer oldUserPlayer = iUserPlayerService.selectById(playerResult.getUserId());
        UserPlayer userPlayer = new UserPlayer();
        userPlayer.setId(playerResult.getUserId());
        userPlayer.setExperience(getPlayerNewExperience(oldUserPlayer.getExperience(), playerResult.getIntegral()));
        userPlayer.setCoin(getPlayerNewCoin(oldUserPlayer, playerResult));
        if (isGradeCodeChange(oldUserPlayer.getGradeCode(), getPlayerNewGradeCode(oldUserPlayer.getExperience(), playerResult.getIntegral()))) {
            userPlayer.setGradeCode(getPlayerNewGradeCode(oldUserPlayer.getExperience(), playerResult.getIntegral()));
        }
        this.iUserPlayerService.saveOrUpdate(userPlayer);
    }

    private boolean isGradeCodeChange(Integer gradeCode, Integer playerNewGradeCode) {
        return gradeCode.equals(playerNewGradeCode) ? false : true;
    }

    private Integer getPlayerNewGradeCode(Integer experience, String integral) {
        return getGradeCode(getPlayerNewExperience(experience, integral));
    }

    private Integer getGradeCode(Integer experience) {
        List<PlayerGrade> list = this.iPlayerGradeService.selectList();
        if (null != list) {
            for (PlayerGrade playerGrade : list) {
                if (experience >= playerGrade.getStartExperience() && experience < playerGrade.getEndExperience()) {
                    return playerGrade.getGradeCode();
                }
            }
        }
        return null;
    }

    private Integer getPlayerNewCoin(UserPlayer oldUserPlayer, PlayerResultDTO playerResult) {
        return oldUserPlayer.getCoin() + playerResult.getCoin();
    }

    private Integer getPlayerNewExperience(Integer experience, String integral) {
        if (null == integral) {
            return experience;
        }
        return experience + Integer.valueOf(integral);
    }

    /**
     * @param question
     * @Method isHasNextQuestion
     * @Author tao
     * @Version 1.0
     * @Description 是否有新问题
     * @Return java.lang.Boolean
     * @Exception
     * @Date 2018/10/11 17:32
     */
    public Integer isHasNextQuestion(QuestionDTO question) {
        return null == question ? AnswerEnum.ANSWER_FALSE.getCode() : AnswerEnum.ANSWER_RIGHT.getCode();
    }

    /**
     * @param result
     * @param useTime
     * @Method getIntegral
     * @Author tao
     * @Version 1.0
     * @Description 计算积分
     * @Return java.lang.String
     * @Exception
     * @Date 2018/10/11 16:36
     */
    private Integer getIntegral(Integer result, Integer useTime, String roomId, Integer userId) {
        String currentIntegral = PkCacheMap.getPlayerIntegralPkIng(roomId, userId);
        if (null == currentIntegral || "".equals(currentIntegral)) {
            currentIntegral = IntegralConstant.PK_ING_INTEGRAL_INITIAL + "";
        }
        if (null != result && AnswerEnum.ANSWER_RIGHT.getCode().equals(result)) {
            Integer countIntegral = countIntegral(useTime, currentIntegral);
            PkCacheMap.setPlayerIntegralPkIng(roomId, userId, countIntegral.toString());
            return countIntegral;
        } else {
            return Integer.valueOf(currentIntegral);
        }
    }

    /**
     * 答题正确计算积分
     *
     * @param useTime
     * @param currentIntegral
     * @return
     */
    private Integer countIntegral(Integer useTime, String currentIntegral) {
        return (IntegralConstant.INIT_COUNT_SCORE / 10)
                * ((IntegralConstant.TOTAL_COUNT_TIME - useTime))
                + Integer.valueOf(currentIntegral);
    }

    /**
     * 合适答案是否正确
     *
     * @param roomId
     * @param questionNumber
     * @param questionAnswerNumber
     * @return
     */
    private Integer checkAnswer(String roomId, Integer questionNumber, String questionAnswerNumber) {
        if (null == questionAnswerNumber || "".equals(questionAnswerNumber)) {
            return AnswerEnum.ANSWER_FALSE.getCode();
        }
        QuestionDTO question = iRedisService.getRoomQuestionByQuestionNumber(roomId, questionNumber);
        if (questionAnswerNumber.equals(question.getRightAnswerNumber())) {
            return AnswerEnum.ANSWER_RIGHT.getCode();
        }
        return AnswerEnum.ANSWER_FALSE.getCode();
    }

    /**
     * 超时，直接给正常的玩家返回获胜结果
     *
     * @param roomId
     * @param userId
     * @param questionIndex
     */
    public void timeOutResult(String roomId, Integer userId, Integer questionIndex , Integer questionNumber) {
        logger.info("--->pk异常超时,roomId={},userId={},questionIndex={}<---",roomId,userId,questionIndex);
        PKResponseVO pkResponse = new PKResponseVO(Arrays.asList(getSelfPlayerResultWhenTimeOut(userId, questionIndex)),
                null,
                isHasNextQuestion(null),
                iRedisService.getRightAnswerContent(questionNumber,roomId));
        settlement(pkResponse, roomId);
        pkResponse.setResultCode(PkResponseVOEnum.RESULT_CODE_OPPONENT_GIVE_UP.getCode());
        this.sendSingleMessage(ResponseData.success(createPkResponse(pkResponse)), userId);
        clearOtherPlayerRedisTimeOut(getOtherUserId(userId, roomId), questionIndex,roomId);
    }

    private PlayerResultDTO getSelfPlayerResultWhenTimeOut(Integer userId, Integer questionIndex) {
        PlayerResultDTO playerResult = PkCacheMap.getPlayerResultByUserId(userId, questionIndex);
        if (null == playerResult) {
            playerResult = new PlayerResultDTO();
            playerResult.setUserId(userId);
        }
        return playerResult;
    }

    /**
     * 清除对手在reids中缓存的数据
     *
     * @param userId        对手的id
     * @param questionIndex
     */
    private void clearOtherPlayerRedisTimeOut(Integer userId, Integer questionIndex,String roomId) {
        PkCacheMap.deleteFirstQuestionGetSign(userId, questionIndex);
        PkCacheMap.deleteOtherQuestionGetSign(userId, questionIndex);
        PkCacheMap.deletePlayerResultByUserId(userId, questionIndex);
        PkCacheMap.deleteRoomQuestionIndex(roomId);
        PkCacheMap.deleteLockByRoomId(roomId);
    }

    /**
     * 查询对手 userId by userId
     *
     * @param userId
     * @param roomId
     * @return
     */
    private Integer getOtherUserId(Integer userId, String roomId) {
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(roomId);
        if (null != userIds && userIds.size() > 0) {
            for (Integer id : userIds) {
                if (!id.equals(userId)) {
                    return id;
                }
            }
        }
        return null;
    }


    /**
     * 定时任务-是否在第一次请求时断开连接，
     * 此时没有人答题，但依然判断正常游戏玩家获胜
     *
     * @param roomId
     * @return
     */
    private Boolean isCutOffWhenFirstRequest(String roomId, Integer questionIndex) {
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(roomId);
        Boolean result = false;
        if (null != userIds && userIds.size() > 0) {
            for (Integer id : userIds) {
                if (null != PkCacheMap.getPkRequestForFirstQuestion(id, questionIndex)) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 定时任务-是否在第2-n次请求时断开，
     * 此时为断开的玩家已经提交答题结果，
     * 可以根据此结果返回响应消息
     *
     * @param roomId
     * @return
     */
    private Boolean isCutOffWhenOtherRequest(String roomId, Integer questionIndex) {
        List<Integer> userIds = iRedisService.getUserIdsByRoomId(roomId);
        boolean result = false;
        if (null != userIds && userIds.size() > 0) {
            for (Integer id : userIds) {
                if (null != PkCacheMap.getPkRequestForOtherQuestion(id, questionIndex)) {
                    result = true;
                }
            }
        }
        return result;
    }
}