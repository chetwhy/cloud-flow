package cn.yccoding.authcenter.component;

import cn.yccoding.authcenter.domain.Member;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt自定义增强器
 *
 * @author YC
 * @since 2020/9/25
 */
public class MyTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        final Map<String, String> additionalInfo = new HashMap<>();
        final Map<String, Object> retMap = new HashMap<>();

        return null;
    }
}
