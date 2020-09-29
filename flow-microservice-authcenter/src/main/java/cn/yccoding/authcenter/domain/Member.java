package cn.yccoding.authcenter.domain;

import lombok.Data;

/**
 * 会员
 *
 * @author YC
 * @since 2020/9/26
 */
@Data
public class Member {

    private String id;

    private String username;

    private String password;

    private String nickname;

    private String phone;
}
