package info.hanniu.hanniupk.backend.modular.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.vo
 * @ClassName: SessionIdValue
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/30 15:54
 * @Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class SessionIdValue implements Serializable {
    private static final long serialVersionUID = 1L;
    private String openId;
    private String sessionKey;

    public SessionIdValue(String openId, String sessionKey) {
        this.openId = openId;
        this.sessionKey = sessionKey;
    }
}
