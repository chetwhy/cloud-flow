package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : Chet
 * @description : 场景信息，统一下单使用
 * @date : 2019/10/29
 */
@Data
public class SceneInfo {

    private String id;

    private String name;

    @JSONField(name = "area_code")
    private String areaCode;

    private String address;
}
