package info.hanniu.hanniupk.system.util;

import com.alibaba.fastjson.JSONObject;
import info.hanniu.hanniupk.kernel.model.constants.WxConstant;
import info.hanniu.hanniupk.kernel.model.util.HttpUtil;
import info.hanniu.hanniupk.kernel.model.util.JsonUtil;
import info.hanniu.hanniupk.kernel.model.vo.JsCode2sessionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.util
 * @ClassName: WxUtil
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/29 15:48
 * @Version: 1.0
 */
public class WxUtil {

    private static Logger logger = LoggerFactory.getLogger(WxUtil.class);
    private static String JSCODE2SESSIONURL = "https://api.weixin.qq.com/sns/jscode2session";
    public static Long DEFAULT_LOGIN_TIME_OUT_SECS = 3600L;


    /**
     * 获取微信用户身份信息
     * @Method jsCode2session
     * @Author tao
     * @Version  1.0
     * @Description
     * @param code
     * @Return info.hanniu.hanniupk.backend.modular.vo.JsCode2sessionVO
     * @Exception
     * @Date 2018/9/29 17:03
     */
    public static JsCode2sessionVO jsCode2session (String code){
        JSONObject jsonObject = HttpUtil.httpRequest(formatJsCode2sessionUrl(code), HttpUtil.REQUEST_METHOD_GET,null);
        logger.info("wx-jsonObject=>>>>>>>>>>>>>"+jsonObject.toJSONString());
        if (checkJsCode2sessionVO(jsonObject)){
            return (JsCode2sessionVO) JsonUtil.jsonObject2class(jsonObject,JsCode2sessionVO.class);
        }
        return null;
    }
    private static boolean checkJsCode2sessionVO(JSONObject jsonObject){
        if (null == jsonObject) {
            return false;
        }
        logger.info("获取到的openid为{}"+jsonObject.getString(WxConstant.WX_OPEN_ID));
        if (null == jsonObject.getString(WxConstant.WX_OPEN_ID) || "" == jsonObject.getString(WxConstant.WX_OPEN_ID).trim() ) {
            logger.error("微信登录获取openId失败，错误信息：code={},msg={}",
                    jsonObject.getString(WxConstant.WX_RESPONSE_ERR_CODE),
                    jsonObject.getString(WxConstant.WX_RESPONSE_ERR_MESSAGE));
            return false;
        }
        return true;
    }

    /**拼接微信获取用户身份信息的url
     * @Method formatJsCode2sessionUrl
     * @Author tao
     * @Version  1.0
     * @Description
     * @param code
     * @Return java.lang.String
     * @Exception
     * @Date 2018/9/29 17:04
     */
    private static String formatJsCode2sessionUrl(String code) {
        StringBuilder stringBuilder = new StringBuilder(JSCODE2SESSIONURL);
        stringBuilder.append("?appid="+ WxConstant.WX_APP_ID)
                .append("&secret="+ WxConstant.WX_APP_SECRET)
                .append("&js_code="+code)
                .append("&grant_type="+ WxConstant.WX_GRANT_TYPE);
        return stringBuilder.toString();
    }

    /**
     * 创建开发者服务器和开发者的小程序应该的会话密钥
     * @return
     */
    public static String createSessionId(){
        return getRandomByUUID();
    }
    /**
     * 获取随机字符byUUID
     * @return
     */
    public static String getRandomByUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
