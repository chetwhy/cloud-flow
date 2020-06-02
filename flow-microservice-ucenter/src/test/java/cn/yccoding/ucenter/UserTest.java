package cn.yccoding.ucenter;

import cn.yccoding.ucenter.bean.User;
import cn.yccoding.ucenter.data.UserRepository;
import cn.yccoding.ucenter.form.RegistrationForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author YC
 * @create 2020/5/2
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void create() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("admin");
        form.setCity("shenzhen");
        form.setState("guangdong");
        form.setFullname("admin123");
        form.setPassword("1234");
        form.setPhone("17322223333");
        form.setStreet("jurong road");
        form.setZip("518000");
        userRepo.save(form.toUser(passwordEncoder));
    }

    @Test
    public void getData() {
        Iterable<User> all = userRepo.findAll();
        assert all!=null:"table is null";
        all.forEach(System.out::println);
    }
}
