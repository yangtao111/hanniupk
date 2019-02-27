package base;

import info.hanniu.hanniupk.core.aop.RequestDataAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 字典启动
 *
 * @author fengshuonan
 * @date 2018-07-25-下午12:23
 */
@SpringBootApplication(scanBasePackages = "info.hanniu.hanniupk.biz.dict")
public class DictApplication {

    private final static Logger logger = LoggerFactory.getLogger(DictApplication.class);

    @Bean
    public RequestDataAop requestDataAop() {
        return new RequestDataAop();
    }

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
        logger.info("GunsApplication is success!");
    }

}
