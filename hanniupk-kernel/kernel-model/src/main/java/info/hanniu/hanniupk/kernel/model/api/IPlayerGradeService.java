package info.hanniu.hanniupk.kernel.model.api;

import info.hanniu.hanniupk.kernel.model.vo.PlayerGradeVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.api
 * @ClassName: IUserPlayerService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/14 16:46
 * @Version: 1.0
 */
@RequestMapping("api/layerGradeService")
public interface IPlayerGradeService {
    /**
     * selectBy 等级码
     * @param gradeCode
     * @return
     */
   @GetMapping("/selectByGradeCode")
   PlayerGradeVO selectByGradeCode(@RequestParam("gradeCode") Integer gradeCode);
}
