package cn.yccoding.mail.form.sendgrid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YC
 * @since 2020/4/21 18:21
 * 邮箱通用信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailInfoForm {

    // 用户名
    private String name;

    // 邮箱地址
    private String email;
}
