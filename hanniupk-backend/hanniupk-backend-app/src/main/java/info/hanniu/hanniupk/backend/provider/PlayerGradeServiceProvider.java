package info.hanniu.hanniupk.backend.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import info.hanniu.hanniupk.backend.modular.entity.PlayerGrade;
import info.hanniu.hanniupk.backend.modular.service.impl.PlayerGradeServiceImpl;
import info.hanniu.hanniupk.backend.modular.service.impl.UserPlayerServiceImpl;
import info.hanniu.hanniupk.kernel.model.api.IPlayerGradeService;
import info.hanniu.hanniupk.kernel.model.api.IUserPlayerService;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import info.hanniu.hanniupk.kernel.model.vo.PlayerGradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
public class PlayerGradeServiceProvider implements IPlayerGradeService {
    @Autowired
    private PlayerGradeServiceImpl playerGradeService;
    @Override
    public PlayerGradeVO selectByGradeCode(Integer gradeCode) {
        return playerGradeService.selectByGradeCode(gradeCode);
    }
}
