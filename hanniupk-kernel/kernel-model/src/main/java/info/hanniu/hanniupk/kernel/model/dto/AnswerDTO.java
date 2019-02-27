package info.hanniu.hanniupk.kernel.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: AnswerDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/9 16:41
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class AnswerDTO {

    /**
     * 选题答案内容
     */
    private String answerContent;
    /**
     * 备选答案编号
     */
    private String answerNumber;

}
