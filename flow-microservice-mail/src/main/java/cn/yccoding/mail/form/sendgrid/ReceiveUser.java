package cn.yccoding.mail.form.sendgrid;

import java.util.List;

import lombok.Data;

/**
 * @author YC
 * @since 2020/4/21 17:31 收件人邮箱信息
 */
@Data
public class ReceiveUser {

    // 收件人
    private List<MailInfoForm> toUser;

    // 抄送
    private List<MailInfoForm> ccUser;

    private List<MailInfoForm> bccUser;

}
