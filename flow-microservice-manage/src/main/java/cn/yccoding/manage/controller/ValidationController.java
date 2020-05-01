package cn.yccoding.manage.controller;

import cn.yccoding.common.contants.MqConstant;
import cn.yccoding.common.form.UserForm;
import cn.yccoding.common.vo.R;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YC
 * @create 2020/5/1
 * 验证控制器
 */
@RestController
@RequestMapping("/api/v1/validation")
public class ValidationController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 模拟注册，测试消息队列和邮件发送
     */
    @PostMapping
    public R sendCaptcha(@RequestParam String username) {
        // TODO 获取用户名的邮箱,暂时用mock信息
        String email = "test@example.com";
        UserForm userForm = new UserForm();
        userForm.setUsername(username);
        userForm.setEmail(email);

        // 邮件通知
        rabbitTemplate.convertAndSend(MqConstant.MAIL_SEND_GRID_EXCHANGE,MqConstant.MAIL_SEND_GRID_ROUTING_KEY,userForm);

        // TODO 其他操作

        return R.ok().message("验证码已发送到您的邮箱");
    }
}
