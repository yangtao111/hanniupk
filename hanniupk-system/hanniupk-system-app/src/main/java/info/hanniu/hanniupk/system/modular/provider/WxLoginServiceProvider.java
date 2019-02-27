package info.hanniu.hanniupk.system.modular.provider;

import info.hanniu.hanniupk.kernel.model.api.IWxLoginService;
import info.hanniu.hanniupk.kernel.model.dto.WxLoginDTO;
import info.hanniu.hanniupk.kernel.model.vo.LoginResponseVO;
import info.hanniu.hanniupk.system.modular.service.WxLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.provider
 * @ClassName: WxLoginServiceProvider
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/15 9:24
 * @Version: 1.0
 */
@RestController
@Primary
public class WxLoginServiceProvider implements IWxLoginService{
    @Autowired
    private WxLoginServiceImpl wxLoginService;

    @Override
    public LoginResponseVO getWxLogin(@RequestBody WxLoginDTO wxLogin) {
        return wxLoginService.getWxLogin(wxLogin);
    }
}
