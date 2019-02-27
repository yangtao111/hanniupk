package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 玩家
 *
 * @author zhanglj
 */
@Data
@ToString
public class PlayerVO {
    /**
     * 玩家ID(用户ID)
     */
    private Long id;
    //最后进入的房间，用于断线重连。当玩家最后一局已结算，此数据要清理。
    private Integer lastRoomId;
    private String lastRoomSign;
    //当前房间，用于判断玩家是否在房间服
    private Integer currentRoomId;
    //进入房间时间(用于清理在房间服的玩家)
    private Date intoRoomTime;


}
