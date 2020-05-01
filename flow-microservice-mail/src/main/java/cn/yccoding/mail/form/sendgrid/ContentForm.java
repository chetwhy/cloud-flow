package cn.yccoding.mail.form.sendgrid;

import lombok.Data;

/**
 * @author YC
 * @since 2020/4/21 18:27
 * 邮件内容
 */
@Data
public class ContentForm {

    // 内容类型，html,txt
    private String type;

    // 内容
    private String value;
}
