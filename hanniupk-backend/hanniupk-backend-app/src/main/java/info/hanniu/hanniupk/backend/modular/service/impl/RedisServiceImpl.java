package info.hanniu.hanniupk.backend.modular.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import info.hanniu.hanniupk.backend.core.constant.RedisKeyConstant;
import info.hanniu.hanniupk.backend.core.enums.ShareStatusEnum;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.vo.RoomVO;
import info.hanniu.hanniupk.backend.util.RedisKeyGenerateUtil;
import info.hanniu.hanniupk.kernel.model.dto.AnswerDTO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: RedisServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/12 14:21
 * @Version: 1.0
 */
@Service
public class RedisServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setSessionId(String key, String value) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(key);
        opts.set(value, RedisKeyConstant.DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * 缓存单个pk房间的问题
     *
     * @param roomId
     * @param questionsMap
     */
    public void setRoomQuestions(String roomId, Map<String, QuestionDTO> questionsMap) {
        Map<Integer, QuestionDTO> qMap = new HashMap<>();
        questionsMap.forEach((key, value) -> {
            qMap.put(Integer.parseInt(key), value);
        });
        this.putRoomQuestionRedis(roomId, qMap);
    }

    public void putRoomQuestionRedis(String roomId, Map<Integer, QuestionDTO> questionsMap) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(createRoomQuestionsKey(roomId));
        opts.set(questionsMap, RedisKeyConstant.DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * 缓存闯关模式，玩家所分配的选题，选题缓存时长为一个小时
     *
     * @param userId
     * @param questionDTOMap
     */
    public void putPassCustomsQuestionRedis(Integer userId, Map<Integer, QuestionDTO> questionDTOMap) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createPassCustomsQuestionKey(userId));
        opts.set(questionDTOMap, RedisKeyConstant.DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    public QuestionDTO getRoomQuestionByQuestionNumber(String roomId, Integer questionNumber) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(createRoomQuestionsKey(roomId));
        Map<Integer, QuestionDTO> questionMap = (Map<Integer, QuestionDTO>) opts.get();
        return questionMap.get(questionNumber);
    }

    /**
     * pk模式，下一题
     *
     * @param roomId
     * @param questionId
     * @return
     */
    public QuestionDTO getRoomNextQuestionByQuestionNumber(String roomId, Integer questionId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(createRoomQuestionsKey(roomId));
        LinkedHashMap<Integer, QuestionDTO> questionMap = (LinkedHashMap<Integer, QuestionDTO>) opts.get();
        return getNextQuestion(questionMap, questionId);
    }

    /**
     * 闯关模式，查询玩家分配的选题
     *
     * @param userId
     * @return
     */
    public LinkedHashMap<Integer, QuestionDTO> getPassCustomsQuestionByUserId(Integer userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createPassCustomsQuestionKey(userId));
        if (null == opts.get()) {
            return null;
        }
        return (LinkedHashMap<Integer, QuestionDTO>) opts.get();
    }

    /**
     * 闯关模式，删除玩家分配的选题
     *
     * @param userId
     */
    public void deletePassCustomsQuestion(Integer userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createPassCustomsQuestionKey(userId));
        RedisOperations<String, Object> operations = opts.getOperations();
        operations.delete(RedisKeyGenerateUtil.createPassCustomsQuestionKey(userId));
    }

    /**
     * 遴选下一题
     *
     * @param questionMap
     * @param questionId
     * @return
     */
    public QuestionDTO getNextQuestion(LinkedHashMap<Integer, QuestionDTO> questionMap, Integer questionId) {
        if (null == questionMap || questionMap.size() <= 0) {
            return null;
        }
        Set<Integer> set = questionMap.keySet();
        Object[] keys = set.toArray();
        for (int i = 0; i < keys.length; i++) {
            if (null == questionId) {
                logger.info("------>下次pk问题id:<------{}", keys[i]);
                return questionMap.get(keys[i]);
            }
            if (questionId.equals(keys[keys.length - 1])) {
                return null;
            }
            if (questionId.equals(keys[i])) {
                logger.info("------>下次pk问题id:<------{}", keys[i + 1]);
                return questionMap.get(keys[i + 1]);
            }
        }
        return null;
    }

    /**
     * @return java.lang.String
     * @Author zhanglj
     * @Description 获取正确答案内容
     * @Date 2018/10/28 8:50 PM
     * @Param [questionNumber, roomId]
     **/
    public String getRightAnswerContent(Integer questionNumber, String roomId) {
        if (null == questionNumber) {
            return "";
        }
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(createRoomQuestionsKey(roomId));
        Map<Integer, QuestionDTO> questionMap = (Map<Integer, QuestionDTO>) opts.get();
        QuestionDTO questionDTO = questionMap.get(questionNumber);
        AnswerDTO answerDTO = questionDTO.getAnswerList().stream().filter(item -> item.getAnswerNumber().equals(questionDTO.getRightAnswerNumber())).collect(Collectors.toList()).get(0);
        logger.info("------>缓存中获取本题内容：{}<-----", answerDTO.getAnswerContent());
        return answerDTO.getAnswerContent();
    }

    public void clearQuestionsByRoomId(String roomId) {
        if (StringUtils.isNotEmpty(createRoomQuestionsKey(roomId))) {
            logger.info("------>删除此次pk的questions缓存<------");
            BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(createRoomQuestionsKey(roomId));
            if (null != opts.get()) {
                RedisOperations<String, Object> operations = opts.getOperations();
                operations.delete(createRoomQuestionsKey(roomId));
            }
        }
    }

    private String createRoomQuestionsKey(String roomId) {
        if (StringUtils.isEmpty(roomId)) {
            return null;
        }
        return RedisKeyConstant.ROOM_QUESTIONS_PREFIX + roomId;
    }

    /**
     * @return java.util.Map<java.lang.Integer       ,       info.hanniu.hanniupk.kernel.model.dto.QuestionDTO>
     * @Author zhanglj
     * @Description 随机获取题
     * @Date 2018/10/15 2:30 PM
     * @Param [number] 数量
     **/
    public Map<Integer, QuestionDTO> getQuestionByRandom(int number) {
        Map<Integer, QuestionDTO> questionDTOMap = new LinkedHashMap<>(number);
        BoundHashOperations<String, Integer, String> boundHashOps = this.redisTemplate.boundHashOps(RedisKeyConstant.QUESTION_CASHE_KEY);
        BoundSetOperations<String, Object> boundSetOps = this.redisTemplate.boundSetOps(RedisKeyConstant.QUESTION_MAP_SET_KEY_CASHE_KEY);
        Set<Integer> keySet = boundSetOps.distinctRandomMembers(number).stream().map(item -> (Integer) item).collect(Collectors.toSet());
        List<String> questionDTOList = boundHashOps.multiGet(keySet);
        for (int i = 0; i < questionDTOList.size(); i++) {
            QuestionDTO questionDTO = JSON.parseObject(questionDTOList.get(i), QuestionDTO.class);
            questionDTO.setQuestionContent(questionDTO.getQuestionContent());
            questionDTOMap.put(questionDTO.getQuestionNumber(), questionDTO);
        }
        return questionDTOMap;
    }

    /**
     * 用roomId获取房间内pk双方的userId
     *
     * @param roomId
     * @return
     */
    public List<Integer> getUserIdsByRoomId(String roomId) {
        BoundHashOperations<String, String, RoomVO> boundHashOps = this.redisTemplate.boundHashOps(RedisKeyGenerateUtil.getRoomKeyByRoomId(roomId));
        RoomVO room = boundHashOps.get(roomId);
        if (null != room) {
            return Arrays.asList(room.getPlayerId1(), room.getPlayerId2());
        }
        return Arrays.asList();
    }

    /**
     * 获取游戏房间
     *
     * @param roomId
     * @return
     */
    public RoomVO getRoomByRoomId(String roomId) {
        BoundHashOperations<String, String, RoomVO> boundHashOps = this.redisTemplate.boundHashOps(RedisKeyGenerateUtil.getRoomKeyByRoomId(roomId));
        return boundHashOps.get(roomId);
    }

    /**
     * pk结束删除pk房间
     *
     * @param roomId
     */
    public void deletePkRoom(String roomId) {
        logger.info("------>删除pk房间.roomId={}<-------", roomId);
        BoundHashOperations<String, String, RoomVO> boundHashOps = this.redisTemplate.boundHashOps(RedisKeyGenerateUtil.getRoomKeyByRoomId(roomId));
        if (null != boundHashOps && null != boundHashOps.get(roomId)) {
            boundHashOps.delete(roomId);
        }
    }

    /**
     * 缓存玩家发起分享的状态
     *
     * @param userId
     */
    public void setShareStatus(String userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createUserShareKey(userId));
        opts.set(ShareStatusEnum.SHARE_NORMAL.getCode(), RedisKeyConstant.SHARE_FRIEND_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * 分享者掉线
     *
     * @param userId
     */
    public void setShareOutLine(String userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createUserShareKey(userId));
        opts.set(ShareStatusEnum.SHARE_OUT_LINE.getCode(), RedisKeyConstant.SHARE_FRIEND_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    public void setShareOnLine(String userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createUserShareKey(userId));
        opts.set(ShareStatusEnum.SHARE_NORMAL.getCode(), RedisKeyConstant.SHARE_FRIEND_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * 发起分享者放弃战斗
     *
     * @param userId
     */
    public void giveUpShare(String userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createUserShareKey(userId));
        opts.set(ShareStatusEnum.SHARE_GIVE_UP.getCode(), RedisKeyConstant.SHARE_FRIEND_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * 分享设置为已经接受pk
     *
     * @param userId
     */
    public void setShareUsed(String userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createUserShareKey(userId));
        opts.set(ShareStatusEnum.SHARE_USED.getCode(), RedisKeyConstant.SHARE_FRIEND_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    /**
     * 获取分享状态
     *
     * @param userId
     * @return
     */
    public Integer getShareStatus(String userId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createUserShareKey(userId));
        if (null != opts.get()) {
            return (Integer) opts.get();
        }
        return ShareStatusEnum.SHARE_TIME_OUT.getCode();
    }

    /**
     * 设置或者修改机器人请求记录
     *
     * @param robotId
     * @param pkRequest
     */
    public void setOrUpdateRobotPkRequestLog(Integer robotId, PKRequestDTO pkRequest) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createRobotAutoAnswerLogKey(robotId));
        opts.set(pkRequest, RedisKeyConstant.SHARE_FRIEND_TIME_OUT_SECS, TimeUnit.SECONDS);
    }

    public void deleteRobotPkRequestLog(Integer robotId) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(RedisKeyGenerateUtil.createRobotAutoAnswerLogKey(robotId));
        RedisOperations<String, Object> operations = opts.getOperations();
        operations.delete(RedisKeyGenerateUtil.createRobotAutoAnswerLogKey(robotId));
    }

    public List<PKRequestDTO> getRobotPkRequestLogs() {
        List<PKRequestDTO> list = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(RedisKeyGenerateUtil.createRobotAutoAnswerFuzzyKey());
        keys.forEach((key) -> {
            BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(key);
            list.add((PKRequestDTO) opts.get());
        });
        return list;
    }

}
