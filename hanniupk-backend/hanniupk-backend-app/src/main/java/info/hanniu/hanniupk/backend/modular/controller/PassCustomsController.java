package info.hanniu.hanniupk.backend.modular.controller;

import info.hanniu.hanniupk.backend.modular.dto.PassCustomsDTO;
import info.hanniu.hanniupk.backend.modular.service.IPassCustomsService;
import info.hanniu.hanniupk.backend.modular.vo.PassCustomsVO;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.controller
 * @ClassName: PassCustomsController
 * @Author: tao
 * @Description: 闯关模块
 * @Date: 2018/11/19 16:05
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/passCustoms")
public class PassCustomsController {

    @Autowired
    private IPassCustomsService iPassCustomsService;

    @PostMapping("/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody PassCustomsDTO passCustomsDTO){
        return iPassCustomsService.submit(passCustomsDTO);
    }
}
