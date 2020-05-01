package cn.yccoding.mail.form.sendgrid;

import com.sendgrid.ASM;
import com.sendgrid.Email;
import com.sendgrid.MailSettings;
import com.sendgrid.TrackingSettings;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author YC
 * @since 2020/4/21 17:41
 * 邮件发送的通用属性
 */
@Data
public class MailRequestForm {
    // 收件人
    private ReceiveUser receiveUsers;

    // 邮件主题
    private String subject;

    // 邮件内容
    private ContentForm contentForm;

    // 附件
    private AttachmentsForm attachmentsForm;

    // 模板
    private String templateId;

    private Map<String,String> section;

    private Map<String,String> header;

    private List<String> categories;

    private Map<String,String> customArgs;

    private ASM asm;

    private String ipPoolId;

    private MailSettings mailSettings;

    private TrackingSettings trackingSettings;

    private Email replyTo;
}
