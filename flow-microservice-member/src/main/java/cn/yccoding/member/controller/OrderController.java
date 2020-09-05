package cn.yccoding.member.controller;

import cn.yccoding.common.vo.R;
import cn.yccoding.member.config.interceptor.AuthHandlerInterceptor;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 *
 * @author YC
 * @since 2020/9/4
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @GetMapping
    public ResponseEntity<R> getOrder() {
        log.info("下订单...");
        // session会话信息
        ///Member member = (Member) getHttpSession().getAttribute("member");
        Claims claims = (Claims) getRequest().getAttribute(AuthHandlerInterceptor.GLOBAL_JWT_MEMBER_INFO);
        return R.ok().message("测试订单调用").data("user",claims.get("sub")).buildResponseEntity();
    }
}
