package cn.yccoding.gzh.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author YC
 * @create 2020/3/6
 * 接受普通消息实体
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RcvCommonMsg {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private String msgType;

    @XmlElement(name = "MsgId")
    private Long msgId;

    @XmlElement(name = "Content")
    private String content;

    // 图片链接
    @XmlElement(name = "PicUrl")
    private String picUrl;

    // 语音格式
    @XmlElement(name = "Format")
    private String format;

    // 语音识别结果
    @XmlElement(name = "Recognition")
    private String recognition;

    // 媒体id
    @XmlElement(name = "MediaId")
    private String mediaId;

    // 缩略图的媒体id
    @XmlElement(name = "ThumbMediaId")
    private String thumbMediaId;

    // 地理位置维度
    @XmlElement(name = "Location_X")
    private String locationX;

    // 地理位置经度
    @XmlElement(name = "Location_Y")
    private String locationY;

    // 地图缩放大小
    @XmlElement(name = "Scale")
    private String scale;

    // 地理位置信息
    @XmlElement(name = "Label")
    private String label;

    // 消息标题
    @XmlElement(name = "Title")
    private String title;

    // 消息描述
    @XmlElement(name = "Description")
    private String description;

    // 消息链接
    @XmlElement(name = "Url")
    private String url;

}
