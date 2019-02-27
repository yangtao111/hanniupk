package info.hanniu.hanniupk.backend.modular.controller;

import info.hanniu.hanniupk.backend.core.enums.SocketResponseTypeEnum;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.dto.SocketRequestDTO;
import info.hanniu.hanniupk.backend.modular.service.AsyncService;
import info.hanniu.hanniupk.backend.modular.service.impl.PkSocketServiceImpl;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.controller
 * @ClassName: PKSocketController
 * @Author: tao
 * @Description: pk中长链接逻辑
 * @Date: 2018/10/9 15:39
 * @Version: 1.0
 */
@Controller
public class PkSocketController {

    private static Logger logger = LoggerFactory.getLogger(PkSocketController.class);

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private PkSocketServiceImpl pkSocketService;

    /**
     * @param
     * @Method pk
     * @Author tao
     * @Version 1.0
     * @Description 用户pk过程中的数据交互
     * @Return void
     * @Exception
     * @Date 2018/10/9 15:54
     */
    //@MessageMapping("/pk")
    @GetMapping("/pk")
    @ResponseBody
    public ResponseData pk(Integer questionIndex,String roomId, Integer userId, Integer questionId, String questionAnswerId, Integer useTime) {
        PKRequestDTO  pkRequest = new PKRequestDTO();
        pkRequest.setRoomId(roomId);
        pkRequest.setUserId(userId);
        pkRequest.setQuestionId(questionId);
        pkRequest.setQuestionAnswerId(questionAnswerId);
        pkRequest.setUseTime(useTime);
        pkRequest.setQuestionIndex(questionIndex);
        pkSocketService.run(pkRequest);
        return ResponseData.success();
    }
}
