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
package info.hanniu.hanniupk.system.modular.provider;

import info.hanniu.hanniupk.kernel.model.api.AuthService;
import info.hanniu.hanniupk.kernel.model.dto.LoginUser;
import info.hanniu.hanniupk.system.modular.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权服务的提供者
 *
 * @author fengshuonan
 * @date 2018-08-06-上午9:05
 */
@RestController
@RequestMapping("/AuthServiceGateWayProvider")
public class AuthServiceGateWayProvider {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password) {
        return sysUserService.login(account, password);
    }
    @RequestMapping("/checkToken")
    public boolean checkToken(@RequestParam("token") String token) {
        return sysUserService.checkToken(token);
    }
    @RequestMapping("/logout")
    public void logout(@RequestParam("token") String token) {
        sysUserService.logout(token);
    }
    @RequestMapping("/getLoginUserByToken")
    public LoginUser getLoginUserByToken(@RequestParam("token") String token) {
        LoginUser loginUser = sysUserService.getLoginUserByToken(token);
        return  loginUser;
    }

}
