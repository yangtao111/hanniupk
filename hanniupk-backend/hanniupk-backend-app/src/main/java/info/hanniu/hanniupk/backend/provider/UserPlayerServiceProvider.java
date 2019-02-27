package info.hanniu.hanniupk.backend.provider;

import info.hanniu.hanniupk.backend.modular.service.impl.UserPlayerServiceImpl;
import info.hanniu.hanniupk.kernel.model.api.IUserPlayerService;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.provider
 * @ClassName: UserPlayerServiceProvider
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 16:56
 * @Version: 1.0
 */
@RestController
@Primary
public class UserPlayerServiceProvider implements IUserPlayerService {
    @Autowired
    private UserPlayerServiceImpl userPlayerService;

    /**
     * 查询 by id
     * @param id
     * @return
     */
    @Override
    public UserPlayer selectById(@RequestParam Integer id){
        return userPlayerService.selectById(id);
    };

    /**
     * 添加或修改
     * @param userPlayer
     */
    @Override
    public void saveOrUpdate(@RequestBody UserPlayer userPlayer){
         userPlayerService.saveOrUpdate(userPlayer);
    };
}
