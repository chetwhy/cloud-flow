package cn.yccoding.member.object;

import lombok.Data;

/**
 * 注册
 *
 * @author YC
 * @since 2020/9/4
 */
@Data
public class RegisterQo {
    private String username;

    private String password;

    private String phone;

    private String otpCode;
}
