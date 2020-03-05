package cn.yccoding.gzh.controller;

import cn.yccoding.gzh.service.CommonGzhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import cn.yccoding.common.vo.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YC
 * @create 2020/3/6
 */
@RestController
@RequestMapping("/api/v1")
public class HelloController {
    @Autowired
    private CommonGzhService commonGzhService;

    @GetMapping("/hello")
    public R testResult() {
        String token = commonGzhService.getRedisAccessToken();
        return R.ok().data("k1",token);
    }
}
