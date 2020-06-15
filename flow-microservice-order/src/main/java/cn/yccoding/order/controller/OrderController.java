package cn.yccoding.order.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YC
 * @since 2020/6/5 18:25
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    Order order1 = new Order(1, "apple", 12, "aaa");
    Order order2 = new Order(2, "amazon", 16, "bbb");
    Order order3 = new Order(3, "mi", 18, "ccc");
    Order order4 = new Order(4, "face", 5, "ddd");

    // 默认订单数据集合
    public List<Order> orders = Arrays.asList(order1, order2, order3, order4);

    /**
     * 获取所有订单数据
     */
    @GetMapping("/list")
    public RespResult listUsers() {
        Map<String, Object> data = new HashMap<>();
        data.put("items", orders);
        RespResult result = new RespResult(20000, "请求成功", data);
        return result;
    }

    /**
     * 获取指定id订单
     */
    @GetMapping("/{id}")
    public RespResult findById(@PathVariable Integer id) {
        Order order = this.orders.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
        Map<String, Object> data = new HashMap<>();
        data.put("item", order);
        RespResult result = new RespResult(20000, "请求成功", data);
        return result;
    }

    @PutMapping("/{id}")
    public RespResult updateById(@PathVariable Integer id, @RequestBody Order order) {
        for (Order o : orders) {
            if (o.id == id) {
                BeanUtils.copyProperties(order, o);
                break;
            }
        }
        RespResult result = new RespResult(20000, "更新成功", null);
        return result;
    }

    @PostMapping
    public RespResult save(@RequestBody Order order) {
        orders.add(order);
        RespResult result = new RespResult(20000, "保存成功", null);
        return result;
    }

    /**
     * 通用结果类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class RespResult {
        private long code;
        private String message;
        private Map<String, Object> data;
    }

    /**
     * 订单类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Order {
        private Integer id;
        private String product;
        private Integer total;
        private String description;
    }
}
