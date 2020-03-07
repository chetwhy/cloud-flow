package cn.yccoding.gzh;

import cn.yccoding.gzh.config.SpringDataJpaConfig;
import cn.yccoding.gzh.domain.ReplyMessage;
import cn.yccoding.gzh.repository.ReplyMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author YC
 * @create 2020/3/5
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {SpringDataJpaConfig.class, GzhApplication.class})
public class GzhApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(GzhApplicationTests.class);

    @Autowired
    private ReplyMessageRepository replyMessageRepository;

    @Before
    public void create() {
        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setKeyword("who");
        replyMessage.setText("王二狗");
        replyMessageRepository.save(replyMessage);
        assert replyMessage.getId() > 0 : "error";
    }

    @Test
    public void getData() {
        List<ReplyMessage> all = replyMessageRepository.findAll();
        assert all!=null:"table is null";
        all.forEach(System.out::println);
    }
}
