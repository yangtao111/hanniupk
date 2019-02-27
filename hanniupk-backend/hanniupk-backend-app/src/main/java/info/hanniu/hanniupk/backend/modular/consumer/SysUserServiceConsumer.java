package info.hanniu.hanniupk.backend.modular.consumer;

import info.hanniu.hanniupk.kernel.model.api.ISysUserService;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.consumer
 * @ClassName: SysUserServiceConsumer
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 19:38
 * @Version: 1.0
 */
@FeignClient("hanniupk-system")
public interface SysUserServiceConsumer extends ISysUserService {

    
}
