package cn.yccoding.gzh;

import cn.yccoding.gzh.config.SpringDataJpaConfig;
import cn.yccoding.gzh.domain.ReplyMessage;
import cn.yccoding.gzh.repository.ReplyMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @Author YC
 * @create 2020/3/5
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {SpringDataJpaConfig.class, GzhApplication.class})
public class GzhApplicationTests {

    @Autowired
    private ReplyMessageRepository replyMessageRepository;

    @Before
    public void create() throws IOException {
        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setKeyword("who");
        FileInputStream fis = new FileInputStream("aaa.txt");
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
        replyMessage.setFile(bytes);
        System.out.println(replyMessage);
        replyMessageRepository.save(replyMessage);
        assert replyMessage.getId() > 0 : "error";
    }

    @Test
    public void getData() {
        List<ReplyMessage> all = replyMessageRepository.findAll();
        assert all!=null:"table is null";
        all.forEach(System.out::println);
    }

    @Test
    public void getDataById() throws IOException {
        ReplyMessage message = replyMessageRepository.findByKeyword("who2");
        Optional<ReplyMessage> one = replyMessageRepository.findById(1L);
        ReplyMessage replyMessage = one.get();
        if (message == null) {
            System.out.println("null");
            return;
        }
        byte[] file = message.getFile();
        FileOutputStream fos = new FileOutputStream(new File("bbb.txt"));
        fos.write(file);
        fos.close();
    }

}
