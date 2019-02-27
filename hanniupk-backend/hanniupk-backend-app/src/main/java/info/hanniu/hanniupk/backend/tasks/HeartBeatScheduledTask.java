package info.hanniu.hanniupk.backend.tasks;

import cn.hutool.core.date.DateUtil;
import com.netflix.discovery.converters.Auto;
import info.hanniu.hanniupk.backend.core.variable.WebSocketMap;
import info.hanniu.hanniupk.backend.modular.dto.HeartBeatLogDTO;
import info.hanniu.hanniupk.backend.modular.service.impl.PkSocketServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.RedisServiceImpl;
import info.hanniu.hanniupk.backend.socket.MessageWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.tasks
 * @ClassName: HeartBeatScheduledTask
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/26 20:08
 * @Version: 1.0
 */
@Component
public class HeartBeatScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatScheduledTask.class);

    private static final Long DIFFERENT_MILLION_SECOND_MAX = 30000L;

    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private PkSocketServiceImpl pkSocketService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
       /*List<HeartBeatLogDTO> heartBeatLogDTOList = redisService.getHeartBeatLogs();
        if (null != heartBeatLogDTOList && heartBeatLogDTOList.size()>0) {
            // key = userId,value = HeartBeatLogDTo
            heartBeatLogDTOList.forEach((heartBeatLogDTO)->{
                if (isTimeOut(heartBeatLogDTO.getCreateTime())) {
                    returnOpponentWin(heartBeatLogDTO.getUserId(),heartBeatLogDTO.getRoomId());
                    this.removeWebSocket(heartBeatLogDTO.getUserId());
                }
            });
        }*/
    }

    /**
     * 删除非正常断开socket连接的用户的socket对象
     * @param userId
     */
    private void removeWebSocket(Integer userId){
        logger.info("------>定时任务：删除socket<------");
        MessageWebSocket messageWebSocket = WebSocketMap.webSocketMap.get(userId);
        if (null != messageWebSocket) {
            WebSocketMap.webSocketMap.remove(userId);
        }
    }

   /* private void returnOpponentWin(Integer userId,String roomId,Integer questionIndex) {
        logger.info("------>定时任务:返回获胜者信息<------");
        pkSocketService.opponentGiveUp(roomId,userId,questionIndex);
    }*/

    /**
     * 10秒没有收到新日志，认为socket与前端失去联系
     * @param timeMIllis
     * @return
     */
    public boolean isTimeOut(String timeMIllis){
        Date currentDate = new Date();
        Date date = new Date(Long.valueOf(timeMIllis));
        Long differentMillisecond = DateUtil.betweenMs(currentDate,date);
        if (differentMillisecond > DIFFERENT_MILLION_SECOND_MAX) {
            logger.info("心跳启动，当前时间：{}，上次心跳时间：{}",
                    DateUtil.format(currentDate,"yyyy-MM-dd HH:mm:ss"),
                    DateUtil.format(date,"yyyy-MM-dd HH:mm:ss"));
            return true;
        }
        return false;
    }

}
