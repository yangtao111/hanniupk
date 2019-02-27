package info.hanniu.hanniupk.backend.core.enums;

import lombok.Data;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @ClassName: SocketRequestTypeEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/2 20:49
 * @Version: 1.0
 */
public enum SocketRequestTypeEnum {
    /** pk 过程请求参数 */
    PK_REQUEST(0,"pk请求"),
    ONCE_MORE_REQUEST(1,"再来一次请求"),
    PK_WITH_FRIEND_REQUEST(2,"邀请好友对战请求")
    ;
    private Integer code;

    private String message;

    SocketRequestTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

}
