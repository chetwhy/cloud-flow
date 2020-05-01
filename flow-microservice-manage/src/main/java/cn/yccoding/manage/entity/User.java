package cn.yccoding.manage.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author YC
 * @create 2020/4/5
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private String id;

    private String username;

    private String password;

    private String email;
}
