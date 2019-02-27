package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.modular.service.AsyncService;
import info.hanniu.hanniupk.backend.modular.service.IThreadTaskService;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @Author: zhanglj
 * @Description: 异步接口
 * @Date: 2018/10/9 8:16 PM
 * @Version: 1.0.0
 */
@Service
public class AsyncServiceImpl<T> implements AsyncService<T> {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    @Async("threadPoolTaskExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }

    @Override
    public DeferredResult<String> getDeferredResult(IThreadTaskService iThreadTaskService, T t) {
        logger.info("外部线程>>>>{}<<<<<开始。", Thread.currentThread().getName());
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<String>(60 * 1000L);
        //处理超时事件 采用委托机制
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                LogUtil.info("DeferredResult超时");
                result.setResult(null);
            }
        });
        result.onCompletion(new Runnable() {

            @Override
            public void run() {
                //完成后
                LogUtil.info("调用完成");
            }
        });
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //处理业务逻辑
                logger.info("内部线程：》》》》{}《《《《开始！", Thread.currentThread().getName());
                iThreadTaskService.run(t);
                logger.info("内部线程：》》》》{}《《《《结束！", Thread.currentThread().getName());
            }
        });
        logger.info("外部线程>>>>{}<<<<<结束。", Thread.currentThread().getName());
        return result;
    }

    @Override
    public DeferredResult<String> getDeferredResult() {
        logger.info("外部线程>>>>{}<<<<<开始。", Thread.currentThread().getName());
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<String>(60 * 1000L);
        //处理超时事件 采用委托机制
        result.onTimeout(new Runnable() {

            @Override
            public void run() {
                LogUtil.info("DeferredResult超时");
                result.setResult("超时了!");
            }
        });
        result.onCompletion(new Runnable() {

            @Override
            public void run() {
                //完成后
                LogUtil.info("调用完成");
            }
        });
        threadPoolTaskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                //处理业务逻辑
                LogUtil.info("内部线程：》》》》{}《《《《开始！" + Thread.currentThread().getName());
                //返回结果
                result.setResult("DeferredResult!!");
                LogUtil.info("内部线程：》》》》{}《《《《结束！" + Thread.currentThread().getName());
            }
        });
        logger.info("外部线程>>>>{}<<<<<结束。", Thread.currentThread().getName());
        return result;
    }


    @Override
    public Callable<String> getCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                LogUtil.info("内部线程：" + Thread.currentThread().getName());
                return "callable!";
            }
        };
        threadPoolTaskExecutor.submit(callable);
        return callable;
    }

    @Override
    public WebAsyncTask<String> getWebAsyncTask() {
        WebAsyncTask<String> result = new WebAsyncTask<String>(60 * 1000L, new Callable<String>() {

            @Override
            public String call() throws Exception {
                LogUtil.info("内部线程：" + Thread.currentThread().getName());
                return "WebAsyncTask!!!";
            }
        });
        result.onTimeout(new Callable<String>() {

            @Override
            public String call() throws Exception {
                // TODO Auto-generated method stub
                return "WebAsyncTask超时!!!";
            }
        });
        result.onCompletion(new Runnable() {

            @Override
            public void run() {
                //超时后 也会执行此方法
                LogUtil.info("WebAsyncTask执行结束");
            }
        });
        return result;
    }
}
