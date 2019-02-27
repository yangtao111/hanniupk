package info.hanniu.hanniupk.backend.modular.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import info.hanniu.hanniupk.backend.modular.mapper.UserPlayerMapper;
import info.hanniu.hanniupk.kernel.model.api.IUserPlayerService;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: UserPlayerServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/12 20:35
 * @Version: 1.0
 */
@Service
public class UserPlayerServiceImpl extends ServiceImpl<UserPlayerMapper,UserPlayer> {

    public UserPlayer selectById(Long id){
        return this.selectOne(new EntityWrapper<UserPlayer>().eq("id", id));
    }

    public void saveOrUpdate(UserPlayer userPlayer){
        this.insertOrUpdate(userPlayer);
    }
}
