package info.hanniu.hanniupk.backend.core.exception;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.exception
 * @Author: zhanglj
 * @Description: 自定义超时异常类
 * @Date: 2018/10/9 8:37 PM
 * @Version: 1.0.0
 */
public class AsyncRequestTimeoutException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 8754629185999484614L;

    public AsyncRequestTimeoutException(String uri){
        super(uri);
    }

}
