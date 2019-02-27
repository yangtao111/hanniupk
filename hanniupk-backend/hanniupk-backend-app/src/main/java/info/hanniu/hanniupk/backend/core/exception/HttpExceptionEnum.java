package info.hanniu.hanniupk.backend.core.exception;

import info.hanniu.hanniupk.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.exception
 * @ClassName: HttpExceptionEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/29 19:38
 * @Version: 1.0
 */
public enum HttpExceptionEnum implements AbstractBaseExceptionEnum {
    CONNECTION_EXCEPTION(800,"连接异常"),
    OTHER_EXCEPTION(801,"其他异常");

    private int code;
    private String message;

    HttpExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
