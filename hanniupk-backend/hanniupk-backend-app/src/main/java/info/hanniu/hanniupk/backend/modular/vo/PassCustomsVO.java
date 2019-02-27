package info.hanniu.hanniupk.backend.modular.vo;

import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import lombok.Data;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.vo
 * @ClassName: PassCustomsVO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/19 16:10
 * @Version: 1.0
 */
@Data
public class PassCustomsVO {

    /**
     * 下一题内容
     */
    private QuestionDTO question;

    /**
     * 是否是最后一个问题
     */
    private Integer hasNextQuestion;

    /**
     * 答案是否正确
     */
    private Integer result;

}
