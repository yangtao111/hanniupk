package info.hanniu.hanniupk.system.modular.service;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: BaseTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 14:55
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseTest {
    @Before
    public void init() {
        System.out.println("***************开始测试******************");
    }
    @After
    public void after() {
        System.out.println("***************测试结束******************");
    }
}
