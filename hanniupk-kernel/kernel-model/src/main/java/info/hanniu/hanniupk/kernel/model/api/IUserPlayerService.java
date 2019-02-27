package info.hanniu.hanniupk.kernel.model.api;

import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.api
 * @ClassName: IUserPlayerService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 16:46
 * @Version: 1.0
 */
@RequestMapping("api/userPlayerService")
public interface IUserPlayerService {
    /**
     * 查询 by id
     * @param id
     * @return
     */
    @GetMapping(value = "/selectById")
    UserPlayer selectById(@RequestParam("id") Integer id);

    /**
     * 添加或修改
     * @param userPlayer
     */
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    void saveOrUpdate(@RequestBody UserPlayer userPlayer);
}
