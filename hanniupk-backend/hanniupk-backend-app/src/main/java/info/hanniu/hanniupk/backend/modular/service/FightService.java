package info.hanniu.hanniupk.backend.modular.service;

import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @Author: zhanglj
 * @Description: pk接口
 * @Date: 2018/10/9 2:48 PM
 * @Version: 1.0.0
 */
public interface FightService {
    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO
     * @Author zhanglj
     * @Description 异步匹配对手相关信息
     * @Date 2018/10/9 4:08 PM
     * @Param [token]
     **/
    Callable<ResponseData> getGameUserMatchCallable(FightService fightService, String token);

    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO
     * @Author zhanglj
     * @Description 匹配对手相关信息
     * @Date 2018/10/9 4:08 PM
     * @Param [token]
     **/
    public ResponseData getGameUserMatch(String token);

    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO
     * @Author zhanglj
     * @Description 异步匹配对手相关信息
     * @Date 2018/10/9 4:08 PM
     * @Param [token]
     **/
    DeferredResult<ResponseData> getGameUserMatchDeferredResult(FightService fightService, String token);

    /**
     * @return org.springframework.web.context.request.async.DeferredResult<info.hanniu.hanniupk.core.reqres.response.ResponseData>
     * @Author zhanglj
     * @Description 设置邀请好友pk房间信息
     * @Date 2018/10/20 12:03 PM
     * @Param [token]
     **/
    ResponseData setInviteFriendRoom(Integer currentUserId, Integer opponentUserId);
}
