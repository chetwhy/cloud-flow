package cn.yccoding.ucenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author YC
 * @create 2020/5/1
 */
@Controller
public class LoginController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "welcome cloud flow";
    }

}
