package cn.yccoding.common.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author YC
 * @create 2020/4/5
 */
@Data
public class UserForm implements Serializable {
    private String username;

    private String password;

    private String email;
}
