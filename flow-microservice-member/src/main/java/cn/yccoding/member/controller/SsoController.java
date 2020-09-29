package cn.yccoding.member.controller;

import cn.yccoding.common.vo.R;
import cn.yccoding.member.config.JwtKit;
import cn.yccoding.member.config.property.JwtProperties;
import cn.yccoding.member.domain.entity.Member;
import cn.yccoding.member.object.RegisterQo;
import cn.yccoding.member.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * 会员登录控制器
 *
 * @author YC
 * @since 2020/9/3
 */
@Slf4j
@RestController
@RequestMapping("/sso")
public class SsoController extends BaseController {
    @Autowired
    private SsoService ssoService;

    @Autowired
    private JwtKit jwtKit;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/optCode")
    public ResponseEntity<R> getOptCode(@RequestParam String phone) {
        String optCode = ssoService.getOtpCode(phone);
        return R.ok().data("optCode", optCode).buildResponseEntity();
    }

    /**
     * 获取注册信息
     * @param registerQo
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<R> register(@Valid @RequestBody RegisterQo registerQo) {
        ssoService.register(registerQo);
        return R.ok().message("注册成功").buildResponseEntity();
    }

    //@GetMapping("/login")
    public ResponseEntity<R> sessionLogin(String username, String password) {
        Member member = ssoService.login(username, password);
        // 登录记录session
        getHttpSession().setAttribute("member", member);
        // TODO 返回member的vo类
        return R.ok().message("登录成功").data("username", member.getUsername()).buildResponseEntity();
    }

    @GetMapping("/login")
    public ResponseEntity<R> jwtLogin(String username, String password) {
        Member member = ssoService.login(username, password);
        // 返回jwt
        HashMap<String, String> map = new HashMap<>(2);
        map.put("token", jwtKit.generate(member));
        map.put("header", jwtProperties.getPrefix());
        return R.ok().message("登录成功").data("jwt", map).buildResponseEntity();
    }
}
