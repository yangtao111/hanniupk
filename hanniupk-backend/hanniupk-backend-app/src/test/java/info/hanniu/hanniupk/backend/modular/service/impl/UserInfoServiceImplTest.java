package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.modular.service.BaseTest;
import info.hanniu.hanniupk.backend.modular.service.IUserInfoService;
import info.hanniu.hanniupk.backend.modular.vo.UserInfoVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: UserInfoServiceImplTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 14:01
 * @Version: 1.0
 */
public class UserInfoServiceImplTest extends BaseTest{
    @Autowired
    private IUserInfoService userInfoService;

    @Test
    public void getUserInfo() throws Exception {
        Integer userId = Integer.valueOf("109569");
        UserInfoVO userInfoVO = userInfoService.getUserInfo(userId);
        System.out.println("userInfoVO:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userInfoVO.toString());
    }

}