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
package info.hanniu.hanniupk.gateway.modular.service;

import info.hanniu.hanniupk.gateway.core.exception.AuthExceptionEnum;
import info.hanniu.hanniupk.gateway.core.exception.GateWayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

import static info.hanniu.hanniupk.gateway.core.constants.AuthConstants.AUTH_HEADER;

/**
 * Token校验的服务
 *
 * @author fengshuonan
 * @date 2018-08-13 21:50
 */
public abstract class TokenValidateService {

    private Logger logger = LoggerFactory.getLogger(TokenValidateService.class);

    /**
     * @author stylefeng
     * @Date 2018/8/13 22:11
     */
    public boolean doValidate(ServerHttpRequest request) {
        logger.info("TokenValidateService>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>..1");
        //先获取token
        String tokenFromRequest = this.getTokenFromRequest(request);

        //校验token是否正确
        return this.validateToken(tokenFromRequest, request);
    }

    /**
     * 获取请求中的token
     *
     * @author stylefeng
     * @Date 2018/8/13 22:05
     */
    private String getTokenFromRequest(ServerHttpRequest request) {
        //获取token
        String authToken = "";
        if (null != request.getHeaders().get(AUTH_HEADER)) {
            authToken = request.getHeaders().get(AUTH_HEADER).get(0);
        }
        if (null == authToken || "".equals(authToken)) {
            //如果header中没有token，则检查请求参数中是否带token
            List<String> tokenList = request.getQueryParams().get("token");
            if (null == tokenList || null == authToken || "".equals(authToken)) {
                throw new GateWayException(AuthExceptionEnum.TOKEN_EMPTY);
            }
            authToken = tokenList.get(0);
        }
//        else {
//            authToken = authToken.substring("Bearer ".length());
//        }

        return authToken;
    }

    /**
     * 校验token
     *
     * @author stylefeng
     * @Date 2018/8/13 21:50
     */
    protected abstract boolean validateToken(String token, ServerHttpRequest request);

}
