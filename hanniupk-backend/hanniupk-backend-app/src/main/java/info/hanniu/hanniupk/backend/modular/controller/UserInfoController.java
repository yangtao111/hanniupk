package info.hanniu.hanniupk.backend.modular.controller;

import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.backend.modular.service.IUserInfoService;
import info.hanniu.hanniupk.backend.modular.vo.UserRankVO;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.controller
 * @ClassName: UserInfoController
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 9:22
 * @Version: 1.0
 */
@Controller
@RequestMapping("/api/v1/userInfo")
public class UserInfoController {

    @Autowired
    private IUserInfoService iUserInfoService;

    @GetMapping("/getByUserId")
    @ResponseBody
    public ResponseData getUserInfo(@RequestParam(required = true) Integer userId) {
        return ResponseData.success(iUserInfoService.getUserInfo(userId));
    }

    /**
     * @return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Author zhanglj
     * @Description 查询用户等级排名
     * @Date 2018/10/20 6:33 PM
     * @Param []
     **/
    @GetMapping("userGradeRank")
    @ResponseBody
    public ResponseData getUserGradeRank(Page<UserRankVO> page) {
        return this.iUserInfoService.getUserGradeRank(page);
    }

    /**
     * @return info.hanniu.hanniupk.core.reqres.response.ResponseData
     * @Author zhanglj
     * @Description 查询微信好友等级排名
     * @Date 2018/10/20 7:52 PM
     * @Param [page]
     **/
    @GetMapping("friendGradeRank")
    @ResponseBody
    public ResponseData getFriendGradeRank(@RequestParam("userId") Integer userId,@RequestParam("current") Integer current,@RequestParam("size") Integer size) {
        Page<UserRankVO> page = new Page<>(current, size);
        return this.iUserInfoService.getFriendGradeRank(page, userId);
    }


}
