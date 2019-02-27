package info.hanniu.hanniupk.backend.modular.controller;

import info.hanniu.hanniupk.backend.core.enums.ShareCyclicSendEnum;
import info.hanniu.hanniupk.backend.core.variable.ShareCyclicSendMessageMap;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.dto.ShareRequestDTO;
import info.hanniu.hanniupk.backend.modular.service.IShareService;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.controller
 * @ClassName: ShareController
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/14 15:25
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/share")
public class ShareController {

    @Autowired
    private IShareService iShareService;

    /**
     * 分享给好友，缓存分享时间
     * @param shareRequest
     * @return
     */
    @PostMapping("/shareToFriend")
    @ResponseBody
    public ResponseData shareToFriend(@RequestBody ShareRequestDTO shareRequest){
        if (null == shareRequest || StringUtils.isEmpty(shareRequest.getUserId())) {
            return ResponseData.error("userId不能为空！");
        }
        iShareService.shareToFriend(shareRequest.getUserId());
        return ResponseData.success();
    }

    /**
     *取消分享，清除分享时间缓存
     * @param shareRequest
     * @return
     */
    @PostMapping("/giveUpShare")
    @ResponseBody
    public ResponseData giveUpShare(@RequestBody ShareRequestDTO shareRequest){
        if (null == shareRequest || StringUtils.isEmpty(shareRequest.getUserId())) {
            return ResponseData.error("userId不能为空！");
        }
        iShareService.giveUpShare(shareRequest.getUserId());
        return ResponseData.success();
    }

    /**
     *核查分享状态是否超时，放弃 或者 正常
     * @param userId
     * @return
     */
    @GetMapping("/checkShareStatus")
    @ResponseBody
    public ResponseData checkShareStatus(@RequestParam("userId")  String userId){
        return iShareService.checkShareStatus(userId);
    }

    /*@GetMapping("/stopShareCyclic")
    @ResponseBody
    public ResponseData stopShareCyclic(Integer userId){
        ShareCyclicSendMessageMap.shareCyclicSendMessageMap.put(userId, ShareCyclicSendEnum.SHARE_CYCLIC_SEND_NO.getCode());
        return ResponseData.success();
    }*/

    /**
     * 发起人掉线（小程序到后台）
     * @param userId
     * @return
     */
    @GetMapping("/shareOutLine")
    @ResponseBody
    public ResponseData shareOutLine(@RequestParam("userId") String userId){
         iShareService.shareOutLine(userId);
         return ResponseData.success();
    }

    /**
     * 发起人上线（小程序调回前台）
     * @param userId
     * @return
     */
    @GetMapping("/shareOnLine")
    @ResponseBody
    public ResponseData shareOnLine(@RequestParam("userId") String userId){
        return iShareService.shareOnLine(userId);
    }

}
