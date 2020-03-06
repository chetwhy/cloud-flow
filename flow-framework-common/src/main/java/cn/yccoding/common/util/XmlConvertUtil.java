package cn.yccoding.common.util;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @Author YC
 * @create 2020/2/3 xml工具类
 */
public class XmlConvertUtil {

    /**
     * transfer xml to bean
     *
     * @param xml
     *            待转化的xml字符串
     * @param c
     *            转化后的类
     * @param <T>
     *            转化后类类型
     * @return 转化后的类对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T)unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * transfer bean to xml
     *
     * @param obj
     *            待转化类对象
     * @param clazz
     *            待转化类
     * @return 转化后的xml字符串
     * @throws JAXBException
     */
    public static String beanToXml(Object obj, java.lang.Class<?> clazz) throws JAXBException {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                @Override
                public void escape(char[] chars, int start, int length, boolean isAttVal, Writer writer) throws IOException {
                    writer.write(chars,start,length);
                }
            });
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("JAXBException happened.");
        }
        return result;
    }

}
