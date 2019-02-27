package info.hanniu.hanniupk.backend.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.mysql.jdbc.log.LogUtils;
import info.hanniu.hanniupk.backend.core.constant.RedisKeyConstant;
import info.hanniu.hanniupk.backend.modular.vo.RoomVO;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;

import java.util.Date;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.util
 * @Author: zhanglj
 * @Description: redis key 生成
 * @Date: 2018/11/1 2:26 PM
 * @Version: 1.0.0
 */
public class RedisKeyGenerateUtil {

    public static String getRoomKeyByRoomId(String roomId) {
        if (StringUtils.isEmpty(roomId)) {
            return null;
        }
        return RedisKeyConstant.ROOM_CASHE_KEY + roomId;
    }

    /**
     * 获取pk过程中缓存第1题标记的key
     * @param userId
     * @param questionIndex
     * @return
     */
    public static String getFirstQuestionGetSignByUserId(Integer userId,Integer questionIndex){
        if (null == userId) {
            LogUtil.info("FirstQuestionGetSign,userId为空！");
            return null;
        }
        return RedisKeyConstant.FIRST_QUESTION_GET_SIGN + userId.toString() + "_" + questionIndex;
    }

    /**
     * 获取pk过程中缓存2-n题标记的key
     * @param userId
     * @param questionIndex
     * @return
     */
    public static String getOtherQuestionGetSignByUserId(Integer userId,Integer questionIndex){
        if (null == userId) {
            LogUtil.info("OtherQuestionGetSign,userId为空！");
            return null;
        }
        return RedisKeyConstant.OTHER_QUESTION_GET_SIGN + userId.toString() + "_" + questionIndex;
    }

    /*public static String getHeartBeatLog(Integer userId){
        if (null == userId) {
            return null;
        }
        return RedisKeyConstant.HEART_BEAT_LOG + userId.toString();
    }*/

    /**
     * 创建pk过程中缓存返回数据的key
     * @param roomId
     * @param questionIndex
     * @return
     */
    public static String createPkResponseDataKey(String roomId,Integer userId ,Integer questionIndex) {
        if (org.apache.commons.lang.StringUtils.isEmpty(roomId)) {
            return null;
        }
        return RedisKeyConstant.PK_RESPONSE_DATA_PREFIX_ + roomId + "_" + userId+ "_" + questionIndex;
    }

    /**
     * 创建key用于存储pk过程中，双方玩家的当前积分
     *
     * @param roomId
     * @param userId
     * @return
     */
    public static String createPlayerIntegralPkIngKey(String roomId, Integer userId) {
        if (org.apache.commons.lang.StringUtils.isEmpty(roomId) || null == userId) {
            return null;
        }
        return roomId + userId.toString();
    }

    /**
     * 答题结果缓存
     * @param userId
     * @param questionIndex
     * @return
     */
    public static String createUserResultKey(Integer userId, Integer questionIndex) {
        if (null == userId) {
            return null;
        }
        return RedisKeyConstant.PLAYER_RESULT_PREFIX + userId.toString()+"_" + questionIndex;
    }

    /**
     * 分享给好友，用于判断分享是否超时或者分享者放弃战斗
     * @param userId
     * @return
     */
    public static String createUserShareKey(String userId){
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        return RedisKeyConstant.PK_SHARE_KEY + userId;
    }

    /**
     * 创建闯关模式，缓存玩家闯关选题的key
     * @param userId
     * @return
     */
    public static String createPassCustomsQuestionKey(Integer userId){
        if (null == userId) {
            return null;
        }
        return RedisKeyConstant.PASS_CUSTOMS_QUESTION_KEY_PREFIX + userId;
    }

    /**
     * 创建同步锁的key
     * @param roomId
     * @return
     */
    public static String createLockKey(String roomId) {
        if (StringUtils.isEmpty(roomId)) {
            return null;
        }
        return RedisKeyConstant.REENTRANT_LOCK_KEY_PREFIX+roomId;
    }

    /**
     * 创建reids中机器人自动回答问题的日志的key
     * @param userId
     * @return
     */
    public static String createRobotAutoAnswerLogKey(Integer userId){
        if (null == userId) {
            return null;
        }
        return RedisKeyConstant.ROBOT_AUTO_ANSWER_REQUEST_LOG + userId;
    }

    /**
     * 机器人记录模糊查询的key
     * @return
     */
    public static String createRobotAutoAnswerFuzzyKey(){
        return RedisKeyConstant.ROBOT_AUTO_ANSWER_REQUEST_LOG + "*";
    }
}
