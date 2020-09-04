package cn.yccoding.member.controller;

import cn.yccoding.common.vo.R;
import cn.yccoding.member.object.RegisterQo;
import cn.yccoding.member.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会员登录控制器
 *
 * @author YC
 * @since 2020/9/3
 */
@RestController
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    private SsoService ssoService;

    @GetMapping("/optCode")
    public ResponseEntity<R> getOptCode(@RequestParam String phone) {
        String optCode = ssoService.getOtpCode(phone);
        return R.ok().data("optCode", optCode).buildResponseEntity();
    }

    public ResponseEntity<R> register(RegisterQo registerQo) {

        return null;
    }
}
