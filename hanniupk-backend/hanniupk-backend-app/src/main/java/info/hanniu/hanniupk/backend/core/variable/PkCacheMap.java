package info.hanniu.hanniupk.backend.core.variable;

import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.util.RedisKeyGenerateUtil;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import info.hanniu.hanniupk.kernel.model.dto.PlayerResultDTO;
import org.apache.commons.lang.StringUtils;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.variable
 * @ClassName: PkCacheMap
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/6 13:22
 * @Version: 1.0
 */
public class PkCacheMap {
    public static int PK_PLAYERS_SIZE = 2;
    private static ConcurrentHashMap<String, Integer> roomQuestionIndexMap = new ConcurrentHashMap<>();

    public static void setRoomQuestionIndex(String roomId, Integer questionIndex) {
        if (StringUtils.isNotEmpty(roomId) && null != questionIndex) {
            roomQuestionIndexMap.put(roomId, questionIndex);
        }
    }

    public static Integer getRoomQuestionIndex(String roomId) {
        if (StringUtils.isNotEmpty(roomId)) {
            return roomQuestionIndexMap.get(roomId);
        }
        return null;
    }

    public static void deleteRoomQuestionIndex(String roomId) {
        if (StringUtils.isNotEmpty(roomId)) {
            roomQuestionIndexMap.remove(roomId);
        }
    }

    /**
     * 每回合答题结果
     */
    private static ConcurrentHashMap<String, ResponseData> pkResponseDateMap = new ConcurrentHashMap<>();

    /**
     * 缓存每回合答题结果
     *
     * @param roomId
     * @param questionIndex
     * @param pkResponse
     */
    public static void setPkResponseData(String roomId, Integer userId, Integer questionIndex, ResponseData pkResponse) {
        if (StringUtils.isNotEmpty(roomId)) {
            pkResponseDateMap.put(RedisKeyGenerateUtil.createPkResponseDataKey(roomId, userId, questionIndex),
                    pkResponse);

        }
    }

    /**
     * 获取每回合答题结果
     *
     * @param roomId
     * @param questionIndex
     * @return
     */
    public static ResponseData getPkResponseData(String roomId, Integer userId, Integer questionIndex) {
        if (StringUtils.isNotEmpty(roomId)) {
            return pkResponseDateMap.get(RedisKeyGenerateUtil.createPkResponseDataKey(roomId, userId, questionIndex));
        }
        return null;
    }

    /**
     * 删除每回合答题结果
     *
     * @param roomId
     * @param questionIndex
     */
    public static void deletePkResponseData(String roomId, Integer userId, Integer questionIndex) {
        if (StringUtils.isNotEmpty(roomId)) {
            pkResponseDateMap.remove(RedisKeyGenerateUtil.createPkResponseDataKey(roomId, userId, questionIndex));
        }
    }

    /**
     * 请求标记
     */
    private static ConcurrentHashMap<String, PKRequestDTO> questionGetSignMap = new ConcurrentHashMap<>();

    /**
     * 缓存第一题请求
     *
     * @param pkRequest
     */
    public static void setFirstQuestionGetSign(PKRequestDTO pkRequest) {
        setRequest(pkRequest, RedisKeyGenerateUtil.getFirstQuestionGetSignByUserId(pkRequest.getUserId(), pkRequest.getQuestionIndex()));
    }

    /**
     * 缓存第2-n题请求
     *
     * @param pkRequest
     */
    public static void setOtherQuestionGetSign(PKRequestDTO pkRequest) {
        setRequest(pkRequest, RedisKeyGenerateUtil.getOtherQuestionGetSignByUserId(pkRequest.getUserId(), pkRequest.getQuestionIndex()));
    }

    public static void setRequest(PKRequestDTO pkRequest, String redisKey) {
        if (null != pkRequest && StringUtils.isNotEmpty(redisKey)) {
            questionGetSignMap.put(redisKey, pkRequest);
        }
    }

    /**
     * 删除第一题请求缓存
     *
     * @param userId
     * @param questionIndex
     */
    public static void deleteFirstQuestionGetSign(Integer userId, Integer questionIndex) {
        if (null != userId) {
            deleteRequest(RedisKeyGenerateUtil.getFirstQuestionGetSignByUserId(userId, questionIndex));
        }
    }

    /**
     * 删除第2-n题请求缓存
     *
     * @param userId
     * @param questionIndex
     */
    public static void deleteOtherQuestionGetSign(Integer userId, Integer questionIndex) {
        if (null != userId) {
            deleteRequest(RedisKeyGenerateUtil.getOtherQuestionGetSignByUserId(userId, questionIndex));
        }
    }

