package info.hanniu.hanniupk.gateway.core.filter;

import info.hanniu.hanniupk.gateway.core.constants.AuthConstants;
import info.hanniu.hanniupk.gateway.modular.service.TokenValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ProjectName: hanniupk-gateway
 * @Package: info.hanniu.hanniupk.gateway.core.filter
 * @ClassName: JwtTokenFilter
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/23 18:43
 * @Version: 1.0
 */
@Configuration
public class JwtTokenFilter implements GlobalFilter {
    private Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private TokenValidateService tokenValidateService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (shouldFilter(exchange)) {
            doValidate(exchange);
        }
        return chain.filter(exchange.mutate().request(exchange.getRequest()).build());
       // return chain.filter(exchange);
    }

    /**
     * 校验token
     * @param exchange
     */
    private void doValidate(ServerWebExchange exchange) {
        tokenValidateService.doValidate(exchange.getRequest());
    }

    /**
     * 是否进行token验证,登陆接口和验证token放过资源过滤
     * @param exchange
     * @return
     */
    private boolean shouldFilter(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        return !isAuthActionUrl(path)
                && !isValidateTokenUrl(path)
                && !isValidateSocketUrl(path)
                && !isTokenTimeOut(path);
    }
    private boolean isAuthActionUrl(String path){
        return path.contains(AuthConstants.AUTH_ACTION_URL)||path.contains(AuthConstants.WX_LOGIN_URL);
    }
    private boolean isValidateTokenUrl(String path){
        return path.contains(AuthConstants.VALIDATE_TOKEN_URL);
    }
    private boolean isValidateSocketUrl(String path){
        return path.contains(AuthConstants.WEB_SOCKET_URL);
    }

    private boolean isTokenTimeOut(String path){
        return path.contains(AuthConstants.IS_TOKEN_EXIST);
    }
}
