package info.hanniu.hanniupk.system.config;

import info.hanniu.hanniupk.kernel.logger.chain.aop.ChainOnConsumerAop;
import info.hanniu.hanniupk.kernel.logger.chain.aop.ChainOnControllerAop;
import info.hanniu.hanniupk.kernel.logger.chain.aop.ChainOnProviderAop;
import info.hanniu.hanniupk.kernel.model.api.AuthService;
import info.hanniu.hanniupk.system.context.LoginContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 全局配置
 *
 * @author fengshuonan
 * @date 2018-08-04-下午5:47
 */
@Configuration
public class ContextConfig {

    /**
     * 获取当前用户的便捷工具
     */
    @Bean
    public LoginContext loginContext(AuthService authService) {
        return new LoginContext(authService);
    }

    /**
     * 调用链治理
     */
    @Bean
    public ChainOnConsumerAop chainOnConsumerAop() {
        return new ChainOnConsumerAop();
    }

    /**
     * 调用链治理
     */
    @Bean
    public ChainOnControllerAop chainOnControllerAop() {
        return new ChainOnControllerAop();
    }

    /**
     * 调用链治理
     */
    @Bean
    public ChainOnProviderAop chainOnProviderAop() {
        return new ChainOnProviderAop();
    }

}
