package cn.yccoding.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/list")
    public RespResult listUsers() {
        User user1 = new User(1, "aaa", 12, "apple");
        User user2 = new User(2, "bbb", 10, "amazon");
        User user3 = new User(3, "ccc", 15, "mi");
        User user4 = new User(4, "ddd", 16, "face");
        List<User> users = Arrays.asList(user1, user2, user3, user4);

        Map<String, Object> data = new HashMap<>();
        data.put("items", users);
        RespResult result = new RespResult(20000, "请求成功", data);
        return result;
    }

    static class RespResult {
        private long code;
        private String message;
        private Map<String, Object> data;

        public RespResult() {
        }

        public RespResult(long code, String message, Map<String, Object> data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public long getCode() {
            return code;
        }

        public void setCode(long code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }
    }

    static class User{
        private Integer id;
        private String username;
        private Integer age;
        private String company;

        public User() {
        }

        public User(Integer id, String username, Integer age, String company) {
            this.id = id;
            this.username = username;
            this.age = age;
            this.company = company;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }
}
