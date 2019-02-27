package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.vo
 * @Author: zhanglj
 * @Description: 匹配对象返回vo
 * @Date: 2018/10/9 3:43 PM
 * @Version: 1.0.0
 */
@Data
public class GameUserMatchVO {
    /**
     * @Description 房间ID
     **/
    private String roomId;
    /**
     * @Description 对方ID
     **/
    private Integer otherPlayerId;
    /**
     * @Description 对方头像
     **/
    private String otherAvatar;
    /**
     * @Description 对方昵称
     **/
    private String otherNickName;
    /**
     * @Author zhanglj
     * @Description 玩家等级
     **/
    private String gradeName;

    private Integer type;


}
