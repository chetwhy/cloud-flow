package cn.yccoding.gateway.filter;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.gateway.common.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 鉴权过滤器
 *
 * @author YC
 * @since 2020/9/25
 */
//@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered, InitializingBean {
    /**
     * 请求各个微服务 不需要用户认证的URL
     */
    private static Set<String> shouldSkipUrl = new LinkedHashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 从数据库读取
        shouldSkipUrl.add("/producer");
        // 去认证的请求,本来就不需要拦截
        shouldSkipUrl.add("/oauth/token");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String reqPath = exchange.getRequest().getURI().getPath();
        TokenInfo tokenInfo = (TokenInfo) exchange.getAttribute("tokenInfo");

        // 无需拦截的路径
        boolean approveFlag = shouldSkipUrl.stream().anyMatch(reqPath::contains);
        if (approveFlag) {
            return chain.filter(exchange);
        }

        // token过期
        if (!tokenInfo.isActive()) {
            log.warn("token已过期");
            throw new CustomException(ResultCodeEnum.TOKEN_EXPIRED);
        }

        // 鉴权
        String[] authorities = tokenInfo.getAuthorities();
        boolean permissionFlag = Arrays.stream(authorities).anyMatch(reqPath::contains);
        if (!permissionFlag) {
            log.warn("权限不足");
            throw new CustomException(ResultCodeEnum.ACCESS_FORBIDEN);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
