package info.hanniu.hanniupk.backend.core.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.filter
 * @ClassName: ListenerEchoSocket
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/25 15:14
 * @Version: 1.0
 */
public class ListenerEchoSocket {
    @Autowired
    private WebSocketSession webSocketSession;
    public void closeWebSocket(){
        try {
            webSocketSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
