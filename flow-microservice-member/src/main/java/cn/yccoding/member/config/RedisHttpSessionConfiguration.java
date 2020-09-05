package cn.yccoding.member.config;


import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * spring session redis配置
 * 引入分布式会话体系，会话内容存储在redis中
 *
 * @author YC
 * @since 2020/9/5
 */
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class RedisHttpSessionConfiguration {

}
