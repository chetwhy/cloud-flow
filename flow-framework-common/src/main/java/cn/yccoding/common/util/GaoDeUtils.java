package cn.yccoding.common.util;

import javax.validation.constraints.NotBlank;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 高德地图api
 *
 * 详细API使用见官网： https://lbs.amap.com/api/webservice/guide/api/georegeo
 *
 * @author YC
 * @since 2020/8/31
 */
public class GaoDeUtils {
    /**
     * web服务api key
     */
    public static final String KEY = "26fcb528c74a79f3847f764fde103127";

    /**
     * 地理编码 API 服务地址
     */
    public static final String GEOCODE_URL = "https://restapi.amap.com/v3/geocode/geo";

    public static String geo(String address) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("key", KEY);
        paramMap.put("address", address);
        String respJson = HttpUtil.get(GEOCODE_URL, paramMap);
        return respJson;
    }

    public static String geo(GeoFormDto geoForm) {
        Map<String, Object> paramMap = BeanUtil.beanToMap(geoForm);
        String respJson = HttpUtil.get(GEOCODE_URL, paramMap);
        return respJson;
    }

    public static void main(String[] args) {
        GeoFormDto geoForm = new GeoFormDto();
        geoForm.setAddress("上海市长宁区延安西路2299号上海世贸商城6楼裂变空间P-603");
        geoForm.setCity("上海");
        geoForm.setKey(KEY);
        System.out.println(geo(geoForm));
    }
}

@Data
class GeoForm {
    /**
     * api key
     * not blank 注解只在wvc有效
     */
    @NotBlank(message = "密钥不能为空")
    private String key;

    /**
     * JSON，XML
     */
    private String output = "json";
}

@Data
class GeoFormDto extends GeoForm {

    /**
     * 规则遵循：国家、省份、城市、区县、城镇、乡村、街道、门牌号码、屋邨、大厦，如：北京市朝阳区阜通东大街6号。如果需要解析多个地址的话，请用"|"进行间隔，并且将 batch 参数设置为 true，最多支持 10
     * 个地址进进行"|"分割形式的请求。
     */
    @NotBlank(message = "地址信息不能为空")
    private String address;

    /**
     * 可选输入内容包括：指定城市的中文（如北京）、指定城市的中文全拼（beijing）、citycode（010）、adcode（110000），不支持县级市。当指定城市查询内容为空时，会进行全国范围内的地址转换检索。
     */
    private String city;

    /**
     * batch 参数设置为 true 时进行批量查询操作，最多支持 10 个地址进行批量查询。
     */
    private String batch;
    private String sig;

    /**
     * callback 值是用户定义的函数名称，此参数只在 output 参数设置为 JSON 时有效
     */
    private String callback;
}
