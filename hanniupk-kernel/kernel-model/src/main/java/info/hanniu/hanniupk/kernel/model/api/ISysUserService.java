package info.hanniu.hanniupk.kernel.model.api;

import info.hanniu.hanniupk.kernel.model.dto.LoginUser;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.api
 * @ClassName: ISysUserService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 19:42
 * @Version: 1.0
 */
@RequestMapping("api/sysUserService")
public interface ISysUserService {

    @RequestMapping("/save")
    Integer save(@RequestBody SysUserDTO sysUserDTO);

    /**
     * 通过token获取用户信息
     *
     * @author zhanglj
     * @Date 2018/10/11 16:32
     */
    @RequestMapping(value = "/getSysUserByToken", method = RequestMethod.POST)
    SysUserDTO getSysUserByToken(@RequestParam("token") String token);

    /**
     * 通过userId获取用户信息
     *
     * @author zhanglj
     * @Date 2018/10/11 16:32
     */
    @RequestMapping(value = "/getSysUserByUserId", method = RequestMethod.POST)
    SysUserDTO getSysUserByUserId(@RequestParam("userId") Integer userId);

    /**
     * 通过openId获取用户信息，
     * 若无用户信息，返回null。
     *
     * @param openId
     * @return
     */
    @GetMapping(value = "/getSysUserByOpenId")
    SysUserDTO getSysUserByOpenId(@RequestParam("openId") String openId);

    /**
     * 通过openId获取用户信息，
     * 若无用户信息，返回null。
     *
     * @param userIdListStr
     * @return
     */
    @PostMapping(value = "/getSysUserListByUserIdList")
    List<SysUserDTO> getSysUserListByUserIdList(@RequestParam("userIdListStr") String userIdListStr);

    /**
     * @return java.util.List<java.lang.Integer>
     * @Author zhanglj
     * @Description 查询好友ID列表
     * @Date 2018/10/29 4:35 PM
     * @Param token token
     **/
    @GetMapping(value = "/getUserFriendIdListByUserId")
    Set<Integer> getUserFriendIdList(@RequestParam("userId") Integer userId);
    /**
     * @Author zhanglj
     * @Description 保存好友关系
     * @Date  2018/11/19 12:58 PM
     * @Param [userId, friendId]
     * @return java.lang.Integer
     **/
    @PostMapping(value = "/saveUserFriend")
    Integer saveUserFriend(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId);

    /**
     * 查询所有未在使用的机器人
     * @return
     */
    @GetMapping(value = "/getRobotsInactive")
    List<SysUserDTO> getRobotsInactive();

    /**
     * 机器人状态改为正在使用
     * @param robotId
     */
    @GetMapping(value = "/setRobotActive")
    void setRobotActive(@RequestParam("robotId") Integer robotId);

    /**
     * 机器人状态改为未在使用
     * @param robotId
     */
    @GetMapping(value = "/setRobotInactive")
    void setRobotInactive(@RequestParam("robotId") Integer robotId);

}
