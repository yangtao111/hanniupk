package info.hanniu.hanniupk.backend.modular.dto;

import lombok.Data;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: PkOnceMoreRequestDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/6 20:58
 * @Version: 1.0
 */
@Data
public class PkOnceMoreRequestDTO {
    /** 再来一局发起人 */
    private Integer currentUserId;
    /** 再来一局被邀请人 */
    private Integer opponentUserId;
    /** 构造分享缓存key的发起人userId+随机数 */
    private String randomUserId;
}
