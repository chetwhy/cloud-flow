package cn.yccoding.gzh.repository;

import cn.yccoding.gzh.domain.ReplyMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author YC
 * @create 2020/3/7
 */
@Repository
public interface ReplyMessageRepository extends JpaRepository<ReplyMessage,Long> {

    ReplyMessage findByKeyword(String keyword);
}