    public static void deleteRequest(String redisKey) {
        if (StringUtils.isNotEmpty(redisKey)) {
            questionGetSignMap.remove(redisKey);
        }
    }

    /**
     * 获取第一题请求缓存
     *
     * @param userId
     * @param questionIndex
     * @return
     */
    public static PKRequestDTO getPkRequestForFirstQuestion(Integer userId, Integer questionIndex) {
        return getRequest(RedisKeyGenerateUtil.getFirstQuestionGetSignByUserId(userId, questionIndex));
    }

    /**
     * 获取第2-n题请求缓存
     *
     * @param userId
     * @param questionIndex
     * @return
     */
    public static PKRequestDTO getPkRequestForOtherQuestion(Integer userId, Integer questionIndex) {
        return getRequest(RedisKeyGenerateUtil.getOtherQuestionGetSignByUserId(userId, questionIndex));
    }

    public static PKRequestDTO getRequest(String redisKey) {
        PKRequestDTO pkRequestDTO = questionGetSignMap.get(redisKey);
        return pkRequestDTO;
    }

    /**
     * 缓存玩家pk过程中积分
     */
    private static ConcurrentHashMap<String, String> playerIntegralMap = new ConcurrentHashMap<>();

    /**
     * 缓存pk过程中玩家积分
     *
     * @param roomId
     * @param userId
     * @param currentIntegral
     */
    public static void setPlayerIntegralPkIng(String roomId, Integer userId, String currentIntegral) {
        if (StringUtils.isNotEmpty(roomId) && null != userId) {
            playerIntegralMap.put(RedisKeyGenerateUtil.createPlayerIntegralPkIngKey(roomId, userId),
                    currentIntegral);
        }
    }

    /**
     * 获取玩家积分缓存
     *
     * @param roomId
     * @param userId
     * @return
     */
    public static String getPlayerIntegralPkIng(String roomId, Integer userId) {
        if (StringUtils.isNotEmpty(roomId) && null != userId) {
            return playerIntegralMap.get(RedisKeyGenerateUtil.createPlayerIntegralPkIngKey(roomId, userId));
        }
        return null;
    }

    /**
     * 删除玩家积分缓存
     *
     * @param roomId
     * @param userId
     */
    public static void deletePlayerIntegralPkIng(String roomId, Integer userId) {
        if (StringUtils.isNotEmpty(roomId) && null != userId) {
            playerIntegralMap.remove(RedisKeyGenerateUtil.createPlayerIntegralPkIngKey(roomId, userId));
        }
    }

    /**
     * 缓存每回合答题结果
     */
    private static ConcurrentHashMap<String, PlayerResultDTO> playerResultMap = new ConcurrentHashMap<>();

    /**
     * 缓存每回合答题结果
     *
     * @param userId
     * @param questionIndex
     * @param playerResult
     */
    public static void setPlayerResultByUserId(Integer userId, Integer questionIndex, PlayerResultDTO playerResult) {
        if (null != userId) {
            playerResultMap.put(RedisKeyGenerateUtil.createUserResultKey(userId, questionIndex), playerResult);
        }
    }

    /**
     * 获取每回合答题结果
     *
     * @param userId
     * @param questionIndex
     * @return
     */
    public static PlayerResultDTO getPlayerResultByUserId(Integer userId, Integer questionIndex) {
        if (null != userId) {
            return playerResultMap.get(RedisKeyGenerateUtil.createUserResultKey(userId, questionIndex));
        }
        return null;
    }

    /**
     * 删除每回合答题结果
     *
     * @param userId
     * @param questionIndex
     */
    public static void deletePlayerResultByUserId(Integer userId, Integer questionIndex) {
        if (null != userId) {
            playerResultMap.remove(RedisKeyGenerateUtil.createUserResultKey(userId, questionIndex));
        }
    }

    /**
     * 同步锁
     */
    public static ConcurrentHashMap<String,ReentrantLock> reentrantLockConcurrentHashMap = new ConcurrentHashMap<>();

    public static void setLockByRoomId(String roomId,ReentrantLock reentrantLock){
        if (StringUtils.isNotEmpty(roomId)) {
            reentrantLockConcurrentHashMap.put(RedisKeyGenerateUtil.createLockKey(roomId),reentrantLock);
        }
    }
    public static ReentrantLock getLockByRoomId(String roomId){
        if (StringUtils.isEmpty(roomId)) {
            return null;
        }
        return reentrantLockConcurrentHashMap.get(RedisKeyGenerateUtil.createLockKey(roomId));
    }
    public static void deleteLockByRoomId(String roomId){
        if (StringUtils.isNotEmpty(roomId)) {
            reentrantLockConcurrentHashMap.remove(RedisKeyGenerateUtil.createLockKey(roomId));
        }
    }

}
