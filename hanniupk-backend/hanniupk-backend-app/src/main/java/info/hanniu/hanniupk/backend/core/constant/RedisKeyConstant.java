package info.hanniu.hanniupk.backend.core.constant;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.constant
 * @Author: zhanglj
 * @Description: reids key常量
 * @Date: 2018/10/10 12:29 PM
 * @Version: 1.0.0
 */
public class RedisKeyConstant {
    public static final Long DEFAULT_LOGIN_TIME_OUT_SECS = 3600L;
    public static final Long SHARE_FRIEND_TIME_OUT_SECS = 600L;

    /**
     * @Author zhanglj
     * @Description 缓存游戏房间key
     * @Date  2018/10/10 12:33 PM
     * @Param
     * @return
     **/
    public static final String ROOM_CASHE_KEY = "ROOM_";

    /**
     * @Author zhanglj
     * @Description
     * @Date  2018/10/10 12:33 PM
     * @Param
     * @return
     **/
    public static final String QUESTION_CASHE_KEY = "PK_QUESTION";

    /**
     * @Author zhanglj
     * @Description 缓存问题集合中每一个问题map的key集合key
     * @Date  2018/10/10 12:33 PM
     **/
    public static final String QUESTION_MAP_SET_KEY_CASHE_KEY = "PK_QUESTION_KEY_SET";

    /**
     * 缓存房间问题集合key前缀
     */
    public static final String ROOM_QUESTIONS_PREFIX = "ROOM_QUESTIONS_";

    /**
     * 请求第一题，保存于redis中的标志。
     */
    public static final String FIRST_QUESTION_GET_SIGN = "FIRST_QUESTION_GET_SIGN_";
    /**
     * 请求第二题及以上，保存于redis中的标志。
     */
    public static final String OTHER_QUESTION_GET_SIGN = "OTHER_QUESTION_GET_SIGN_";
    /**
     * 保存于redis中的玩家答题结果前缀
     */
    public static final String PLAYER_RESULT_PREFIX = "PLAYER_RESULT_";
    /**
     * 心跳日志key
     */
    public static final String HEART_BEAT_LOG = "HEART_BEAT_LOG_";
    /**
     * pk过程中每次返回的数据key前缀，后面连接roomId
     */
    public static final String PK_RESPONSE_DATA_PREFIX_ = "PK_RESPONSE_DATA_PREFIX_";

    /**
     * 分享给好友，用于判断，超时、放弃战斗
     */
    public static final String PK_SHARE_KEY = "PK_SHARE_KEY_";
    /**
     * 闯关模式，缓存玩家闯关选题的key
     */
    public static final String PASS_CUSTOMS_QUESTION_KEY_PREFIX = "PASS_CUSTOMS_QUESTION_KEY_";

    public static final String REENTRANT_LOCK_KEY_PREFIX = "REENTRANT_LOCK_KEY_PREFIX_";

    public static final String ROBOT_AUTO_ANSWER_REQUEST_LOG = "robot_auto_answer_request_log_";
}
