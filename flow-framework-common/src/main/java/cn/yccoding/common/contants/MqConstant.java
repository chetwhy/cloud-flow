package cn.yccoding.common.contants;

/**
 * @Author YC
 * @create 2020/4/4
 */
public class MqConstant {
    // 邮件消息队列
    public static final String MAIL_REGISTER_QUEUE = "mail.register.queue";
    public static final String MAIL_REGISTER_EXCHANGE = "mail.register.exchange";
    public static final String MAIL_REGISTER_ROUTING_KEY = "register.mail";

    // send grid
    public static final String MAIL_SEND_GRID_QUEUE = "mail.sendgrid.queue";
    public static final String MAIL_SEND_GRID_EXCHANGE = "mail.sendgrid.exchange";
    public static final String MAIL_SEND_GRID_ROUTING_KEY = "sendgrid.mail";
}
