package info.hanniu.hanniupk.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.DispatcherHandler;

/**
 * 网关服务
 *
 * @author fengshuonan
 * @Date 2017/11/10 上午11:24
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableDiscoveryClient
public class HanniupkGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanniupkGatewayApplication.class, args);
    }

}
