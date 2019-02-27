package info.hanniu.hanniupk.backend.core.variable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.variable
 * @ClassName: CyclicBarrierMap
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/5 14:08
 * @Version: 1.0
 */
public class CyclicBarrierMap {
    /**
     * 存放处理并发的CyclicBarrier对象
     */
    public static ConcurrentHashMap<String, CyclicBarrier> cyclicBarrierMap = new ConcurrentHashMap<>();

    /**
     * pk 中cyclicBarrier 超时时间
     */
    public static Long timeOut = 12L;
}
