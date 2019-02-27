package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhanglj
 * 题目实体类vo
 */
@Data
@ToString
public class QuestionVO {

    private Integer id;
    /**
     * 问题编号
     */
    private String questionNumber;
    /**
     * 问题内容
     */
    private String questionContent;


}