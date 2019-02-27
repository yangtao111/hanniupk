package info.hanniu.hanniupk.kernel.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.entity
 * @ClassName: PlayerGrade
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/12 18:52
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class PlayerGradeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 等级码
     */
    private Integer gradeCode;
    /**
     * 等级名称
     */
    private String gradeName;
    /**
     * 开始经验值
     */
    private Integer startExperience;
    /**
     * 结束经验值
     */
    private Integer endExperience;
    private Date createTime;
    private Date updateTime;
    private Integer sort;
    private Integer createBy;
    private Integer updateBy;
    private String remark;

}
