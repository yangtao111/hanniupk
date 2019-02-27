package info.hanniu.hanniupk.backend.modular.service.impl;

import com.alibaba.fastjson.JSON;
import info.hanniu.hanniupk.backend.core.constant.RedisKeyConstant;
import info.hanniu.hanniupk.backend.modular.dto.PkOnceMoreRequestDTO;
import info.hanniu.hanniupk.backend.modular.service.BaseTest;
import info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO;
import info.hanniu.hanniupk.backend.modular.vo.RoomVO;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: InviteFriendServiceImplTest
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/8 15:49
 * @Version: 1.0
 */
public class InviteFriendServiceImplTest extends BaseTest{
    @Autowired
    private InviteFriendServiceImpl inviteFriendService;
    @Test
    public void getUsersInfo() throws Exception {
        PkOnceMoreRequestDTO pkOnceMoreRequestDTO = new PkOnceMoreRequestDTO();
        pkOnceMoreRequestDTO.setCurrentUserId(1);
        pkOnceMoreRequestDTO.setOpponentUserId(2);
        RoomVO roomVO = new RoomVO();
        roomVO.setId("15");
     //  List<GameUserMatchVO> gameUserMatchVOS =  inviteFriendService.getUsersInfo(pkOnceMoreRequestDTO,roomVO);
     //  System.out.println("--->gameUserMatchVOS="+gameUserMatchVOS.toString());
    }
}