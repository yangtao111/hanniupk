package info.hanniu.hanniupk.gateway.modular.consumer;

import info.hanniu.hanniupk.gateway.resource.ResourceDefinition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @ProjectName: hanniupk-gateway
 * @Package: info.hanniu.hanniupk.gateway.modular.consumer
 * @ClassName: ResourceServiceConsumerTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/23 21:15
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceServiceConsumerTest {
    @Autowired
    private ResourceServiceConsumer resourceServiceConsumer;
    @Test
    public void getResourceByUrl() throws Exception {
        String requestUri="/HANNIUPK-BACKEND/api/v1/userInfo/getByUserId";
        ResourceDefinition currentResource = resourceServiceConsumer.getResourceByUrl(requestUri);
        Assert.assertNotNull(currentResource);
    }

}