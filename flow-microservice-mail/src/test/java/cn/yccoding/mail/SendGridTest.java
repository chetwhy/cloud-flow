package cn.yccoding.mail;

import com.sendgrid.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;

/**
 * @Author YC
 * @create 2020/5/1
 * 测试类
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class SendGridTest {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testWebApi() {
        // 发件人，即配置的发件人邮箱地址
        Email from = new Email("test@example.com");
        // 邮件主题
        String subject = "Sending with SendGrid is Fun";
        // 收件人邮件地址
        Email to = new Email("test@example.com");
        // 邮件内容
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);
        // 配置API Keys的密钥
        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void sendSMTPRelay() {
        // 简单邮件类
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 寄件人，默认是配置的username
        mailMessage.setFrom("test@example.com");
        // 收件人，支持多个收件人
        mailMessage.setTo("test@example.com");
        // 邮件主题
        mailMessage.setSubject("Test SMTP Relay mail");
        // 邮件的文本信息
        mailMessage.setText("Hello this is test mail from java 2");

        // 发送邮件
        mailSender.send(mailMessage);
    }
}
