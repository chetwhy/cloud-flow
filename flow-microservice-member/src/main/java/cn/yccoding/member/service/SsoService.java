package cn.yccoding.member.service;

import cn.yccoding.member.domain.entity.Member;
import cn.yccoding.member.object.RegisterQo;

/**
 * 会员登录服务类
 *
 * @author YC
 * @since 2020/9/3
 */
public interface SsoService {

    /**
     * 注册会员前，获取验证码
     *
     * @param phone
     * @return
     */
    String getOtpCode(String phone);

    /**
     * 会员注册
     */
    boolean register(RegisterQo registerQo);

    /**
     * 会员登录
     * @param username
     * @param password
     * @return
     */
    Member login(String username, String password);
}
