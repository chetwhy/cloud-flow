package cn.yccoding.order.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YC
 * @since 2020/6/18 14:24
 * 订单类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private String product;
    private Integer total;
    private String description;
}
