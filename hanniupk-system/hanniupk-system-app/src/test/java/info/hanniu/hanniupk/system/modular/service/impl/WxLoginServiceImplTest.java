package info.hanniu.hanniupk.system.modular.service.impl;

import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.kernel.model.dto.WxLoginDTO;
import info.hanniu.hanniupk.kernel.model.vo.LoginResponseVO;
import info.hanniu.hanniupk.system.modular.service.BaseTest;
import info.hanniu.hanniupk.system.modular.service.WxLoginServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.service.impl
 * @ClassName: WxLoginServiceImplTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 17:34
 * @Version: 1.0
 */
public class WxLoginServiceImplTest extends BaseTest{
    @Autowired
    WxLoginServiceImpl wxLoginService;
    @Test
    public void saveSysUser() throws Exception {
        String openId = "hhh";
        wxLoginService.saveSysUser(openId,getWxLoginDTO());
    }
    public WxLoginDTO getWxLoginDTO() {
        WxLoginDTO wxLoginDTO = new WxLoginDTO();
        wxLoginDTO.setAvatarUrl("hhhh_hhh");
        wxLoginDTO.setCode("hdjd");
        wxLoginDTO.setGender(2);
        wxLoginDTO.setNickName("gogo");
        return wxLoginDTO;
    }

    @Test
    public void getSysUserByOpenId() throws Exception {
        SysUserDTO sysUser = wxLoginService.getSysUserByOpenId("hhh");
        System.out.println(">>>>>>>>>>>>>>>>SysUserDTO"+sysUser.toString());
    }

    @Test
    public void getSessionId() throws Exception {
        wxLoginService.getSessionId("sessionKey","openId");
    }

    @Test
    public void createSessionIdValue() throws Exception {
        String value = wxLoginService.createSessionIdValue("sessionKey","openId");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+value);
    }

    @Test
    public void isSessionIdExisted() throws Exception {
        boolean a = wxLoginService.isSessionIdExisted("4445814cf46b41c58c5b6b3061bacb6e");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+a);
    }
    @Test
    public void getResponseData(){
        LoginResponseVO loginResponse = wxLoginService.getResponseData("sdfhksdhfkds",Integer.valueOf("1046382712773222402"));
        System.out.println("loginResponse==============================>"+loginResponse.toString());
    }

}