package info.hanniu.hanniupk.kernel.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: QuestionDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/9 16:39
 * @Version: 1.0
 */
@Data
public class QuestionDTO {

    /**
     * 问题索引
     */
    private Integer questionIndex;

    /**
     * 问题编号
     */
    private Integer questionNumber;
    /**
     * 正确答案编号
     */
    private String rightAnswerNumber;
    /**
     * 问题内容
     */
    private String questionContent;
    /**
     * @Author zhanglj
     * @Description 排序
     * @Date 2018/10/17 6:36 PM
     **/
    private int sort;
    /**
     * @Author zhanglj
     * @Description 问题类型(诗词 ， 成语, 天文 、 地理等等)
     * @Date 2018/10/17 6:37 PM
     **/
    private String questionType;
    /**
     * @Author zhanglj
     * @Description 答题形式(选择 、 填空 、 判断)
     * @Date 2018/10/17 6:39 PM
     **/
    private String answerType;

    /**
     * 备选答案列表
     */
    private List<AnswerDTO> answerList;

    /**
     * 备选答案
     */
    private String answerListStr;

}
