package info.hanniu.hanniupk.backend.core.encoder;

import com.alibaba.fastjson.JSONObject;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.core.encoder
 * @ClassName: SocketResponseEncoder
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/26 15:17
 * @Version: 1.0
 */
public class SocketResponseEncoder implements Encoder.Text<ResponseData> {
    @Override
    public String encode(ResponseData responseData) throws EncodeException {
        return JSONObject.toJSONString(responseData);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
