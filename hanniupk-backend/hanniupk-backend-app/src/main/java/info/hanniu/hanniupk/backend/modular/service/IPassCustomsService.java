package info.hanniu.hanniupk.backend.modular.service;

import info.hanniu.hanniupk.backend.modular.dto.PassCustomsDTO;
import info.hanniu.hanniupk.backend.modular.vo.PassCustomsVO;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: IPassCustomsService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/19 16:12
 * @Version: 1.0
 */
public interface IPassCustomsService {
    /**
     * 提交答案
     * @param passCustomsDTO
     * @return
     */
    ResponseData submit(PassCustomsDTO passCustomsDTO);
}
