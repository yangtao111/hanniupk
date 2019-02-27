package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 游戏房间
 *
 * @author zhanglj
 */
@Data
@NoArgsConstructor
public class RoomVO implements Serializable {
    /**
     * 房间ID
     */
    private String id;
    /**
     * 签名
     */
    private String sign;

    /**
     * @Description 创建时间(用于清理房间)
     **/
    private Date createTime;
    /**
     * 玩家player1
     */
    private Integer playerId1;
    /**
     * 玩家player2
     */
    private Integer playerId2;

    /**
     * @Author zhanglj
     * @Description 处理请求返回信息，两线程要么同时返回匹配成功，要么同时返回匹配失败
     * @Date 2018/10/18 2:26 PM
     * @Param
     * @return
     **/
    private ReentrantLock reentrantLock;

    /**
     * @Author zhanglj
     * @Description 房间状态 0、空房间 1、等待配对中 2 已配对 3 游戏中
     * @Date 2018/10/9 4:49 PM
     **/
    private int status;

}
