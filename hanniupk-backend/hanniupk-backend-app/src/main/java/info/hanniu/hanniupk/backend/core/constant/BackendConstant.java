package info.hanniu.hanniupk.backend.core.constant;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.constant
 * @Author: zhanglj
 * @Description: backend 常量接口
 * @Date: 2018/10/14 11:17 PM
 * @Version: 1.0.0
 */
public interface BackendConstant {

    /**
     * @Author zhanglj
     * @Description 匹配超时时间
     * @Date 2018/10/14 11:29 PM
     **/
    Integer MATCH_TIMEOUT = 6;
    /**
     * @Author zhanglj
     * @Description request请求异步响应超时时间
     * @Date 2018/10/14 11:35 PM
     **/
    Long DEFERRED_RESULT_TIMEOUT = 20000L;
    /**
     * @Author zhanglj
     * @Description 答题数量
     * @Date 2018/10/14 11:35 PM
     **/
    Integer QUESTION_NUMBER = 5;
    /**
     * @Author zhanglj
     * @Description 房间过期时间
     * @Date 2018/11/1 2:21 PM
     **/
    Integer ROOM_EXPIRE_TIME = 5;
    /**
     * @Author tao
     * @Version  1.0
     * @Description 闯关模式的选题数量
     * @Date 2018/11/19 16:28
     */
    Integer PASS_CUSTOMS_QUESTION_NUMBER = 500;


}
