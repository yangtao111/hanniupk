package info.hanniu.hanniupk.kernel.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: PlayerResultDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/12 12:54
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class PlayerResultDTO {
    /**
     * 计算后的积分
     */
    private String integral;
    /**
     * 用户的userId
     */
    private Integer userId;
    /**
     * 答案是否正确
     */
    private Integer result;
    /**
     * 本局所获的金币数
     */
    private Integer coin;
    /**
     * 是否为胜利者
     */
    private Integer winner;

    public PlayerResultDTO(String integral, Integer userId, Integer result) {
        this.integral = integral;
        this.userId = userId;
        this.result = result;
    }

    public PlayerResultDTO(String integral, Integer userId, Integer result, Integer coin, Integer winner) {
        this.integral = integral;
        this.userId = userId;
        this.result = result;
        this.coin = coin;
        this.winner = winner;
    }


}
