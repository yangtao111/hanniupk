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
package info.hanniu.hanniupk.gateway.modular.service.impl;

import info.hanniu.hanniupk.gateway.core.exception.AuthExceptionEnum;
import info.hanniu.hanniupk.gateway.core.exception.GateWayException;
import info.hanniu.hanniupk.gateway.modular.service.TokenValidateService;
import info.hanniu.hanniupk.gateway.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;


/**
 * 纯token验证鉴权服
 *
 * @author fengshuonan
 * @date 2018-08-13 21:52
 */
//@Service
public class PureTokenValidateServiceImpl extends TokenValidateService {
    private Logger logger = LoggerFactory.getLogger(PureTokenValidateServiceImpl.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected boolean validateToken(String token, ServerHttpRequest request) {
        logger.info("PureTokenValidateServiceImpl......................................");
        try {
            boolean flag = jwtTokenUtil.isTokenExpired(token);
            if (flag) {
                throw new GateWayException(AuthExceptionEnum.TOKEN_ERROR);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            throw new GateWayException(AuthExceptionEnum.TOKEN_ERROR);
        }
    }
}
