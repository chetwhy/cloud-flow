package cn.yccoding.member.config;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.member.config.property.JwtProperties;
import cn.yccoding.member.domain.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author YC
 * @since 2020/9/5
 */
public class JwtKit {

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 生成jwt token
     *
     * iss (issuer)：签发人
     * exp (expiration time)：过期时间
     * sub (subject)：主题
     * aud (audience)：受众
     * nbf (Not Before)：生效时间
     * iat (Issued At)：签发时间
     * jti (JWT ID)：编号
     * @return
     */
    public String generate(Member member) {
        Map<String, Object> claims = new HashMap<>(4);
        // payload消息题
        claims.put("sub", member.getUsername());
        claims.put("iat", LocalDateTime.now());
        claims.put("id", member.getId());
        claims.put("memberLevelId", member.getMemberLevelId());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 解析jwt token
     *
     * @return
     */
    public Claims parse(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ResultCodeEnum.JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ResultCodeEnum.JWT_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new CustomException(ResultCodeEnum.JWT_MALFORMED);
        } catch (SignatureException e) {
            throw new CustomException(ResultCodeEnum.JWT_SIGNATURE);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ResultCodeEnum.JWT_ILLEGAL_ARGUMENT);
        }
        return claims;
    }
}
