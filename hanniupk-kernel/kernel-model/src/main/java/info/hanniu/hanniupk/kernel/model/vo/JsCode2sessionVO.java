package info.hanniu.hanniupk.kernel.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.vo
 * @ClassName: JsCode2sessionVO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/29 15:56
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class JsCode2sessionVO {
    private String openId;
    private String sessionKey;
    private String unionId;

    public JsCode2sessionVO(String openId, String sessionKey, String unionId) {
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.unionId = unionId;
    }
}
