package info.hanniu.hanniupk.backend.tasks;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import info.hanniu.hanniupk.backend.core.variable.PkCacheMap;
import info.hanniu.hanniupk.backend.modular.consumer.SysUserServiceConsumer;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.service.impl.AsyncServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.PkSocketServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.RedisServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.RobotServiceImpl;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import info.hanniu.hanniupk.kernel.model.dto.AnswerDTO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.tasks
 * @ClassName: RobotAutoRequestTask
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/21 16:59
 * @Version: 1.0
 */
@Component
public class RobotAutoRequestTask {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatScheduledTask.class);
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private AsyncServiceImpl asyncService;
    @Autowired
    private PkSocketServiceImpl pkSocketService;
    @Autowired
    private RobotServiceImpl robotService;
    @Autowired
    private SysUserServiceConsumer sysUserServiceConsumer;

    private static final Long TIME_OUT = 30000L;

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime(){
        List<PKRequestDTO> lists = redisService.getRobotPkRequestLogs();
        if (null != lists && lists.size() > 0) {
            lists.forEach(this::task);
        }
    }
    private void task(PKRequestDTO item){
        try {
            if (isTimeToSendRequest(item.getActiveTime(),item.getUserId(),item.getRoomId(),item.getQuestionIndex())) {
                logger.info("机器人请求开始：robotId={}",item.getUserId());
                asyncService.getDeferredResult(this.pkSocketService, item);
                setNextRequest(item);
            } else if (isInitRobot(item.getActiveTime())) {
                initRobot(item.getUserId());
            }
        } catch (Exception e) {
            LogUtil.info("--->机器人定时器，task发生错误！<---");
            e.printStackTrace();
        }
    }

    /**
     * 若当前时间与记录执行时间差值大于3分钟，认为无效数据，清理
     * @param activeTime
     * @return
     */
    private boolean isInitRobot(Date activeTime) {
        return System.currentTimeMillis() - activeTime.getTime() > TIME_OUT;
    }

    /**
     * 获取真实玩家的id
     * @param roomId
     * @param robotId
     * @return
     */
    private Integer getTruePlayerId(String roomId, Integer robotId){
        List<Integer> userIds = this.redisService.getUserIdsByRoomId(roomId);
        Integer otherId = null;
        for (Integer id : userIds) {
            if (!id.equals(robotId)) {
                otherId = id;
            }
        }
        return otherId;
    }

    /**
     * 是否达到执行记录的要求，1、到达执行时间 2、真实玩家以后请求进入服务器
     * @param activeTime
     * @param userId
     * @param roomId
     * @param questionIndex
     * @return
     */
    private boolean isTimeToSendRequest(Date activeTime,Integer userId,String roomId,Integer questionIndex) {
        Integer otherId = getTruePlayerId(roomId,userId);
        boolean isHasFirstQuestionSign = PkCacheMap.getPkRequestForFirstQuestion(otherId, questionIndex)!=null;
        boolean isHasOtherQuestionSign = PkCacheMap.getPkRequestForOtherQuestion(otherId,questionIndex)!=null;
        boolean isTimeRight = System.currentTimeMillis() >= activeTime.getTime();
        return (isHasFirstQuestionSign || isHasOtherQuestionSign)&&isTimeRight;
    }

    /**
     * 设置下一道题的请求参数
     * @param item
     */
    private void setNextRequest(PKRequestDTO item) {
       QuestionDTO nextQuestion =  getNextQuestion(item.getRoomId(),item.getQuestionId());
        if (null != nextQuestion && null != nextQuestion.getQuestionNumber()) {
            robotService.setRobotRequestLog(createNextQuestionIndex(item.getQuestionIndex()),
                    item.getRoomId(),
                    item.getUserId(),
                    nextQuestion.getQuestionNumber(),
                    getPlayerUseTime(item.getRoomId(), item.getUserId(), item.getQuestionIndex()),
                    getAnswerSelectIds(nextQuestion));
        } else {
           initRobot(item.getUserId());
        }
    }

    /**
     *构造随机选择答案的范围
     * @param nextQuestion
     * @return
     */
    private List<String> getAnswerSelectIds(QuestionDTO nextQuestion) {
        if (null == nextQuestion) {
            return null;
        }
        List<AnswerDTO> answerDTOS = nextQuestion.getAnswerList();
        String rightAnswerNumber = nextQuestion.getRightAnswerNumber();
        List<AnswerDTO> selectList = answerDTOS
                .stream()
                .filter(answerDTO -> !answerDTO.getAnswerNumber().equals(rightAnswerNumber))
                .collect(Collectors.toList());
        Integer random = robotService.getRandomInteger(0,selectList.size());
        List<String> answerList = Arrays.asList(rightAnswerNumber,selectList.get(random).getAnswerNumber());
        return answerList;
    }

    /**
     * 初始化机器人，删除缓存，状态还原为未启用
     * @param robotId
     */
    private void initRobot(Integer robotId){
        LogUtil.info("robot初始化设置！robotId="+robotId);
        redisService.deleteRobotPkRequestLog(robotId);
        this.sysUserServiceConsumer.setRobotInactive(robotId);
    }

    /**
     * 获取真实玩家答题所花费的时间
     * @param roomId
     * @param userId
     * @param questionIndex
     * @return
     */
    private Integer getPlayerUseTime(String roomId, Integer userId, Integer questionIndex) {
        Integer otherId = getTruePlayerId(roomId,userId);
        PKRequestDTO firstQuestionCache =  PkCacheMap.getPkRequestForFirstQuestion(otherId, questionIndex);
        PKRequestDTO otherQuestionCache = PkCacheMap.getPkRequestForOtherQuestion(otherId,questionIndex);
        return firstQuestionCache != null && firstQuestionCache.getUseTime() != null ? firstQuestionCache.getUseTime() : otherQuestionCache.getUseTime();
    }

    /**
     * 获取下一道题的id
     * @param roomId
     * @param questionId
     * @return
     */
    private QuestionDTO getNextQuestion(String roomId, Integer questionId) {
       return this.redisService.getRoomNextQuestionByQuestionNumber(roomId,questionId);
    }

    /**
     * 构建下一个问题编号
     * @param questionIndex
     * @return
     */
    private Integer createNextQuestionIndex(Integer questionIndex) {
        if (null == questionIndex) {
            return RobotServiceImpl.QUESTION_INDEX_INIT;
        }
        return questionIndex + 1;
    }
}
