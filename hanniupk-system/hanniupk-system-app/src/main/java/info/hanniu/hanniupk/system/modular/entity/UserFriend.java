package info.hanniu.hanniupk.system.modular.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: hanniupk-system
 * @Package: info.hanniu.hanniupk.system.modular.entity
 * @Author: zhanglj
 * @Description: 用户好友双向关系表关系表
 * @Date: 2018/10/29 3:56 PM
 * @Version: 1.0.0
 */
@TableName("user_friend")
@Data
public class UserFriend {
    /**
     * @Author zhanglj
     * @Description 主键ID
     * @Date 2018/10/29 3:59 PM
     **/
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * @Author zhanglj
     * @Description 插入数据时、保证user_id<friend_id
     * @Date 2018/10/29 4:01 PM
     **/
    @TableField("user_id")
    private Integer userId;
    /**
     * @Author zhanglj
     * @Description 插入数据时、保证user_id<friend_id
     * @Date 2018/10/29 4:02 PM
     **/
    @TableField("friend_id")
    private Integer friendId;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;

}
