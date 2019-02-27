package info.hanniu.hanniupk.backend.modular.dto;

import lombok.Data;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: PassCustomsDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/19 16:10
 * @Version: 1.0
 */
@Data
public class PassCustomsDTO {
    /** 玩家id */
    private Integer userId;
    /** 选题ID,这里是所答选题的ID，第一次请求为空 */
    private Integer questionId;
    /** 玩家所给答案ID，第一次请求时为空 */
    private String questionAnswerId;
}
