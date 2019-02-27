package info.hanniu.hanniupk.backend.core.variable;

import info.hanniu.hanniupk.backend.socket.MessageWebSocket;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.variable
 * @ClassName: WebSocketMap
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/5 14:07
 * @Version: 1.0
 */
public class WebSocketMap {
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 由于web类型不能存储key相同的对象，所以把userId作为key
     */
    public static ConcurrentHashMap<Integer, MessageWebSocket> webSocketMap = new ConcurrentHashMap<>();
}
