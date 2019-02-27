package info.hanniu.hanniupk.kernel.logger.config;

import info.hanniu.hanniupk.kernel.logger.config.properties.LogProperties;
import info.hanniu.hanniupk.kernel.logger.service.LogProducerService;
import info.hanniu.hanniupk.kernel.logger.service.impl.LogProducerServiceImpl;
import info.hanniu.hanniupk.kernel.model.constants.ConfigPrefixConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认kafka消息队列日志
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午4:48:10
 */
@Configuration
public class LoggerAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = ConfigPrefixConstants.LOG_PREFIX)
    public LogProperties logProperties() {
        return new LogProperties();
    }

    @Bean
    public LogProducerService logProducerService() {
        return new LogProducerServiceImpl();
    }
}