package info.hanniu.hanniupk.backend.modular.service;

import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.backend.modular.vo.UserInfoVO;
import info.hanniu.hanniupk.backend.modular.vo.UserRankVO;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: IUserInfoService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 9:26
 * @Version: 1.0
 */
public interface IUserInfoService {
    /**
     * 获取home页用户信息
     *
     * @param userId
     * @return
     */
    UserInfoVO getUserInfo(Integer userId);

    /**
     * @return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Author zhanglj
     * @Description 查询用户等级排名
     * @Date 2018/10/20 6:37 PM
     * @Param [page]
     **/
    ResponseData getUserGradeRank(Page<UserRankVO> page);

    /**
     * @return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Author 微信好友等级排名
     * @Description //TODO
     * @Date 2018/10/20 7:51 PM
     * @Param [page]
     **/
    ResponseData getFriendGradeRank(Page<UserRankVO> page, Integer userId);
}
