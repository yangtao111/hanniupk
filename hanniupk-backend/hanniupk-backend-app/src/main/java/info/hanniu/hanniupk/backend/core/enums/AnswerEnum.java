package info.hanniu.hanniupk.backend.core.enums;

import lombok.Getter;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @Author: zhanglj
 * @Description: TODO
 * @Date: 2018/10/28 6:33 PM
 * @Version: 1.0.0
 */
public enum AnswerEnum {
    ANSWER_RIGHT(1, "正确"),
    ANSWER_FALSE(0, "错误");

    AnswerEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Getter
    private Integer code;
    @Getter
    private String message;
}
