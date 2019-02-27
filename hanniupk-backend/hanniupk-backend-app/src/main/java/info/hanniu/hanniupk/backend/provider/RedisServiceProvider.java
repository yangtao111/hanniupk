package info.hanniu.hanniupk.backend.provider;

import info.hanniu.hanniupk.backend.core.variable.PkCacheMap;
import info.hanniu.hanniupk.backend.modular.service.impl.RedisServiceImpl;
import info.hanniu.hanniupk.kernel.model.api.IRedisService;
import info.hanniu.hanniupk.kernel.model.dto.PlayerResultDTO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.provider
 * @ClassName: RedisServiceProvider
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 17:05
 * @Version: 1.0
 */
@RestController
@Primary
public class RedisServiceProvider implements IRedisService {

    @Autowired
    private RedisServiceImpl redisService;

    /**
     * 向redis中缓存小程序和汗牛服务器的通讯凭证：sessionId
     * @param key
     * @param value
     */
    @Override
    public void setSessionId(String key, String value){
        redisService.setSessionId(key,value);
    };

    /**
     * 向redis中存储随机选择出的每个房间的问题
     * @param roomId
     * @param questionsMap
     */
    @Override
    public void setRoomQuestions(String roomId, Map<String,QuestionDTO> questionsMap){
        redisService.setRoomQuestions(roomId,questionsMap);
    };

    /**
     * 根据问题编号查找单个问题
     * @param roomId
     * @param questionNumber
     * @return
     */
    @Override
    public QuestionDTO getRoomQuestionByQuestionNumber(String roomId, Integer questionNumber){
        return redisService.getRoomQuestionByQuestionNumber(roomId,questionNumber);
    };

    /**
     * 缓存中删除本次pk的问题集合
     * @param roomId
     */
    @Override
    public void clearQuestionsByRoomId(String roomId){
        redisService.clearQuestionsByRoomId(roomId);
    };

    /**
     * 根据当前问题编号获取下一个问题
     * @param roomId
     * @param questionNumber
     * @return
     */
    @Override
    public QuestionDTO getRoomNextQuestionByQuestionNumber(String roomId, Integer questionNumber){
        return redisService.getRoomNextQuestionByQuestionNumber(roomId,questionNumber);
    };

    /**
     * redis 缓存当前pk玩家的积分
     * @param roomId
     * @param userId
     * @param currentIntegral
     */
    @Override
    public void setPlayerIntegralPkIng(String roomId, Integer userId, String currentIntegral){
        PkCacheMap.setPlayerIntegralPkIng(roomId,userId,currentIntegral);
    };

    /**
     * redis 缓存中获取当前pk玩家的积分
     * @param roomId
     * @param userId
     * @return
     */
    @Override
    public String getPlayerIntegralPkIng(String roomId, Integer userId){
        return PkCacheMap.getPlayerIntegralPkIng(roomId,userId);
    };

    /**
     *redis 删除此次pk玩家的积分缓存
     * @param roomId
     * @param userId
     */
    @Override
    public void deletePlayerIntegralPkIng(String roomId, Integer userId){
        PkCacheMap.deletePlayerIntegralPkIng(roomId,userId);
    };
}
