package info.hanniu.hanniupk.backend.modular.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.backend.modular.consumer.SysUserServiceConsumer;
import info.hanniu.hanniupk.backend.modular.entity.PlayerGrade;
import info.hanniu.hanniupk.backend.modular.mapper.PlayerGradeMapper;
import info.hanniu.hanniupk.backend.modular.mapper.UserPlayerMapper;
import info.hanniu.hanniupk.backend.modular.service.IUserInfoService;
import info.hanniu.hanniupk.backend.modular.vo.UserInfoVO;
import info.hanniu.hanniupk.backend.modular.vo.UserRankVO;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.model.api.IPlayerGradeService;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import info.hanniu.hanniupk.kernel.model.util.MyBeanUtils;
import info.hanniu.hanniupk.kernel.model.vo.PlayerGradeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: UserInfoServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 9:27
 * @Version: 1.0
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private SysUserServiceConsumer sysUserServiceConsumer;
    @Autowired
    private UserPlayerServiceImpl userPlayerService;

    @Autowired
    private UserPlayerMapper userPlayerMapper;
    @Autowired
    private PlayerGradeMapper playerGradeMapper;

    @Autowired
    private IPlayerGradeService iPlayerGradeService;

    @Override
    public UserInfoVO getUserInfo(Integer userId) {
        UserInfoVO userInfoVO = new UserInfoVO();
        SysUserDTO sysUserDTO = sysUserServiceConsumer.getSysUserByUserId(userId);
        MyBeanUtils.copyProperties(sysUserDTO, userInfoVO);
        UserPlayer userPlayer = userPlayerService.selectById(userId);
        MyBeanUtils.copyProperties(userPlayer, userInfoVO);
        if (null != userPlayer) {
            PlayerGradeVO playerGrade = iPlayerGradeService.selectByGradeCode(userPlayer.getGradeCode());
            if (null != playerGrade) {
                userInfoVO.setGradeName(playerGrade.getGradeName());
            }
        }
        return userInfoVO;
    }

    /**
     * @return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Author zhanglj
     * @Description 查询微信好友等级排名
     * @Date 2018/10/20 7:52 PM
     * @Param [page]
     **/
    @Override
    public ResponseData getFriendGradeRank(Page<UserRankVO> page, Integer userId) {
        List<UserRankVO> userRankVOList = new ArrayList<>();
        //查询用户好友
        Set<Integer> friendIdList = this.sysUserServiceConsumer.getUserFriendIdList(userId);
        if (friendIdList != null && friendIdList.size() > 0) {

            userRankVOList = this.getUserRankVOList(page, friendIdList);
        }
        return ResponseData.success(userRankVOList);
    }

    /**
     * @return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Author zhanglj
     * @Description 查询用户等级排名
     * @Date 2018/10/20 7:51 PM
     * @Param [page]
     **/
    @Override
    public ResponseData getUserGradeRank(Page<UserRankVO> page) {
        List<UserRankVO> userRankVOList = getUserRankVOList(page, null);
        return ResponseData.success(userRankVOList);
    }

    /**
     * @return java.util.List<info.hanniu.hanniupk.backend.modular.vo.UserRankVO>
     * @Author zhanglj
     * @Description 查询排行榜
     * @Date 2018/10/29 7:11 PM
     * @Param [page, entityWrapper]
     **/
    private List<UserRankVO> getUserRankVOList(Page<UserRankVO> page, Set<Integer> friendIdList) {
        EntityWrapper<UserPlayer> entityWrapper = new EntityWrapper<>();
        if(friendIdList!=null && friendIdList.size()>0) {
            entityWrapper.in("id", friendIdList);
        }
        entityWrapper.orderBy("experience", false);
        List<UserRankVO> userRankVOList = new ArrayList<>();
        List<UserPlayer> userPlayers = this.userPlayerMapper.selectPage(page, entityWrapper);
        List<Integer> userIdList = userPlayers.stream().map(UserPlayer::getId).collect(Collectors.toList());
        //远程调用服务
        List<SysUserDTO> sysUserList = this.sysUserServiceConsumer.getSysUserListByUserIdList(JSONArray.toJSONString(userIdList));
        for (int i = 0; i < userPlayers.size(); i++) {
            UserRankVO userRankVO = new UserRankVO();
            UserPlayer userPlayer = userPlayers.get(i);
            BeanUtils.copyProperties(userPlayer, userRankVO);
            //设置序号
            userRankVO.setSerialNumber(i + 1);
            //设置用户昵称和头像
            for (SysUserDTO sysUserDTO : sysUserList) {
                if (sysUserDTO.getUserId().equals(userPlayer.getId())) {
                    userRankVO.setNickName(sysUserDTO.getNickName());
                    userRankVO.setAvatarUrl(sysUserDTO.getAvatarUrl());
                    break;
                }
            }
            //设置用户游戏等级
            PlayerGrade playerGrade = new PlayerGrade();
            playerGrade.setGradeCode(userPlayer.getGradeCode());
            playerGrade = this.playerGradeMapper.selectOne(playerGrade);
            userRankVO.setGradeName(playerGrade.getGradeName());
            userRankVOList.add(userRankVO);
        }
        return userRankVOList;
    }
}
