package cn.yccoding.member.domain.service;

import cn.yccoding.member.domain.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yc
 * @since 2020-09-03
 */
public interface MemberService extends IService<Member> {

    /**
     * 根据电话获取会员信息
     * @param phone
     * @return
     */
    List<Member> getByPhone(String phone);
}
