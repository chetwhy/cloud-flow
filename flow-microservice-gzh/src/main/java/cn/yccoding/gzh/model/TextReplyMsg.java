package cn.yccoding.gzh.model;

import cn.yccoding.common.adapter.CDataAdapter;
import cn.yccoding.gzh.constant.MsgType;
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
 * 被动回复的消息实体 文本
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Accessors(chain = true)
public class TextReplyMsg{

    @XmlJavaTypeAdapter(CDataAdapter.class)
    @XmlElement(name = "ToUserName")
    private String toUserName;


    @XmlJavaTypeAdapter(CDataAdapter.class)
    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;


    @XmlJavaTypeAdapter(CDataAdapter.class)
    @XmlElement(name = "MsgType")
    private final String msgType = MsgType.TEXT;


    @XmlJavaTypeAdapter(CDataAdapter.class)
    @XmlElement(name = "Content")
    private String content;

}
