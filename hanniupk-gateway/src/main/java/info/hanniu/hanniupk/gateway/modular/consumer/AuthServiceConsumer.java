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
package info.hanniu.hanniupk.gateway.modular.consumer;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 鉴权服务的消费者
 *
 * @author fengshuonan
 * @date 2018-08-07-下午3:12
 */
@FeignClient("hanniupk-system")
public interface AuthServiceConsumer {
    @RequestMapping("/AuthServiceGateWayProvider/login")
    String login(@RequestParam("account") String account, @RequestParam("password") String password);

    @RequestMapping("/AuthServiceGateWayProvider/checkToken")
    boolean checkToken(@RequestParam("token") String token);

    @RequestMapping("/AuthServiceGateWayProvider/logout")
    void logout(@RequestParam("token") String token);
}
