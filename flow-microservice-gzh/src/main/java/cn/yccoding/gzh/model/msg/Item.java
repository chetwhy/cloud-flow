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
public class Item {

    // 消息标题
    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String title;

    // 消息描述
    @XmlElement(name = "Description")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String description;

    // 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    @XmlElement(name = "PicUrl")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String picUrl;

    // 点击图文消息跳转链接
    @XmlElement(name = "Url")
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String url;
}
