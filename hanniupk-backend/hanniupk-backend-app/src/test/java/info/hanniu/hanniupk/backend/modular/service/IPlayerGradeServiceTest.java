package info.hanniu.hanniupk.backend.modular.service;

import info.hanniu.hanniupk.backend.modular.entity.PlayerGrade;
import info.hanniu.hanniupk.backend.modular.service.impl.PlayerGradeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: IPlayerGradeServiceTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/13 15:50
 * @Version: 1.0
 */
public class IPlayerGradeServiceTest extends BaseTest{
    @Autowired
    private PlayerGradeServiceImpl iPlayerGradeService;
    @Test
    public void selectList() throws Exception {
        List<PlayerGrade> playerGrades = iPlayerGradeService.selectList();
        System.out.println(playerGrades);
    }

}