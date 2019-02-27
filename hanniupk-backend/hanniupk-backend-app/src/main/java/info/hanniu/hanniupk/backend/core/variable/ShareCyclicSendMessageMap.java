package info.hanniu.hanniupk.backend.core.variable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.variable
 * @ClassName: ShareCyclicSendMessageMap
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/29 14:49
 * @Version: 1.0
 */
public class ShareCyclicSendMessageMap {

    public static ConcurrentMap<Integer,Integer> shareCyclicSendMessageMap = new ConcurrentHashMap<>();
}
