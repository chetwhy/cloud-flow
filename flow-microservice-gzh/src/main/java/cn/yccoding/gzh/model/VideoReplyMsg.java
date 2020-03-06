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
 * 被动回复的消息实体 视频
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Accessors(chain = true)
public class VideoReplyMsg {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private final String msgType= MsgType.VIDEO;

    // 通过素材管理中的接口上传多媒体文件，得到的id
    @XmlElement(name = "MediaId")
    private String mediaId;

    // 消息标题
    @XmlElement(name = "Title")
    private String title;

    // 消息描述
    @XmlElement(name = "Description")
    private String description;

}
