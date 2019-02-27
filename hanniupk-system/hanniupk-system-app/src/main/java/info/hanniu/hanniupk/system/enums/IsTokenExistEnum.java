package info.hanniu.hanniupk.system.enums;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.enums
 * @ClassName: IsTokenExistEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/16 17:06
 * @Version: 1.0
 */
public enum IsTokenExistEnum {
    /***/
    IS_TOKEN_EXIST_YES(1,"token存在，未过期"),
    IS_TOKEN_EXIST_NO(0,"token不正确，过期");

    private int code;
    private String message;

    IsTokenExistEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
