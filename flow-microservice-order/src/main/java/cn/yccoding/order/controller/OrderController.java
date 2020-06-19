package cn.yccoding.order.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import cn.yccoding.common.vo.R;
import cn.yccoding.order.form.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * @author YC
 * @since 2020/6/5 18:25
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
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
    public R listUsers() {
        Map<String, Object> data = new HashMap<>();
        data.put("items", orders);
        return R.ok().data(data);
    }

    /**
     * 获取指定id订单
     */
    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {
        Order order = this.orders.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
        Map<String, Object> data = new HashMap<>();
        data.put("item", order);
        return R.ok().data(data);
    }

    @PutMapping("/{id}")
    public R updateById(@PathVariable Integer id, @RequestBody(required = false) Order order) {
        log.info("准备更新订单...[{}]", order);
        if (order == null) {
            return R.ok().message("更新成功null");
        }
        for (Order o : orders) {
            if (o.getId() == id) {
                BeanUtils.copyProperties(order, o);
                break;
            }
        }
        return R.ok().message("更新成功");
    }

    @PostMapping("/aaa")
    public R save(@RequestBody(required = false) Order order) {
        log.info("准备新增订单...[{}]", order);
        return R.ok().message("保存成功");
    }

}
