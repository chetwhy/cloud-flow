package cn.yccoding.ucenter.form;

import cn.yccoding.ucenter.bean.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

/**
 * 用户注册表单
 */
@Data
public class RegistrationForm {

  private String username;
  private String password;
  private String fullname;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String phone;

  // 密码加密
  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(
        username, passwordEncoder.encode(password),
        fullname, street, city, state, zip, phone);
  }
  
}
