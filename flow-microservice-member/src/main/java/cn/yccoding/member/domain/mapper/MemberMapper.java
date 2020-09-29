package cn.yccoding.member.domain.mapper;

import cn.yccoding.member.domain.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yc
 * @since 2020-09-03
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
