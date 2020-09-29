package cn.yccoding.payment.alipay.model;

import cn.yccoding.payment.alipay.sdk.model.GoodsDetail;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * model
 *
 * @author YC
 * @since 2020/9/29
 */
@Data
public class TradePaySummary implements Serializable {
    /**
     * 订单标题
     */
    private String subject;

    /**
     * 商户门店编号
     */
    private String storeId;

    /**
     * 商户操作员编号
     */
    private String operatorId;

    private List<Item> items;

    @Data
    public static class Item implements Serializable{
        // 商品编号(国标)
        private String goodsId;

        private String alipayGoodsId;

        // 商品名称
        private String goodsName;

        // 商品数量
        private int quantity;

        // 商品价格，此处单位为元，精确到小数点后2位
        private String price;

        // 商品类别
        private String goodsCategory;

        // 商品详情
        private String body;
    }
}
