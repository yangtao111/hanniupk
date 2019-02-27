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


import info.hanniu.hanniupk.gateway.resource.ResourceDefinition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * 资源服务的消费者
 *
 * @author zhanglj
 * @date 2018-08-07-下午3:12
 */
@FeignClient("hanniupk-system")
public interface ResourceServiceConsumer {


    @RequestMapping("/resourceServiceForGateWay/reportResources")
    void reportResources(@RequestParam("appCode") String appCode,
                         @RequestBody Map<String, Map<String, ResourceDefinition>> resourceDefinitions);

    @RequestMapping("/resourceServiceForGateWay/getUserResourceUrls")
    Set<String> getUserResourceUrls(@RequestParam("accountId") String accountId);

    @RequestMapping("/resourceServiceForGateWay/getResourceByUrl")
    ResourceDefinition getResourceByUrl(@RequestParam("url") String url);

}
