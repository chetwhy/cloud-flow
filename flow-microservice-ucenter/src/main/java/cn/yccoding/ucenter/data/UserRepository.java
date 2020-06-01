package cn.yccoding.ucenter.data;

import cn.yccoding.ucenter.bean.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author YC
 * @create 2020/5/1
 */
public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);
}
