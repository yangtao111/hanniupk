package info.hanniu.hanniupk.backend.modular.dto;

import lombok.Data;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: SocketRequestDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/2 20:48
 * @Version: 1.0
 */
@Data
public class SocketRequestDTO {
    private Integer type;
    /** socket pk 请求参数 */
    private PKRequestDTO pkRequest;

    private PkOnceMoreRequestDTO pkOnceMoreRequest;
}
