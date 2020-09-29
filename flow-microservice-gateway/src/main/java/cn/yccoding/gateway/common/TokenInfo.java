package cn.yccoding.gateway.common;

import lombok.Data;

import java.util.Date;

/**
 * token info
 *
 * @author YC
 * @since 2020/9/25
 */
@Data
public class TokenInfo {
    private boolean active;

    private String client_id;

    private String[] scope;

    private String user_name;

    private String[] aud;

    private Date exp;

    private String[] authorities;
}
