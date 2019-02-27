package info.hanniu.hanniupk.kernel.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: WxLoginDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/13 17:16
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class WxLoginDTO {
    /**
     * 小程序获取openId用
     */
    private String code;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像图片的 URL。
     */
    private String avatarUrl;
    /**
     * 用户性别
     */
    private Integer gender;

    private Integer userId;

}
