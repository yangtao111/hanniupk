package info.hanniu.hanniupk.backend.socket;

import com.alibaba.fastjson.JSON;
import info.hanniu.hanniupk.backend.core.encoder.SocketResponseEncoder;
import info.hanniu.hanniupk.backend.core.variable.WebSocketMap;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.service.impl.AsyncServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.PkSocketServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.SocketMessageMappingImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.config
 * @ClassName: MyWebSocket
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/25 17:40
 * @Version: 1.0
 */
@ServerEndpoint(value = "/messageSocket/{userId}", encoders = {SocketResponseEncoder.class})
@Component
public class MessageWebSocket {
    private Logger logger = LoggerFactory.getLogger(MessageWebSocket.class);
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    public Session session;
    /**
     * socket无法自动注入，采用以下方式注入
     */
    private static ApplicationContext applicationContext;
    private SocketMessageMappingImpl socketMessageMapping;
    private AsyncServiceImpl asyncService;
    private PkSocketServiceImpl pkSocketService;
    public static void setApplicationContext(ApplicationContext applicationContext) {
        MessageWebSocket.applicationContext = applicationContext;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        logger.info("------>messageWebSocket->onOpen:socket连接成功，userId={},size={}<------",userId,WebSocketMap.webSocketMap.size());
        this.session = session;
        WebSocketMap.webSocketMap.put(userId, this);
    }



    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") Integer userId) {
        logger.info("------>关闭messageSocket.onClose；删除socket<------");
        WebSocketMap.webSocketMap.remove(userId);
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        socketMessageMapping = applicationContext.getBean(SocketMessageMappingImpl.class);
        socketMessageMapping.run(message);

//        asyncService = applicationContext.getBean(AsyncServiceImpl.class);
//        pkSocketService = applicationContext.getBean(PkSocketServiceImpl.class);
//        PKRequestDTO pkRequest = JSON.parseObject(message,PKRequestDTO.class);
//        asyncService.getDeferredResult(this.pkSocketService, pkRequest);
    }
    /**
     * 发生错误时调用
     *
     * @OnError
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("------>messageSocket 发生错误<------", error.getCause());
        error.printStackTrace();
    }

}
