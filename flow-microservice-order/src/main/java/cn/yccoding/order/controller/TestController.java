package cn.yccoding.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YC
 * @since 2020/6/11 17:43
 */
@CrossOrigin
@Slf4j
@Controller
@RequestMapping("/api/v1/orders")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/aaa")
    public void testRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "http://www.yccoding2.cn";
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        log.info("测试后端跳转:[{}]",url);
        response.sendRedirect(url);
        return;
    }
}
