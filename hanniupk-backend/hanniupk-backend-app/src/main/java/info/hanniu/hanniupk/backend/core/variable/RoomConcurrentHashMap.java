package info.hanniu.hanniupk.backend.core.variable;

import info.hanniu.hanniupk.backend.modular.vo.RoomVO;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.variable
 * @Author: zhanglj
 * @Description: 整个jvm 全局变量(单例)
 * @Date: 2018/10/12 3:19 PM
 * @Version: 1.0.0
 */
public class RoomConcurrentHashMap {
    /**
     * @Author zhanglj
     * @Description 用户存放房间对象
     * @Date  2018/10/12 3:25 PM
     **/
    private static ConcurrentHashMap<String, RoomVO> instance = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, RoomVO> getInstance() {
        return instance;
    }
}
