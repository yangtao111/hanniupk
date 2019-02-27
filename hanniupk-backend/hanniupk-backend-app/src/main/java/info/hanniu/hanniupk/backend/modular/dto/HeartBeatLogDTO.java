package info.hanniu.hanniupk.backend.modular.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: HeartBeatLogDTO
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/26 21:16
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartBeatLogDTO {
    private String roomId;
    private String createTime;
    private Integer userId;
}
