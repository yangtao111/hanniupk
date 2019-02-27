package info.hanniu.hanniupk.kernel.model.enums;

import lombok.Getter;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.enums
 * @Author: zhanglj
 * @Description: 响应状态枚举类
 * @Date: 2018/10/15 11:00 AM
 * @Version: 1.0.0
 */
public enum ResponseStatusEnum {
    /**
     * @Author zhanglj
     * @Description 请求成功
     * @Date 2018/10/15 11:12 AM
     **/
    DEFAULT_SUCCESS(1, "请求成功"),
    /**
     * @Author zhanglj
     * @Description 请求失败
     * @Date 2018/10/15 11:12 AM
     **/
    DEFAULT_ERROR(0, "请求错误");
    @Getter
    private int code;
    @Getter
    private String message;


    ResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
