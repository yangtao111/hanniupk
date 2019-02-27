package info.hanniu.hanniupk.kernel.model.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.util
 * @ClassName: MyBeanUtils
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/20 14:43
 * @Version: 1.0
 */
public class MyBeanUtils {
    public static void copyProperties(Object source, Object target) {
        if (null != source && null != target) {
            BeanUtils.copyProperties(source,target);
        }
    }
}
