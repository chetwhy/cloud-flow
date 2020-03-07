package cn.yccoding.gzh.model;

import cn.yccoding.common.adapter.CDataAdapter;
import cn.yccoding.gzh.constant.MsgType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * @Author YC
 * @create 2020/3/6
 * 被动回复的消息实体 图片
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Accessors(chain = true)
public class ImgReplyMsg {
    @XmlElement(name = "ToUserName")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String toUserName;

    @XmlElement(name = "FromUserName")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private final String msgType = MsgType.IMAGE;

    // 通过素材管理中的接口上传多媒体文件，得到的id
    @XmlElementWrapper(name = "Image")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    @XmlElement(name = "MediaId")
    private List<String> mediaId;

}

