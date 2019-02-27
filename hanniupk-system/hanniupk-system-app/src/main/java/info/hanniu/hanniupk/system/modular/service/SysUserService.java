/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.hanniu.hanniupk.system.modular.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import info.hanniu.hanniupk.core.util.ToolUtil;
import info.hanniu.hanniupk.kernel.jwt.utils.JwtTokenUtil;
import info.hanniu.hanniupk.kernel.model.dto.LoginUser;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.kernel.model.exception.ServiceException;
import info.hanniu.hanniupk.kernel.model.util.MyBeanUtils;
import info.hanniu.hanniupk.system.enums.RobotActiveEnum;
import info.hanniu.hanniupk.system.exception.enums.AuthExceptionEnum;
import info.hanniu.hanniupk.system.modular.entity.SysUser;
import info.hanniu.hanniupk.system.modular.entity.UserFriend;
import info.hanniu.hanniupk.system.modular.mapper.SysUserMapper;
import info.hanniu.hanniupk.system.modular.mapper.UserFriendMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static info.hanniu.hanniupk.system.core.constants.SystemConstants.DEFAULT_LOGIN_TIME_OUT_SECS;
import static info.hanniu.hanniupk.system.core.constants.SystemConstants.LOGIN_USER_CACHE_PREFIX;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-26
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserFriendMapper userFriendMapper;


    /**
     * 用户登录，登录成功返回token
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午3:14
     */
    public String login(String username, String password) {

        //查询账号是否存在
        SysUser sysUser = null;
        List<SysUser> accounts = this.selectList(new EntityWrapper<SysUser>().eq("ACCOUNT", username));
        if (accounts != null && accounts.size() > 0) {
            sysUser = accounts.get(0);
        } else {
            throw new ServiceException(AuthExceptionEnum.USER_NOT_FOUND);
        }

        //校验账号密码是否正确
        String md5Hex = ToolUtil.md5Hex(password + sysUser.getSalt());
        if (!md5Hex.equals(sysUser.getPassword())) {
            throw new ServiceException(AuthExceptionEnum.INVALID_PWD);
        }

        //生成token
        String jwtToken = jwtTokenUtil.generateToken(sysUser.getUserId().toString(), null);

        //token放入缓存
        LoginUser loginUser = new LoginUser();
        loginUser.setAccountId(sysUser.getUserId());
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(LOGIN_USER_CACHE_PREFIX + jwtToken);
        opts.set(loginUser, DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);

        return jwtToken;
    }

    /**
     * 微信登录获取token
     *
     * @param userId
     * @return
     */
    public String wxLogin(Integer userId) {
        //生成token
        String jwtToken = jwtTokenUtil.generateToken(userId.toString(), null);
        //token放入缓存
        LoginUser loginUser = new LoginUser();
        loginUser.setAccountId(userId);
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(LOGIN_USER_CACHE_PREFIX + jwtToken);
        opts.set(loginUser, DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);
        return jwtToken;
    }

    /**
     * 校验token是否正确
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午4:06
     */
    public boolean checkToken(String token) {

        //先校验jwt是否正确
        if (!jwtTokenUtil.checkToken(token)) {
            return false;
        }

        //校验缓存是否有token
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(LOGIN_USER_CACHE_PREFIX + token);
        LoginUser loginUser = (LoginUser) opts.get();
        if (loginUser == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 退出登录
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午4:09
     */
    public void logout(String token) {
        redisTemplate.delete(LOGIN_USER_CACHE_PREFIX + token);
    }

    /**
     * 获取登录用户通过token
     *
     * @author fengshuonan
     * @Date 2018/8/26 下午4:12
     */
    public LoginUser getLoginUserByToken(String token) {
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(LOGIN_USER_CACHE_PREFIX + token);
        Object loginUser = opts.get();
        if (loginUser != null) {
            try {
                return (LoginUser) loginUser;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    public Integer save(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        ToolUtil.copyProperties(sysUserDTO, sysUser);
        if (this.insertOrUpdate(sysUser)) {
            SysUser newSysUser = this.selectOne(new EntityWrapper<SysUser>().eq("OPEN_ID", sysUserDTO.getOpenId()));
            if (null != newSysUser) {
                return newSysUser.getUserId();
            }
        }
        return null;
    }

    /**
     * @return info.hanniu.hanniupk.kernel.model.dto.SysUserDTO
     * @Author zhanglj
     * @Description 根据token 获取用户信息
     * @Date 2018/10/12 8:14 PM
     * @Param [token]
     **/
    public SysUserDTO getSysUserByToken(String token) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(LOGIN_USER_CACHE_PREFIX + token);
        LoginUser loginUser = (LoginUser) opts.get();
        SysUser sysUser = this.selectById(loginUser.getAccountId());
        MyBeanUtils.copyProperties(sysUser, sysUserDTO);
        sysUserDTO.setPlayerId(sysUser.getUserId());
        return sysUserDTO;
    }

    /**
     * @return info.hanniu.hanniupk.kernel.model.dto.SysUserDTO
     * @Author zhanglj
     * @Description 根据userId 获取用户信息
     * @Date 2018/10/12 8:14 PM
     * @Param [userId]
     **/
    public SysUserDTO getSysUserByUserId(Integer userId) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        SysUser sysUser = this.selectById(userId);
        if (null != sysUser) {
            BeanUtils.copyProperties(sysUser, sysUserDTO);
            sysUserDTO.setPlayerId(sysUser.getUserId());
            return sysUserDTO;
        }
        return null;
    }

    /**
     * @param openId
     * @Method getSysUserByOpenId
     * @Author tao
     * @Version 1.0
     * @Description 根据openId获取用户信息
     * @Return info.hanniu.hanniupk.kernel.model.dto.SysUserDTO
     * @Exception
     * @Date 2018/10/14 13:11
     */
    public SysUserDTO getSysUserByOpenId(String openId) {
        SysUserDTO sysUserDTO = new SysUserDTO();
        SysUser sysUser = this.selectOne(new EntityWrapper<SysUser>().eq("OPEN_ID", openId));
        if (null == sysUser) {
            sysUserDTO = null;
        } else {
            BeanUtils.copyProperties(sysUser, sysUserDTO);
        }
        return sysUserDTO;
    }

    /**
     * @return java.util.List<info.hanniu.hanniupk.kernel.model.dto.SysUserDTO>
     * @Author zhanglj
     * @Description 根据用户ID集合获取用户列表信息
     * @Date 2018/10/21 5:01 PM
     * @Param [userIdList]
     **/
    public List<SysUserDTO> getSysUserListByUserIdList(List<Integer> userIdList) {
        List<SysUserDTO> sysUserDTOList = new ArrayList<>();
        List<SysUser> sysUsers = this.baseMapper.selectBatchIds(userIdList);
        sysUsers.forEach(item -> {
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(item, sysUserDTO);
            sysUserDTOList.add(sysUserDTO);
        });
        return sysUserDTOList;
    }

    /**
     * @return java.util.List<java.lang.Integer>
     * @Author zhanglj
     * @Description 根据用户ID查询好友ID列表
     * @Date 2018/10/29 6:50 PM
     * @Param [userId]
     **/
    public Set<Integer> getUserFriendIdList(Integer userId) {
        EntityWrapper<UserFriend> entityWrapper = new EntityWrapper<>();
        //根据user_id 查询好友
        entityWrapper.eq("user_id", userId);
        List<UserFriend> userFriendList1 = this.userFriendMapper.selectList(entityWrapper);
        Set<Integer> userFriendIdSet1 = userFriendList1.stream().map(UserFriend::getFriendId).collect(Collectors.toSet());
        //根据friend_id查询好友
        EntityWrapper<UserFriend> entityWrapper1 = new EntityWrapper<>();
        entityWrapper1.eq("friend_id", userId);
        List<UserFriend> userFriendList2 = this.userFriendMapper.selectList(entityWrapper1);
        Set<Integer> userFriendIdSet2 = userFriendList2.stream().map(UserFriend::getUserId).collect(Collectors.toSet());
        //去掉重复的，取并集
        userFriendIdSet1.addAll(userFriendIdSet2);
        return userFriendIdSet1;

    }

    /**
     * @return java.lang.Integer
     * @Author zhanglj
     * @Description 保存好友用户关系
     * @Date 2018/11/19 1:05 PM
     * @Param [userId, friendId]
     **/
    public Integer saveUserFriend(Integer userId, Integer friendId) {
        //查询好友关系是否存在，存在则返回
        UserFriend userFriend = new UserFriend();
        //userId字段存放两个用户ID相对较小的
        if(userId< friendId) {
            userFriend.setUserId(userId);
            userFriend.setFriendId(friendId);
        } else {
            userFriend.setFriendId(userId);
            userFriend.setUserId(friendId);
        }
        UserFriend oldUserFriend = this.userFriendMapper.selectOne(userFriend);
        if (oldUserFriend != null) {
            return 1;
        }
        Date currentDate = new Date();
        userFriend.setCreateTime(currentDate);
        userFriend.setUpdateTime(currentDate);
        return this.userFriendMapper.insert(userFriend);
    }

    /**
     * 查询所有未在使用的机器人
     * @return
     */
    public List<SysUserDTO> getRobotsInactive(){
        List<SysUserDTO> sysUserDTOList = new ArrayList<>();
        List<SysUser> sysUsers = this.baseMapper.selectList(new EntityWrapper<SysUser>().eq("ROBOT_ACTIVE",
                        RobotActiveEnum.ROBOT_INACTIVE.getCode()));
        sysUsers.forEach(item -> {
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtils.copyProperties(item, sysUserDTO);
            sysUserDTO.setPlayerId(item.getUserId());
            sysUserDTOList.add(sysUserDTO);
        });
        return sysUserDTOList;
    }
    public void setRobotActive(Integer robotId){
        SysUser sysUser = new SysUser();
        sysUser.setUserId(robotId);
        sysUser.setRobotActive(RobotActiveEnum.ROBOT_ACTIVE.getCode());
        this.baseMapper.updateById(sysUser);
    }
    public void setRobotInactive(Integer robotId){
        SysUser sysUser = new SysUser();
        sysUser.setUserId(robotId);
        sysUser.setRobotActive(RobotActiveEnum.ROBOT_INACTIVE.getCode());
        this.baseMapper.updateById(sysUser);
    }
}
