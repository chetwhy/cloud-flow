package cn.yccoding.order.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YC
 * @since 2020/6/5 18:25
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    User user1 = new User(1, "apple", 12, "aaa");
    User user2 = new User(2, "amazon", 16, "bbb");
    User user3 = new User(3, "mi", 18, "ccc");
    User user4 = new User(4, "face", 5, "ddd");

    // 默认订单数据集合
    public List<User> users = Arrays.asList(user1, user2, user3, user4);

    /**
     * 获取所有订单数据
     */
    @GetMapping("/list")
    public RespResult listUsers() {
        Map<String, Object> data = new HashMap<>();
        data.put("items", users);
        RespResult result = new RespResult(20000, "请求成功", data);
        return result;
    }

    /**
     * 获取指定id订单
     */
    @GetMapping("/{id}")
    public RespResult findById(@PathVariable Integer id) {
        User user = this.users.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
        Map<String, Object> data = new HashMap<>();
        data.put("item", user);
        RespResult result = new RespResult(20000, "请求成功", data);
        return result;
    }

    /**
     * 通用结果类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class RespResult {
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
    static class User {
        private Integer id;
        private String product;
        private Integer total;
        private String description;
    }
}
