package cn.yccoding.mail.service.impl;

import cn.yccoding.mail.config.ConstantProperties;
import cn.yccoding.mail.form.sendgrid.ContentForm;
import cn.yccoding.mail.form.sendgrid.MailInfoForm;
import cn.yccoding.mail.form.sendgrid.MailRequestForm;
import cn.yccoding.mail.form.sendgrid.ReceiveUser;
import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @Author YC
 * @create 2020/5/1
 * send grid 邮件服务
 */
@Slf4j
@Service
public class SendGridService {

    /**
     * 发送邮件
     */
    public boolean sendMail(MailRequestForm requestForm) {
        SendGrid sg = new SendGrid(ConstantProperties.API_KEY);
        Request request = new Request();

        Mail mail = buildMail(requestForm);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("邮件发送结果: status code=[{}], body=[{}], headers= [{}]", response.getStatusCode(),
                    response.getBody(), response.getHeaders());
        } catch (IOException e) {
            log.error("邮件发送失败:[{}]", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 封装邮件参数
     */
    private Mail buildMail(MailRequestForm requestForm) {
        Mail mail = new Mail();

        // from
        Email fromEmail = new Email();
        fromEmail.setName(ConstantProperties.FROM_NAME);
        fromEmail.setEmail(ConstantProperties.FROM_EMAIL);
        mail.setFrom(fromEmail);

        // subject
        mail.setSubject(requestForm.getSubject());

        // personalization
        Personalization personalization = new Personalization();
        personalization.setSubject(requestForm.getSubject());
        ReceiveUser receiveUsers = requestForm.getReceiveUsers();
        List<MailInfoForm> toUserList = receiveUsers.getToUser();
        if (toUserList != null && !toUserList.isEmpty()) {
            Email to = new Email();
            toUserList.stream().forEach(i -> {
                to.setName(i.getName());
                to.setEmail(i.getEmail());
                // cc,bcc... 暂只使用直接发送
                personalization.addTo(to);
            });
        }
        mail.addPersonalization(personalization);

        // content
        ContentForm contentForm = requestForm.getContentForm();
        Content content = new Content();
        content.setType(contentForm.getType());
        content.setValue(contentForm.getValue());
        mail.addContent(content);

        // timestamp
        mail.setSendAt(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));

        return mail;
    }
}
