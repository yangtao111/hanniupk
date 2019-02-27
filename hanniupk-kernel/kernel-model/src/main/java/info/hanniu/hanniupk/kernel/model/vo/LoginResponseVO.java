package info.hanniu.hanniupk.kernel.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.vo
 * @ClassName: LoginResponseVO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 10:06
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class LoginResponseVO {
    /**
     * 服务器登录凭证
     */
    private String token;
    /**
     * 用户id
     */
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
     * 用户等级名称
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
