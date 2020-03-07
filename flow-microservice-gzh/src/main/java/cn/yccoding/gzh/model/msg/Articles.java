package cn.yccoding.gzh.model.msg;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author YC
 * @create 2020/3/7
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Articles {

    @XmlElement(name = "item")
    private Item item;
}
