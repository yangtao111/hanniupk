package info.hanniu.hanniupk.backend.core.util;

import com.alibaba.fastjson.JSONObject;
import info.hanniu.hanniupk.kernel.model.util.JsonUtil;
import info.hanniu.hanniupk.kernel.model.vo.JsCode2sessionVO;
import org.junit.Assert;
import org.junit.Test;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.util
 * @ClassName: JsonUtilTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 15:04
 * @Version: 1.0
 */
public class JsonUtilTest {
    @Test
    public void jsonObject2class() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId","aaaaaaa");
        jsonObject.put("sessionKey","kkkkkkkk");
        jsonObject.put("unionId","uuuuuuuu");
        JsCode2sessionVO jsCode2sessionVO =  (JsCode2sessionVO) JsonUtil.jsonObject2class(jsonObject,JsCode2sessionVO.class);
        Assert.assertNotNull(jsCode2sessionVO);
    }

}