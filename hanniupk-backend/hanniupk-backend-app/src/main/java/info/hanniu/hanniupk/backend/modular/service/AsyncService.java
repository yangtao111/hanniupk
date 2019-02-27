package info.hanniu.hanniupk.backend.modular.service;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @Author: zhanglj
 * @Description: 异步接口
 * @Date: 2018/10/9 8:15 PM
 * @Version: 1.0.0
 */
public interface AsyncService<T> {

    /**
     * 执行异步任务
     */
    void executeAsync();

    DeferredResult<String> getDeferredResult(IThreadTaskService iThreadTaskService, T t);

    DeferredResult<String> getDeferredResult();

    Callable<String> getCallable();

    WebAsyncTask<String> getWebAsyncTask();
}
