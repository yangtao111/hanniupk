package info.hanniu.hanniupk.system.enums;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.enums
 * @ClassName: RobotActiveEnum
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/21 14:51
 * @Version: 1.0
 */
public enum RobotActiveEnum {
    /***/
    ROBOT_INACTIVE(0,"未使用的机器人"),
    ROBOT_ACTIVE(1,"正在使用的机器人");

    private int code;
    private String message;

    RobotActiveEnum(int code, String message) {
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
