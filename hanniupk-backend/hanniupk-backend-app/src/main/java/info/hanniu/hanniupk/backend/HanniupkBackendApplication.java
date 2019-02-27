package info.hanniu.hanniupk.backend;

import info.hanniu.hanniupk.backend.socket.MessageWebSocket;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 系统管理
 *
 * @author stylefeng
 * @Date 2018/1/22 21:27
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
public class HanniupkBackendApplication {

    public static void main(String[] args) {
       // SpringApplication.run(HanniupkBackendApplication.class, args);
        SpringApplication springApplication = new SpringApplication(HanniupkBackendApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        //解决WebSocket不能注入的问题
        MessageWebSocket.setApplicationContext(configurableApplicationContext);
        LogUtil.info("HanniupkBackendApplication 启动成功！");
    }

}
