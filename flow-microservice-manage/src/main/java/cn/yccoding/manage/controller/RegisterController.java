package cn.yccoding.manage.controller;

import cn.yccoding.common.contants.MqConstant;
import cn.yccoding.common.vo.R;
import cn.yccoding.manage.entity.User;
import cn.yccoding.common.form.UserForm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author YC
 * @create 2020/4/5
 */
@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 模拟注册，测试消息队列和邮件发送
     */
    @PostMapping
    public R mockRegister(@RequestBody UserForm form) {
        User user = new User();
        BeanUtils.copyProperties(form,user);
        user.setId(UUID.randomUUID().toString().replace("-","").toUpperCase());

        // 邮件通知
        rabbitTemplate.convertAndSend(MqConstant.MAIL_REGISTER_EXCHANGE,MqConstant.MAIL_REGISTER_ROUTING_KEY,form);

        // TODO 其他操作

        return R.ok().message("注册成功,注册信息将邮件通知");
    }
}
