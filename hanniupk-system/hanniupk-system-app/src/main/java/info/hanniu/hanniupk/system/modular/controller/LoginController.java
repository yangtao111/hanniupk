/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.hanniu.hanniupk.system.modular.controller;

import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.model.api.AuthService;
import info.hanniu.hanniupk.kernel.model.dto.WxLoginDTO;
import info.hanniu.hanniupk.kernel.model.vo.LoginResponseVO;
import info.hanniu.hanniupk.system.constants.AuthConstants;
import info.hanniu.hanniupk.system.enums.SystemResponseStatusEnum;
import info.hanniu.hanniupk.system.modular.service.WxLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @date 2017-11-08-下午7:04
 */
@RestController
public class LoginController {

    @Autowired
    private AuthService authService;
    @Autowired
    private WxLoginServiceImpl wxLoginService;

    /**
     * 登录接口
     */
    @RequestMapping(AuthConstants.AUTH_ACTION_URL)
    public ResponseData auth(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        String token = authService.login(userName, password);
        return ResponseData.success(token);
    }

    /**
     * 验证token是否正确
     */
    @RequestMapping(AuthConstants.VALIDATE_TOKEN_URL)
    public ResponseData validateToken(@RequestParam("token") String token) {
        boolean tokenFlag = authService.checkToken(token);
        return ResponseData.success(tokenFlag);
    }

    /**
     * 退出接口
     */
    @RequestMapping(AuthConstants.LOGOUT_URL)
    public ResponseData logout(@RequestParam("token") String token) {
        authService.logout(token);
        return ResponseData.success();
    }

    /**
     * 微信登录
     * @Method getWxLogin
     * @Author tao
     * @Version  1.0
     * @Description
     * @param wxLogin
     * @Return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Exception
     * @Date 2018/9/29 17:38
     */
    @PostMapping(AuthConstants.WX_LOGIN_URL)
    @ResponseBody
    public ResponseData getWxLogin(@RequestBody WxLoginDTO wxLogin){
        LoginResponseVO resultMap = wxLoginService.getWxLogin(wxLogin);
        if (null == resultMap) {
            return new ResponseData(false, SystemResponseStatusEnum.DEFAULT_ERROR_CODE.getCode(),
                    SystemResponseStatusEnum.DEFAULT_ERROR_CODE.getMessage(),null);
        }
        return ResponseData.success(resultMap);
    }

}
