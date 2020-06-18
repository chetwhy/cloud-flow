package cn.yccoding.order.controller;

import cn.yccoding.common.vo.R;
import cn.yccoding.order.form.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author YC
 * @since 2020/6/18 14:41
 * 用户控制器，模拟登录
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    //用户登录接口
    @PostMapping("login")
    public R login(@RequestBody LoginUser user){
        return R.ok().data("token", "admin");
    }

    //获取用户信息
    @GetMapping("info")
    public R info(@RequestParam String token){
        return R.ok()
                .data("roles", "admin")
                .data("name", "admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //用户退出
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
