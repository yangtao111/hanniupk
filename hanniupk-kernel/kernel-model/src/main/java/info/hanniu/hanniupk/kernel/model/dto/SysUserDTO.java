package info.hanniu.hanniupk.kernel.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.dto
 * @ClassName: SysUserDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 18:40
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class SysUserDTO {

    private Integer userId;
    /**
     * 性别（M：男 F：女）
     */
    private String sex;
    /**
     * 微信用户身份标识
     */
    private String openId;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * @Author zhanglj
     * @Description 昵称
     **/
    private String nickName;

    private Integer playerId;

    private Date createTime;
    /** 机器人状态 */
    private Integer robotActive;
    /**
     * @Author zhanglj
     * @Description 房间code码，用于生成roomId, roomCode=userId拼接6位随机数
     * @Date  2018/11/21 9:02 PM
     **/
    private Integer roomCode;


    public SysUserDTO(String openId) {
        this.openId = openId;
    }


}
