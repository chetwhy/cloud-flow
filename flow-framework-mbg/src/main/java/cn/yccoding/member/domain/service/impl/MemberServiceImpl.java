package cn.yccoding.member.domain.service.impl;

import cn.yccoding.member.domain.entity.Member;
import cn.yccoding.member.domain.mapper.MemberMapper;
import cn.yccoding.member.domain.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
