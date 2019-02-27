package info.hanniu.hanniupk.gateway.modular.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.controller
 * @ClassName: IndexController
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/17 14:41
 * @Version: 1.0
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "home";
    }
}
