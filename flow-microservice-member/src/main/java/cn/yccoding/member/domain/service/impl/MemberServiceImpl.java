package cn.yccoding.member.domain.service.impl;

import cn.yccoding.member.domain.entity.Member;
import cn.yccoding.member.domain.mapper.MemberMapper;
import cn.yccoding.member.domain.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yc
 * @since 2020-09-03
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    
    @Override
    public List<Member> getByPhone(String phone) {
        return this.list(Wrappers.<Member>lambdaQuery().select(Member::getId).eq(Member::getPhone, phone));
    }
}
