package info.hanniu.hanniupk.backend.core.enums;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @ClassName: ShareStatusEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/14 14:52
 * @Version: 1.0
 */
public enum  ShareStatusEnum {
    /***/
    SHARE_NORMAL(0,"分享状态正常，继续pk！"),
    SHARE_TIME_OUT(1,"分享超时！"),
    SHARE_GIVE_UP(2,"对方放弃了！"),
    SHARE_USED(3,"已经接受邀请，不可以在走分享pk的流程"),
    SHARE_OUT_LINE(4,"发起者掉线，小程序放到后台，socket断开");
    private Integer code;

    private String message;

    ShareStatusEnum(Integer code, String message) {
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
