package info.hanniu.hanniupk.backend.modular.service.impl;

import cn.hutool.core.date.DateUtil;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: RobotServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/21 16:11
 * @Version: 1.0
 */
@Service
public class RobotServiceImpl {

    @Autowired
    private RedisServiceImpl redisService;
    /** 问题答案随机数区间最小值 */
    private static final Integer ANSWER_MARK_MIN = 0;
    /** 当请求第一题时，所花费的默认时间 */
    private static final Integer GET_FIRST_QUESTION_TIME = 1;
    /** 计算机器人答题所用时间随机参数的最小值 */
    private static final Integer USER_TIME_MIN = 3;
    /** 计算机器人答题所用时间随机参数的最大值 */
    private static final Integer USER_TIME_MAX = 8;
    /** 问题编号的初始值 */
    public static final Integer QUESTION_INDEX_INIT = 0;
    /** 玩家答题所用时间的最大值 */
    private static final Integer ANSWER_TOTAL_TIME = 10;
    /** 真实玩家答题所用时间的默认值 */
    private static final Integer PLAYER_USER_TIME_DEFAULT = 0;

    /**
     * &#x8bbe;&#x7f6e;&#x6216;&#x8005;&#x4fee;&#x6539;&#x673a;&#x5668;&#x4eba;&#x81ea;&#x52a8;&#x8bf7;&#x6c42;&#x8bb0;&#x5f55;&#x7f13;&#x5b58;
     * @param questionIndex
     * @param roomId
     * @param userId
     * @param questionId
     * @param playerUseTime
     * @param answerNumberList 答案数量，用来计算随机答案
     */
    public void setRobotRequestLog(Integer questionIndex, String roomId, Integer userId,
                                   Integer questionId,Integer playerUseTime,List<String> answerNumberList){
        if (null == playerUseTime) {
            playerUseTime = PLAYER_USER_TIME_DEFAULT;
        }
        this.redisService.setOrUpdateRobotPkRequestLog(userId,
                createRobotRequest(questionIndex,roomId,userId,questionId,playerUseTime,answerNumberList));
    }

    /**
     * 构建机器人自动请求的参数
     *
     * @param questionIndex    问题编号
     * @param roomId           房间编号
     * @param userId           机器人id
     * @param questionId       选题id
     * @return
     */
    private PKRequestDTO createRobotRequest(Integer questionIndex, String roomId, Integer userId, Integer questionId,
                                            Integer playerUseTime,List<String> answerNumberList) {
        PKRequestDTO pkRequestDTO = new PKRequestDTO();
        pkRequestDTO.setQuestionIndex(questionIndex);
        pkRequestDTO.setRoomId(roomId);
        pkRequestDTO.setUserId(userId);
        pkRequestDTO.setQuestionId(questionId);
        pkRequestDTO.setQuestionAnswerId(randomQuestionAnswerId(answerNumberList));
        Integer userTime = PLAYER_USER_TIME_DEFAULT;
        if (playerUseTime.equals(PLAYER_USER_TIME_DEFAULT)) {
            pkRequestDTO.setUseTime(playerUseTime);
        } else {
            userTime = randomUseTime(pkRequestDTO.getQuestionId(),playerUseTime);
            pkRequestDTO.setUseTime(userTime);
        }
        pkRequestDTO.setActiveTime(randomActiveTime(userTime,playerUseTime));

        return pkRequestDTO;
    }

    /**
     * 计算机器人执行答题记录的时间
     * @param userTime
     * @param playUseTime
     * @return
     */
    private Date randomActiveTime(Integer userTime,Integer playUseTime) {
        return DateUtils.addSeconds(new Date(), userTime - playUseTime);
    }

    /**
     * 计算机器人答题所花费的时间
     * @param questionId
     * @param playerUserTime
     * @return
     */
    private Integer randomUseTime(Integer questionId,Integer playerUserTime) {
        if (null == questionId) {
            return GET_FIRST_QUESTION_TIME;
        }
        Integer random =  getRandomInteger(USER_TIME_MIN, USER_TIME_MAX);
        if ((random+playerUserTime) > ANSWER_TOTAL_TIME) {
            return ANSWER_TOTAL_TIME;
        }
        return random+playerUserTime;
    }

    /**
     * 机器人随机选择一个答案
     * @return
     */
    private String randomQuestionAnswerId(List<String> answerNumberList) {
        if (null == answerNumberList || answerNumberList.size() <=0 ) {
            return null;
        }
        return answerNumberList.get(getRandomInteger(ANSWER_MARK_MIN,answerNumberList.size()));
    }

    /**
     * 取个随机整数，0开始，bound-1结束
     *
     * @param min
     * @param max
     * @return
     */
    public Integer getRandomInteger(Integer min, Integer max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}
