package cn.yccoding.gzh.model;

import cn.yccoding.common.adapter.CDataAdapter;
import cn.yccoding.gzh.constant.MsgType;
import cn.yccoding.gzh.model.msg.Video;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String toUserName;

    @XmlElement(name = "FromUserName")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private final String msgType= MsgType.VIDEO;

    @XmlElement(name = "Video")
    private Video video;

}
