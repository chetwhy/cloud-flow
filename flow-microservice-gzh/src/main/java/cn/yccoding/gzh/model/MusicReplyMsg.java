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
 * 被动回复的消息实体 音乐
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Accessors(chain = true)
public class MusicReplyMsg {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private final String msgType = MsgType.MUSIC;

    // 消息标题
    @XmlElement(name = "Title")
    private String title;

    // 消息描述
    @XmlElement(name = "Description")
    private String description;

    // 音乐链接
    @XmlElement(name = "MusicURL")
    private String musicURL;

    // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
    @XmlElement(name = "HQMusicUrl")
    private String hQMusicUrl;

    // 缩略图的媒体id
    @XmlElement(name = "ThumbMediaId")
    private String thumbMediaId;

}
