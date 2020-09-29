package cn.yccoding.member.config.interceptor;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.vo.R;
import cn.yccoding.member.config.JwtKit;
import cn.yccoding.member.config.property.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证处理拦截器
 *
 * @author YC
 * @since 2020/9/4
 */
@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtKit jwtKit;

    public final static String GLOBAL_JWT_MEMBER_INFO="jwt:member:info";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入前置拦截器...");
        String authorization = request.getHeader(jwtProperties.getHeader());
        // 1.校验token
        if (StringUtils.isEmpty(authorization)||!authorization.startsWith(jwtProperties.getPrefix())) {
            printUnauthorized(response);
            return false;
        }
        // 2. 去除Bearer前缀
        String token = authorization.substring(jwtProperties.getPrefix().length());
        // 3. 解析
        Claims claims = jwtKit.parse(token);
        if (claims == null) {
            printUnauthorized(response);
            return false;
        }
        request.setAttribute(GLOBAL_JWT_MEMBER_INFO,claims);

        // session校验
        /*if (ObjectUtils.isEmpty(request.getSession().getAttribute("member"))) {
            printUnauthorized(response);
            return false;
        }*/
        return true;
    }

    /**
     * 该部分没有进入到mvc，无法使用自定义异常，需要从response返回
     *
     * @param response
     * @throws Exception
     */
    protected void printUnauthorized(HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        String result = new ObjectMapper().writeValueAsString(R.setResult(ResultCodeEnum.UNAUTHORIZED));
        response.getWriter().print(result);
    }
}
