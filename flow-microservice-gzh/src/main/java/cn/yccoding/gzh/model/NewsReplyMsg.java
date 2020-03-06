package cn.yccoding.gzh.model;

import cn.yccoding.gzh.constant.MsgType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author YC
 * @create 2020/3/6
 * 被动回复的消息实体 图文
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Accessors(chain = true)
public class NewsReplyMsg {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private final String msgType = MsgType.NEWS;

    @XmlElement(name = "Content")
    private String content;

    // 消息标题
    @XmlElement(name = "Title")
    private String title;

    // 消息描述
    @XmlElement(name = "Description")
    private String description;

    // 图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
    @XmlElement(name = "ArticleCount")
    private String articleCount;

    // 图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数
    @XmlElement(name = "Articles")
    private String articles;

    // 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    @XmlElement(name = "PicUrl")
    private String picUrl;

    // 点击图文消息跳转链接
    @XmlElement(name = "Url")
    private String url;

}
