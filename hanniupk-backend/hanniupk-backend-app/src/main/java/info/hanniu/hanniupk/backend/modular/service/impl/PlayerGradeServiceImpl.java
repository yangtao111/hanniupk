package info.hanniu.hanniupk.backend.modular.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import info.hanniu.hanniupk.backend.modular.entity.PlayerGrade;
import info.hanniu.hanniupk.backend.modular.mapper.PlayerGradeMapper;
import info.hanniu.hanniupk.kernel.model.api.IPlayerGradeService;
import info.hanniu.hanniupk.kernel.model.util.MyBeanUtils;
import info.hanniu.hanniupk.kernel.model.vo.PlayerGradeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: PlayerGradeServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/12 20:51
 * @Version: 1.0
 */
@Service
public class PlayerGradeServiceImpl extends ServiceImpl<PlayerGradeMapper, PlayerGrade> {
    public List<PlayerGrade> selectList() {
        return this.selectList(new EntityWrapper<PlayerGrade>());
    }

    public PlayerGradeVO selectByGradeCode(Integer gradeCode) {
        PlayerGrade playerGrade = this.selectOne(new EntityWrapper<PlayerGrade>().eq("grade_code", gradeCode));
        if (null == playerGrade) {
            return null;
        }
        PlayerGradeVO playerGradeVO = new PlayerGradeVO();
        MyBeanUtils.copyProperties(playerGrade, playerGradeVO);
        return playerGradeVO;
    }

}
