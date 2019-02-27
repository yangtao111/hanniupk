package info.hanniu.hanniupk.backend.modular.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
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
@TableName("player_grade")
@Data
@ToString
public class PlayerGrade implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField("id")
    private Integer id;
    /**
     * 等级码
     */
    @TableField("grade_code")
    private Integer gradeCode;
    /**
     * 等级名称
     */
    @TableField("grade_name")
    private String gradeName;
    /**
     * 开始经验值
     */
    @TableField("start_experience")
    private Integer startExperience;
    /**
     * 结束经验值
     */
    @TableField("end_experience")
    private Integer endExperience;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("sort")
    private Integer sort;
    @TableField("create_by")
    private Integer createBy;
    @TableField("update_by")
    private Integer updateBy;
    @TableField("remark")
    private String remark;

}
