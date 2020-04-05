package cn.yccoding.mail;

import cn.yccoding.mail.form.MailForm;
import cn.yccoding.mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YC
 * @create 2020/4/4
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendSampleMail() {
        // 简单邮件类
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 寄件人，默认是配置的username
        mailMessage.setFrom(mailProperties.getUsername());
        // 收件人，支持多个收件人
        mailMessage.setTo("2633357327@qq.com");
        // 邮件主题
        mailMessage.setSubject("Test simple mail");
        // 邮件的文本信息
        mailMessage.setText("Hello this is test mail from java");

        // 发送邮件
        mailSender.send(mailMessage);
    }

    @Test
    public void sendSimpleMail() {
        List<String> to = new ArrayList<>();
        to.add("2633357327@qq.com");
        MailForm mail = new MailForm().setTo(to).setSubject("Test simple mail").setText("Hello this is test mail from java");
        mailService.sendSimpleMail(mail);
        System.out.println("发送完成");
    }

    @Test
    public void sendHtmlMail() {
        String html = "<html>\n" +
                "<body>\n" +
                "<h1>Hello this is test mail from java!</h1>\n" +
                "</body>\n" +
                "</html>";
        List<String> to = new ArrayList<>();
        to.add("2633357327@qq.com");
        List<String> paths = new ArrayList<>();
        paths.add("C:\\Users\\YC\\Desktop\\aaa.txt");
        paths.add("C:\\Users\\YC\\Desktop\\bbb.txt");
        MailForm mail = new MailForm().setTo(to).setSubject("Test html mail").setText(html).setAttachmentPath(paths);
        mailService.sendHtmlMail(mail);
        System.out.println("发送成功");
    }

    @Test
    public void sendTemplateMail() {
        List<String> to = new ArrayList<>();
        to.add("2633357327@qq.com");
        Map<String, Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","123123");
        MailForm mail = new MailForm().setTo(to).setSubject("Test simple mail")
                .setContextVar(map).setTemplateName("register");
        mailService.sendTemplateMail(mail);
        System.out.println("发送完成");
    }
}
