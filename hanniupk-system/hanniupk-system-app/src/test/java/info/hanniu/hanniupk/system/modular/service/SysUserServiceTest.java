package info.hanniu.hanniupk.system.modular.service;

import com.netflix.discovery.converters.Auto;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.service
 * @ClassName: SysUserServiceTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 13:16
 * @Version: 1.0
 */
public class SysUserServiceTest extends BaseTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void getTokenWx(){
        String token = sysUserService.wxLogin(Integer.valueOf("1051637039091949569"));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+token);
    }

    @Test
    public void getSysUserByOpenId() throws Exception {
        SysUserDTO sysUserDTO = sysUserService.getSysUserByOpenId("1934378866");
        Assert.assertNotNull(sysUserDTO);
    }
    @Test
    public void setRobotActive(){
        sysUserService.setRobotActive(101);
    }
    @Test
    public void setRobotInActive(){
        sysUserService.setRobotInactive(101);
    }

}