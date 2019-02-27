package info.hanniu.hanniupk.backend.config;

import info.hanniu.hanniupk.backend.core.interceptor.CallableProcessorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.config
 * @Author: zhanglj
 * @Description: 异步处理线程池
 * @Date: 2018/10/9 7:36 PM
 * @Version: 1.0.0
 */
@Configuration
public class AsyncThreadPool implements WebMvcConfigurer {

    /**
     * 配置线程池
     *
     * @return
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor getAsyncThreadPoolTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(200);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.setKeepAliveSeconds(200);
        taskExecutor.setThreadNamePrefix("callable-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        //处理 callable超时
        configurer.setDefaultTimeout(60 * 1000L);
        configurer.registerCallableInterceptors(callableProcessorInterceptor());
        configurer.setTaskExecutor(getAsyncThreadPoolTaskExecutor());
    }

    @Bean
    public CallableProcessorInterceptor callableProcessorInterceptor() {
        return new CallableProcessorInterceptor();
    }


}
