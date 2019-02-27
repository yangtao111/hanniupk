package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.modular.service.BaseTest;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: UserPlayerServiceImplTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/13 15:09
 * @Version: 1.0
 */
public class UserPlayerServiceImplTest extends BaseTest{
    @Autowired
    private UserPlayerServiceImpl iUserPlayerService;
    @Test
    public void selectById() throws Exception {
        UserPlayer userPlayer = iUserPlayerService.selectById(Long.valueOf(1));
        System.out.println("UserPlayser>>>>>>"+userPlayer.toString());
    }

    @Test
    public void saveOrUpdate() throws Exception {
        UserPlayer userPlayer = new UserPlayer();
        userPlayer.setId(Integer.valueOf(2));
        userPlayer.setGradeCode(1);
        userPlayer.setExperience(100);
        userPlayer.setCoin(30);
        userPlayer.setCreateTime(new Date());
        userPlayer.setRemark("第er个测试用户Player");
        iUserPlayerService.saveOrUpdate(userPlayer);
    }

}