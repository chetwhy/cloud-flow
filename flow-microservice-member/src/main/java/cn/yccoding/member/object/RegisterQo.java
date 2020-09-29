package cn.yccoding.member.object;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注册
 *
 * @author YC
 * @since 2020/9/4
 */
@Data
public class RegisterQo {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 4,max = 20,message = "用户名长度必须在4-20字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4,max = 20,message = "密码长度必须在4-20字符之间")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "电话必须是11个字符")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$",message = "电话号码格式不正确")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6,max = 6,message = "验证码必须6个字符")
    private String otpCode;
}
