package info.hanniu.hanniupk.backend.core.enums;

import lombok.Getter;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core
 * @Author: zhanglj
 * @Description: TODO
 * @Date: 2018/10/11 2:32 PM
 * @Version: 1.0.0
 */
public enum RoomStatusEnum {
    空房间(0, "空房间"),
    待配对(1, "待配对"),
    已配对(2, "已配对"),
    游戏中(3, "游戏中"),
    ;
    @Getter
    private int code;
    @Getter
    private String value;

    RoomStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}
