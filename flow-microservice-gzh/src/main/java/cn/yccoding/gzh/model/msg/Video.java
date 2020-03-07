package cn.yccoding.gzh.model.msg;

import cn.yccoding.common.adapter.CDataAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @Author YC
 * @create 2020/3/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Video {

    // 通过素材管理中的接口上传多媒体文件，得到的id
    @XmlElement(name = "MediaId")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String mediaId;

    // 消息标题
    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String title;

    // 消息描述
    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String description;
}
