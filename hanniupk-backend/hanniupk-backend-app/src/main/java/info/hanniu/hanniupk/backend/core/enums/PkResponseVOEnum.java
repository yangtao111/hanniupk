package info.hanniu.hanniupk.backend.core.enums;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @ClassName: PkResponseVOEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/8 16:22
 * @Version: 1.0
 */
public enum PkResponseVOEnum {
    /***/
    RESULT_CODE_COMMON(0,"正常"),
    RESULT_CODE_OPPONENT_GIVE_UP(1,"对方放弃战斗");
    private Integer code;

    private String message;

    PkResponseVOEnum(Integer code, String message) {
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
