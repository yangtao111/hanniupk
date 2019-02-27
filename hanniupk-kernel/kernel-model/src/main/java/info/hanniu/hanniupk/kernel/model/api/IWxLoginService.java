package info.hanniu.hanniupk.kernel.model.api;

import info.hanniu.hanniupk.kernel.model.dto.WxLoginDTO;
import info.hanniu.hanniupk.kernel.model.vo.LoginResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.api
 * @ClassName: IWxLoginService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/15 9:17
 * @Version: 1.0
 */
@RequestMapping("/api/IWxLoginService")
public interface IWxLoginService {

    /**
     * 微信登录接口
     * @param wxLogin
     * @return
     */
    @PostMapping("/getWxLogin")
    public LoginResponseVO getWxLogin(@RequestBody WxLoginDTO wxLogin);
}
