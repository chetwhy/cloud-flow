package cn.yccoding.gateway.filter;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.gateway.common.MDA;
import cn.yccoding.gateway.common.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 认证过滤器 根据url判断用户请求是要经过认证 才能访问
 *
 * @author YC
 * @since 2020/9/25
 */
@Slf4j
//@Component
public class AuthorizationFilter implements GlobalFilter, Ordered, InitializingBean {
    /**
     * 请求各个微服务 不需要用户认证的URL
     */
    private static final Set<String> shouldSkipUrl = new LinkedHashSet<>();
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 从数据库读取
        shouldSkipUrl.add("/producer");
        // 去认证的请求,本来就不需要拦截
        shouldSkipUrl.add("/oauth/token");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 解析请求路径
        String reqPath = exchange.getRequest().getURI().getPath();
        log.info("网关认证开始URL->:[{}]", reqPath);

        // 1.不需要认证的url
        /*// 使用ant匹配路径
        AntPathMatcher matcher = new AntPathMatcher();
        shouldSkipUrl.stream().anyMatch(i -> matcher.match(i, reqPath));*/
        boolean approveFlag = shouldSkipUrl.stream().anyMatch(reqPath::contains);
        if (approveFlag) {
            return chain.filter(exchange);
        }

        // 获取请求头
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            log.warn("需要认证的url,请求头为空");
            throw new CustomException(ResultCodeEnum.AUTHORIZATION_HEADER_IS_EMPTY);
        }

        // 解析token
        TokenInfo tokenInfo = null;
        try {
            tokenInfo = getTokenInfo(authorization);
        } catch (Exception e) {
            log.warn("校验token异常->[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.AUTHORIZATION_INVALID);
        }

        // 向headers中放文件
        /*ServerHttpRequest httpRequest = exchange.getRequest().mutate().header("tokenInfo", tokenInfo==null?"":tokenInfo.toString()).build();
        ServerWebExchange webExchange = exchange.mutate().request(httpRequest).build();
        webExchange.getAttributes().put("tokenInfo", tokenInfo);*/
        exchange.getAttributes().put("tokenInfo", tokenInfo);
        // 放行,请求继续向下传递，进入下一个过滤器
        return chain.filter(exchange);
    }

    /**
     * 定义当前过滤器的优先级。值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    private TokenInfo getTokenInfo(String authorization) {
        String token = authorization.substring("bearer ".length());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(MDA.clientId, MDA.clientSecret);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(MDA.checkTokenUrl, HttpMethod.POST, httpEntity, TokenInfo.class);
        log.info("token info->[{}]", responseEntity.getBody());

        return responseEntity.getBody();
    }

}
