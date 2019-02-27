package info.hanniu.hanniupk.kernel.model.util;

import com.alibaba.fastjson.JSONObject;
/**
 * JSON工具类
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.util
 * @ClassName: JsonUtil
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/29 16:59
 * @Version: 1.0
 */
public class JsonUtil {
    /**
     * @Method jsonObject2class
     * @Author tao
     * @Version  1.0
     * @Description
     * @param jsonObject
     * @param tClass
     * @Return java.lang.Object
     * @Exception
     * @Date 2018/9/29 17:05
     */
    public static Object jsonObject2class(JSONObject jsonObject, Class tClass){
        return JSONObject.parseObject(JSONObject.toJSONString(jsonObject),tClass);
    }

    public static Object jsonString2class(String jsonString,Class tClass){
        return JSONObject.parseObject(jsonString,tClass);
    }
}
