package info.hanniu.hanniupk.backend.modular.controller;

import info.hanniu.hanniupk.backend.modular.service.AsyncService;
import info.hanniu.hanniupk.backend.modular.service.FightService;
import info.hanniu.hanniupk.core.reqres.request.RequestData;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

/**
 * 对战controller
 *
 * @author zhanglijun
 * @date 创建于 2018/9/27
 */
@RestController
@RequestMapping("/api/v1/fight")
public class FightController {

    @Autowired
    private FightService fightService;
    @Autowired
    private AsyncService asyncService;

    /**
     * 游戏用户匹配
     */
    @GetMapping("gameUserMatch")
    public DeferredResult<ResponseData> gameUserMatch(HttpServletRequest request, RequestData requestData) {
        String token = request.getHeader("Authorization");
        return this.fightService.getGameUserMatchDeferredResult(this.fightService, token);
    }

    /**
     * 设置邀请好友pk房间信息
     */
    @GetMapping("setInviteFriendRoom")
    public ResponseData setInviteFriendRoom(@RequestParam("currentUserId") Integer currentUserId, @RequestParam("opponentUserId") Integer opponentUserId) {
        return this.fightService.setInviteFriendRoom(currentUserId, opponentUserId);
    }


    /**
     * @return Callable<String>
     * @Author zhanglj
     * @Description 异步线程Callable示例
     * @Date 2018/10/9 8:39 PM
     * @Param []
     **/
    @RequestMapping("/callable")
    public Callable<String> callable() {
        LogUtil.info("外部线程：" + Thread.currentThread().getName());
        return this.asyncService.getCallable();
    }

    /**
     * @return org.springframework.web.context.request.async.DeferredResult<java.lang.String>
     * @Author zhanglj
     * @Description 异步线程DeferredResult示例
     * @Date 2018/10/9 8:50 PM
     * @Param []
     **/
    @RequestMapping("/deferredresult")
    public DeferredResult<String> deferredResult() {
        LogUtil.info("外部线程：" + Thread.currentThread().getName());
        return this.asyncService.getDeferredResult();
    }

    /**
     * @return org.springframework.web.context.request.async.WebAsyncTask<java.lang.String>
     * @Author zhanglj
     * @Description 异步线程WebAsyncTask示例
     * @Date 2018/10/9 8:52 PM
     * @Param []
     **/
    @RequestMapping("/webAsyncTask")
    public WebAsyncTask<String> webAsyncTask() {
        LogUtil.info("外部线程：" + Thread.currentThread().getName());
        return this.asyncService.getWebAsyncTask();
    }

}
