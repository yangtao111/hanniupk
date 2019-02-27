package info.hanniu.hanniupk.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.config
 * @ClassName: SocketConfig
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/25 17:39
 * @Version: 1.0
 */
@Configuration
public class SocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
