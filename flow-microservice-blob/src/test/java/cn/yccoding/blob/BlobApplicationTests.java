package cn.yccoding.blob;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author YC
 * @create 2020/3/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {BlobApplication.class})
public class BlobApplicationTests {

}
