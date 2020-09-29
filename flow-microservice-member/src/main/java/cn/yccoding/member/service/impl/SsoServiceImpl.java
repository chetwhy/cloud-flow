package cn.yccoding.member.service.impl;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.member.config.property.RedisKeyProperties;
import cn.yccoding.member.domain.entity.Member;
import cn.yccoding.member.domain.service.MemberService;
import cn.yccoding.member.object.RegisterQo;
import cn.yccoding.member.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    private RedisKeyProperties redisKeyProperties;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getOtpCode(String phone) {
        // 1 当前用户是否注册
        List<Member> members = memberService.getByPhone(phone);
        if (!members.isEmpty()) {
            throw new CustomException(ResultCodeEnum.EXISTED_USER);
        }
        // 2 60s验证码唯一且有效
        String key = redisKeyProperties.getPrefix().getOtpCode() + phone;
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
        long expire = redisKeyProperties.getExpire().getOtpCode();
        redisTemplate.opsForValue().set(key, optCode.toString(), expire, TimeUnit.SECONDS);

        return optCode.toString();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean register(RegisterQo registerQo) {
        // 1. 校验验证码
        String key = redisKeyProperties.getPrefix().getOtpCode() + registerQo.getPhone();
        String redisOptCode = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(redisOptCode) || !redisOptCode.equals(registerQo.getOtpCode())) {
            throw new CustomException(ResultCodeEnum.UNMATCH_OPTCODE);
        }
        // 2. 验证码正确使用后删除
        redisTemplate.delete(key);
        // 3. 数据库插入记录,密码使用加密方式
        Member member = new Member().setStatus(1).setMemberLevelId(4L);
        BeanUtils.copyProperties(registerQo, member);
        String password = passwordEncoder.encode(registerQo.getPassword());
        member.setPassword(password);

        return memberService.save(member);
    }

    @Override
    public Member login(String username, String password) {
        // 1. 获取数据库用户信息
        List<Member> members = memberService.getByUsername(username);
        // 2. 密码加密匹配
        if (CollectionUtils.isEmpty(members) || (!passwordEncoder.matches(password, members.get(0).getPassword()))) {
            throw new CustomException(ResultCodeEnum.UNMATCH_UNAME_PWD);
        }
        // 3. 返回用户信息
        Member member = members.get(0);
        return member;
    }
}
