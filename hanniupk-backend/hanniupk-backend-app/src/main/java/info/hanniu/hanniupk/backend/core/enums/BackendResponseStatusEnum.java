package info.hanniu.hanniupk.backend.core.enums;

import lombok.Getter;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.enums
 * @Author: zhanglj
 * @Description: backend 响应枚举类
 * @Date: 2018/10/15 12:51 PM
 * @Version: 1.0.0
 */
public enum BackendResponseStatusEnum {
    /**
     * @Author zhanglj
     * @Description 请求成功
     * @Date 2018/10/15 11:12 AM
     **/
    SHARE_TIME_OUT_SUCCESS(101, "分享超时"),
    /**
     * @Author zhanglj
     * @Description 请求失败
     * @Date 2018/10/15 11:12 AM
     **/
    SHARE_GIVE_UP_SUCCESS(102, "发起人放弃分享"),

    PASS_CUSTOMS_NO_QUESTION_REPOSITORY(103,"题库中获取未能获取选题"),

    PASS_CUSTOMS_NO_QUESTION_CACHE(104,"给玩家分配的随机选题超时失效"),
    SHARE_USED_SUCCESS(105,"分享出去的小程序，这里是2-n次点击，不走分享pk流程,跳转首页"),
    SHARE_OUT_LINE(106,"分享发起者掉线");
    @Getter
    private int code;
    @Getter
    private String message;


    BackendResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
