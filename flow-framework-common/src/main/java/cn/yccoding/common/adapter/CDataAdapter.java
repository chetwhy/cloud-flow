package cn.yccoding.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @Author YC
 * @create 2020/3/7
 * cdata适配器
 */
public class CDataAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return new StringBuilder("<![CDATA[").append(v).append("]]>").toString();
    }
}
