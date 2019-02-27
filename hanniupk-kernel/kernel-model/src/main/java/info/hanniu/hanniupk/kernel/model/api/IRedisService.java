package info.hanniu.hanniupk.kernel.model.api;

import info.hanniu.hanniupk.kernel.model.dto.PlayerResultDTO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.api
 * @ClassName: IRedisService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 16:49
 * @Version: 1.0
 */
@RequestMapping("api/redisService")
public interface IRedisService {
    /**
     * 向redis中缓存小程序和汗牛服务器的通讯凭证：sessionId
     * @param key
     * @param value
     */
    @PostMapping("/setSessionId")
    void setSessionId(@RequestParam("key") String key,@RequestParam("value") String value);

    /**
     * 向redis中存储随机选择出的每个房间的问题
     * @param roomId
     * @param questionsMap
     */
    @PostMapping("/setRoomQuestions")
    void setRoomQuestions(@RequestParam("roomId") String roomId,@RequestParam("questionsMap") Map<String,QuestionDTO> questionsMap);

    /**
     * 根据问题编号查找单个问题
     * @param roomId
     * @param questionNumber
     * @return
     */
    @GetMapping("/getRoomQuestionByQuestionNumber")
    public QuestionDTO getRoomQuestionByQuestionNumber(@RequestParam("roomId") String roomId,@RequestParam("questionNumber") Integer questionNumber);

    /**
     * 缓存中删除本次pk的问题集合
     * @param roomId
     */
    @PostMapping("/clearQuestionsByRoomId")
    void clearQuestionsByRoomId(@RequestParam("roomId") String roomId);

    /**
     * 根据当前问题编号获取下一个问题
     * @param roomId
     * @param questionNumber
     * @return
     */
    @GetMapping("getRoomNextQuestionByQuestionNumber")
    QuestionDTO getRoomNextQuestionByQuestionNumber(@RequestParam("roomId") String roomId,@RequestParam("questionNumber") Integer questionNumber);

    /**
     * redis 缓存当前pk玩家的积分
     * @param roomId
     * @param userId
     * @param currentIntegral
     */
    @PostMapping("/setPlayerIntegralPkIng")
    void setPlayerIntegralPkIng(@RequestParam("roomId") String roomId,@RequestParam("userId") Integer userId,@RequestParam("currentIntegral") String currentIntegral);

    /**
     * redis 缓存中获取当前pk玩家的积分
     * @param roomId
     * @param userId
     * @return
     */
    @GetMapping("/getPlayerIntegralPkIng")
    String getPlayerIntegralPkIng(@RequestParam("roomId") String roomId,@RequestParam("userId") Integer userId);

    /**
     *redis 删除此次pk玩家的积分缓存
     * @param roomId
     * @param userId
     */
    @PostMapping("/deletePlayerIntegralPkIng")
    void deletePlayerIntegralPkIng(@RequestParam("roomId") String roomId,@RequestParam("userId") Integer userId);
}
