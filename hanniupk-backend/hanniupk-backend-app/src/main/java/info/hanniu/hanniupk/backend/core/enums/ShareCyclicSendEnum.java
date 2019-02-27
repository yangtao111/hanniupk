package info.hanniu.hanniupk.backend.core.enums;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @ClassName: ShareCyclicSendEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/29 14:52
 * @Version: 1.0
 */
public enum ShareCyclicSendEnum {
    SHARE_CYCLIC_SEND_YES(0,"发送"),
    SHARE_CYCLIC_SEND_NO(1,"停止");

    private Integer code;

    private String message;

    ShareCyclicSendEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
