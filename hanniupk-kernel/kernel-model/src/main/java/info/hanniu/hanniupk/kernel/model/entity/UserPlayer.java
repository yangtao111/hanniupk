package info.hanniu.hanniupk.kernel.model.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 删
 *
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.entity
 * @ClassName: UserPlayer
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/12 18:40
 * @Version: 1.0
 */
@TableName("user_player")
@Data
@ToString
@NoArgsConstructor
public class UserPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Integer id;
    /**
     * 用户等级码
     */
    @TableField("grade_code")
    private Integer gradeCode;
    /**
     * 经验值
     */
    @TableField("experience")
    private Integer experience;
    /**
     * 用户金币
     */
    @TableField("coin")
    private Integer coin;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("create_by")
    private Integer createBy;
    @TableField("update_by")
    private Integer updateBy;
    @TableField("remark")
    private String remark;

}
