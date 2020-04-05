package cn.yccoding.mail.service;

import cn.yccoding.mail.form.MailForm;

/**
 * @Author YC
 * @create 2020/4/4
 */
public interface MailService {
    /**
     * 发送简单邮件
     * @param form
     */
    void sendSimpleMail(MailForm form);

    /**
     * 发送html邮件
     * @param form
     */
    void sendHtmlMail(MailForm form);

    /**
     * 发送模板邮件
     * @param form
     */
    void sendTemplateMail(MailForm form);
}
