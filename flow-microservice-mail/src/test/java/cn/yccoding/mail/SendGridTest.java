package cn.yccoding.mail;

import cn.yccoding.mail.form.MailForm;
import com.sendgrid.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

/**
 * @Author YC
 * @create 2020/5/1
 * 测试类
 */
@ActiveProfiles("dev")
@SpringBootTest
@RunWith(SpringRunner.class)
public class SendGridTest {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * web api方式发送邮件
     */
    @Test
    public void testWebApi() {
        Map<String, Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","123123");
        Context context = new Context();
        context.setLocale(Locale.CHINA);
        context.setVariables(map);
        String templateMail = templateEngine.process("register", context);

        // 发件人，即配置的发件人邮箱地址
        Email from = new Email("test@example.com");
        // 邮件主题
        String subject = "Sending with SendGrid is Fun";
        // 收件人邮件地址
        Email to = new Email("test@example.com");
        // 邮件内容
        Content content = new Content("text/html", templateMail);
        Mail mail = new Mail(from, subject, to, content);
        // 配置API Keys的密钥
        SendGrid sg = new SendGrid("SG.xxxx");
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

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * smtp方式发送邮件
     * @throws MessagingException
     */
    @Test
    public void sendSMTPRelayHtml() throws MessagingException {
        String html = "<html>\n" +
                "<body>\n" +
                "<h1>Hello this is test mail from java!</h1>\n" +
                "</body>\n" +
                "</html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("yuan691409320@gmail.com");
        messageHelper.setTo("chao.yuan2@pacteraedge.com");
        messageHelper.setSubject("test");
        messageHelper.setText(html, true);

        // 发送邮件
        mailSender.send(mimeMessage);
    }

    /**
     * 测试stmp发送thyleaf邮件
     * @throws MessagingException
     */
    @Test
    public void sendSMTPRelayHtml2() throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","123123");
        Context context = new Context();
        context.setLocale(Locale.CHINA);
        context.setVariables(map);
        String templateMail = templateEngine.process("register", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("test@example.com");
        messageHelper.setTo("test@example.com");
        messageHelper.setSubject("test");
        messageHelper.setText(templateMail, true);

        // 发送邮件
        mailSender.send(mimeMessage);
    }
}
