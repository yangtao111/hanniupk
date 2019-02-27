package info.hanniu.hanniupk.backend.modular.vo;

import info.hanniu.hanniupk.kernel.model.dto.PlayerResultDTO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.vo
 * @ClassName: PKResponseVO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/9 15:42
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class PKResponseVO {

    private List<PlayerResultDTO> playerResults;

    /**
     * 下一题内容
     */
    private QuestionDTO question;
    /**
     * 是否是最后一个问题
     */
    private Integer hasNextQuestion;
    /**
     * @Author zhanglj
     * @Description 正确答案内容
     * @Date 2018/10/29 2:05 PM
     **/
    private String rightAnswerContent;
    /**
     *取得结果原因，正常流程，或者断网，或者放弃
     */
    private Integer resultCode;
    /**
     * 表示返回的是那种类型的响应结果
     */
    private Integer type;

    public PKResponseVO(List<PlayerResultDTO> playerResults, QuestionDTO question, Integer hasNextQuestion) {
        this.playerResults = playerResults;
        this.question = question;
        this.hasNextQuestion = hasNextQuestion;
    }
    public PKResponseVO(List<PlayerResultDTO> playerResults, QuestionDTO question, Integer hasNextQuestion,String rightAnswerContent) {
        this.playerResults = playerResults;
        this.question = question;
        this.hasNextQuestion = hasNextQuestion;
        this.rightAnswerContent = rightAnswerContent;
    }

}
