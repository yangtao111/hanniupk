package info.hanniu.hanniupk.backend.modular.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: PKRequestDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/9 15:43
 * @Version: 1.0
 */
@Data
public class PKRequestDTO {
    /**
     * @Author zhanglj
     * @Description  问题索引
     **/
    private Integer questionIndex;


    /**
     * 房间ID
     */
    private String roomId;
    private Integer userId;
    /**
     * 选题ID
     */
    private Integer questionId;
    /**
     * 用户答案
     */
    private String questionAnswerId;
    /**
     * 答题所用的时间秒
     */
    private Integer useTime;
    /**
     * 机器人自动回复的 时间点
     */
    private Date activeTime;

}
