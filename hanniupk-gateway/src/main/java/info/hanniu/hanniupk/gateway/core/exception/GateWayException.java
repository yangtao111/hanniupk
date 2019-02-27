package info.hanniu.hanniupk.gateway.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ProjectName: hanniupk-gateway
 * @Package: info.hanniu.hanniupk.gateway.exception
 * @ClassName: GateWayException
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/23 15:47
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
@Data
public class GateWayException extends RuntimeException {

    private Integer code;

    private String errorMessage;

    public GateWayException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public GateWayException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

}
