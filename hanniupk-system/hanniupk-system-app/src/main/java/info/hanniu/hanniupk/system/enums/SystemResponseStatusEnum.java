package info.hanniu.hanniupk.system.enums;

import lombok.Getter;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.enums
 * @ClassName: SystemResponseStatusEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/23 13:11
 * @Version: 1.0
 */
public enum SystemResponseStatusEnum {
    /**
     * 微信登录失败
     */
    DEFAULT_ERROR_CODE(1001, "微信登录失败");

    @Getter
    private int code;
    @Getter
    private String message;

    SystemResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
