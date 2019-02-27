package info.hanniu.hanniupk.system.modular.consumer;

import info.hanniu.hanniupk.kernel.model.api.IRedisService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.consumer
 * @ClassName: RedisServiceConsumer
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 17:27
 * @Version: 1.0
 */
@FeignClient(name = "hanniupk-backend")
public interface RedisServiceConsumer extends IRedisService{
}
