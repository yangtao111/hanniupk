package info.hanniu.hanniupk.system.modular.provider;

import com.alibaba.fastjson.JSONArray;
import info.hanniu.hanniupk.kernel.model.api.ISysUserService;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.system.modular.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.provider
 * @ClassName: SysUserServiceProvider
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 19:43
 * @Version: 1.0
 */
@RestController
@Primary
public class SysUserServiceProvider implements ISysUserService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public Integer save(@RequestBody SysUserDTO sysUserDTO) {
        return sysUserService.save(sysUserDTO);
    }

    /**
     * 通过token获取用户信息
     *
     * @author zhanglj
     * @Date 2018/10/11 16:32
     */
    @Override
    public SysUserDTO getSysUserByToken(String token) {
        return sysUserService.getSysUserByToken(token);
    }

    /**
     * 通过userId获取用户信息
     *
     * @author zhanglj
     * @Date 2018/10/11 16:32
     */
    @Override
    public SysUserDTO getSysUserByUserId(Integer userId) {
        return sysUserService.getSysUserByUserId(userId);
    }

    @Override
    public SysUserDTO getSysUserByOpenId(String openId) {
        return sysUserService.getSysUserByOpenId(openId);
    }

    /**
     * 通过userIdList获取用户信息列表
     *
     * @author zhanglj
     * @Date 2018/10/11 16:32
     */
    @Override
    public List<SysUserDTO> getSysUserListByUserIdList(String userIdListStr) {
        List<Integer> userIdList = JSONArray.parseArray(userIdListStr, Integer.class);
        return this.sysUserService.getSysUserListByUserIdList(userIdList);
    }

    /**
     * @return java.util.List<java.lang.Integer>
     * @Author zhanglj
     * @Description 根据userId 查询好友ID列表
     * @Date 2018/10/29 4:42 PM
     * @Param [token]
     **/
    @Override
    public Set<Integer> getUserFriendIdList(Integer userId) {
        return this.sysUserService.getUserFriendIdList(userId);
    }

    /**
     * @return java.lang.Integer
     * @Author zhanglj
     * @Description 保存好友关系
     * @Date 2018/11/19 1:04 PM
     * @Param [userId, friendId]
     **/
    @Override
    public Integer saveUserFriend(Integer userId, Integer friendId) {
        return this.sysUserService.saveUserFriend(userId, friendId);
    }

    @Override
    public List<SysUserDTO> getRobotsInactive() {
        return this.sysUserService.getRobotsInactive();
    }

    @Override
    public void setRobotActive(Integer robotId) {
        this.sysUserService.setRobotActive(robotId);
    }

    @Override
    public void setRobotInactive(Integer robotId) {
        this.sysUserService.setRobotInactive(robotId);
    }

}
