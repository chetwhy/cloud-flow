package cn.yccoding.mail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yccoding.common.util.CommonUtil;
import cn.yccoding.mail.form.sendgrid.ContentForm;
import cn.yccoding.mail.form.sendgrid.MailInfoForm;
import cn.yccoding.mail.form.sendgrid.MailRequestForm;
import cn.yccoding.mail.form.sendgrid.ReceiveUser;
import cn.yccoding.mail.service.RedisService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author YC
 * @create 2020/5/1 验证码服务
 */
@Service
@Slf4j
public class CaptchaService {

    private static final Integer SEED = 6;
    private static final String TEXT_CONTENT = "text/plain";
    private static final String HTML_CONTENT = "text/html";
    private static long EXPIRED_TIME = 1 * 60L;
    @Autowired
    private SendGridService sendGridService;
    @Autowired
    private RedisService redisService;

    public boolean sendCaptcha(ReceiveUser receiveUser) {
        // to user
        MailInfoForm toUserForm = receiveUser.getToUser().get(0);

        // 准备邮箱内容
        MailRequestForm requestForm = new MailRequestForm();
        requestForm.setReceiveUsers(receiveUser);

        // 验证码
        requestForm.setSubject("验证码");
        StringBuffer content = new StringBuffer();
        content.append("<html><body>").append("<p>请在30min内输入您的验证码：</p>");

        String key = "Captcha_" + toUserForm.getName();
        String captcha = redisService.get(key);
        if (captcha == null) {
            captcha = CommonUtil.randomAlphaNumeric(SEED);
            // 验证码有效时间1min
            redisService.set(key, captcha, EXPIRED_TIME);
        }

        content.append("<h2>").append(captcha).append("</h2>").append("</body></html>");
        ContentForm contentForm = new ContentForm();
        contentForm.setType(HTML_CONTENT);
        contentForm.setValue(content.toString());

        requestForm.setSubject("ups single window 验证登录");
        requestForm.setReceiveUsers(receiveUser);
        requestForm.setContentForm(contentForm);

        // 发送邮件
        boolean isSuccess = sendGridService.sendMail(requestForm);
        if (!isSuccess) {
            boolean retryResult = retrySendMail(requestForm);
            if (!retryResult) {
                log.error("给用户--[{}]发送邮件错误，重试失败", toUserForm.getName());
                // TODO 更新数据库状态--邮件发送失败
                return false;
            }
        }

        // TODO 更新数据库状态--邮件发送成功

        return true;
    }

    /**
     * 邮件发送重试
     */
    private boolean retrySendMail(MailRequestForm requestForm) {
        final byte retryMaxCount = 10;
        for (byte i = 0; i < retryMaxCount; i++) {
            boolean result = sendGridService.sendMail(requestForm);
            if (result) {
                return true;
            }
        }
        return false;
    }
}
