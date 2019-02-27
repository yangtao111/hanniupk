package info.hanniu.hanniupk.system.modular.controller;

import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.system.constants.AuthConstants;
import info.hanniu.hanniupk.system.enums.IsTokenExistEnum;
import info.hanniu.hanniupk.system.modular.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular
 * @ClassName: TokenController
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/16 17:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证token是否过期，实际是验证的token正确性
     * @param token
     * @return
     */
    @GetMapping(AuthConstants.IS_TOKEN_EXIST)
    @ResponseBody
    public ResponseData isTokenTimeOut(String token){
        if (sysUserService.checkToken(token)) {
            return ResponseData.success(IsTokenExistEnum.IS_TOKEN_EXIST_YES.getCode());
        }
        return ResponseData.success(IsTokenExistEnum.IS_TOKEN_EXIST_NO.getCode());
    }
}
