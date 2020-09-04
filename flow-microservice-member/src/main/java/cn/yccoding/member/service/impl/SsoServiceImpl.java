package cn.yccoding.member.service.impl;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.member.config.property.RedisKeyPrefixConfig;
import cn.yccoding.member.domain.entity.Member;
import cn.yccoding.member.domain.service.MemberService;
import cn.yccoding.member.object.RegisterQo;
import cn.yccoding.member.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 会员登录实现类
 *
 * @author YC
 * @since 2020/9/3
 */
@Slf4j
@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private RedisKeyPrefixConfig redisKeyPrefixConfig;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getOtpCode(String phone) {
        // 1 当前用户是否注册
        List<Member> members = memberService.getByPhone(phone);
        if (!members.isEmpty()) {
            throw new CustomException(ResultCodeEnum.USER_EXISTED);
        }
        // 2 60s验证码唯一且有效
        String key = redisKeyPrefixConfig.getPrefix().getOtpCode() + phone;
        if (redisTemplate.hasKey(key)) {
            //throw new CustomException(ResultCodeEnum.OPTCODE_EFFECTIVE);
            log.info("验证码未过期：phone=[{}],optCode=[{}]");
            return (String) redisTemplate.opsForValue().get(key);
        }
        // 3 返回验证码
        Random random = new Random();
        StringBuffer optCode = new StringBuffer();
        final int optSize = 6;
        for (int i = 0; i < optSize; i++) {
            optCode.append(random.nextInt(10));
        }
        log.info("新生成验证码：phone=[{}],optCode=[{}]", phone, optCode);
        // 1min过期
        long expire = redisKeyPrefixConfig.getExpire().getOtpCode();
        redisTemplate.opsForValue().set(key, optCode.toString(), expire, TimeUnit.SECONDS);

        return optCode.toString();
    }

    @Override
    public boolean register(RegisterQo registerQo) {
        return false;
    }
}
