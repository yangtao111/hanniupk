package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用户排名
 *
 * @author zhanglj
 */
@Data
@ToString
public class UserRankVO implements Serializable {
    /**
     * @Author zhanglj
     * @Description 序号
     * @Date 2018/10/21 4:38 PM
     **/
    private Integer serialNumber;
    /**
     * @Author zhanglj
     * @Description 头像
     * @Date 2018/10/21 4:38 PM
     **/
    private String avatarUrl;
    /**
     * @Author zhanglj
     * @Description 昵称
     * @Date 2018/10/21 4:39 PM
     **/
    private String nickName;
    /**
     * @Author zhanglj
     * @Description 等级名称
     * @Date 2018/10/21 4:39 PM
     **/
    private String gradeName;
    /**
     * @Author zhanglj
     * @Description 牛币
     * @Date 2018/10/21 4:39 PM
     **/
    private Integer coin;

    /**
     * @Author zhanglj
     * @Description 经验
     * @Date  2018/11/27 5:51 PM
     **/
    private Integer experience;


}
