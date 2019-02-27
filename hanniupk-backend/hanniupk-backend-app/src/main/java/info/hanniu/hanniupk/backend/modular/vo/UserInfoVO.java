package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.vo
 * @ClassName: UserInfoVO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 10:03
 * @Version: 1.0
 */
@Data
public class UserInfoVO {

    private Integer userId;
    /**
     * 性别（M：男 F：女）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户等级码
     */
    private String gradeName;
    /**
     * 经验值
     */
    private Integer experience;
    /**
     * 用户金币
     */
    private Integer coin;

}
