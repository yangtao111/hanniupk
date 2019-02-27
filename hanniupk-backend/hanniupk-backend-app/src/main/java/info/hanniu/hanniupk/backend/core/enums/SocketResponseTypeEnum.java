package info.hanniu.hanniupk.backend.core.enums;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @ClassName: SocketResponseTypeEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/8 16:21
 * @Version: 1.0
 */
public enum  SocketResponseTypeEnum {
    /** pk 响应参数*/
    PK_RESPONSE(0,"pk响应"),
    PK_WITH_FRIEND_RESPONSE(2,"邀请好友对战响应，返回的是双方玩家的信息");
    private Integer code;

    private String message;

    SocketResponseTypeEnum(Integer code, String message) {
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
