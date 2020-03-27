package cn.yccoding.blob;

import cn.yccoding.blob.config.SpringDataJpaConfig;
import cn.yccoding.blob.domain.BlobFile;
import cn.yccoding.blob.repository.BlobFileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @Author YC
 * @create 2020/3/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {SpringDataJpaConfig.class, BlobApplication.class})
public class BlobApplicationTests {

    @Autowired
    BlobFileRepository blobFileRepository;

    @Before
    public void create() throws IOException {
        BlobFile file = new BlobFile();
        file.setFileName("who");
        byte[] bytes = new byte[4];
        bytes[1] = 65;
        file.setContent(bytes);
        blobFileRepository.save(file);
        assert file.getId() > 0 : "error";
    }

    @Test
    public void getData() {
        List<BlobFile> all = blobFileRepository.findAll();
        all.forEach(System.out::println);
    }
}
