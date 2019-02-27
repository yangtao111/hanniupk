package info.hanniu.hanniupk.backend.modular.service.impl;

import com.netflix.discovery.converters.Auto;
import info.hanniu.hanniupk.backend.core.constant.BackendConstant;
import info.hanniu.hanniupk.backend.core.constant.RedisKeyConstant;
import info.hanniu.hanniupk.backend.core.enums.RoomStatusEnum;
import info.hanniu.hanniupk.backend.core.variable.PkCacheMap;
import info.hanniu.hanniupk.backend.modular.consumer.AuthServiceConsumer;
import info.hanniu.hanniupk.backend.modular.consumer.SysUserServiceConsumer;
import info.hanniu.hanniupk.backend.modular.entity.PlayerGrade;
import info.hanniu.hanniupk.backend.modular.mapper.PlayerGradeMapper;
import info.hanniu.hanniupk.backend.modular.mapper.UserPlayerMapper;
import info.hanniu.hanniupk.backend.modular.service.FightService;
import info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO;
import info.hanniu.hanniupk.backend.modular.vo.RoomVO;
import info.hanniu.hanniupk.backend.util.RandomUtils;
import info.hanniu.hanniupk.backend.util.RedisKeyGenerateUtil;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @Author: zhanglj
 * @Description: TODO
 * @Date: 2018/10/9 2:50 PM
 * @Version: 1.0.0
 */
@Service
public class FightServiceImpl implements FightService {
    @Autowired
    private SysUserServiceConsumer sysUserServiceConsumer;
    @Autowired
    private AuthServiceConsumer authServiceConsumer;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private UserPlayerMapper userPlayerMapper;
    @Autowired
    private PlayerGradeMapper playerGradeMapper;
    @Autowired
    private RobotServiceImpl robotService;

    /**
     * @Author zhanglj
     * @Description 用于相互匹配的两个线程交换信息
     **/
    private Exchanger<SysUserDTO> exchanger = new Exchanger<>();


    /**
     * @ProjectName: hanniupk-backend
     * @Package: info.hanniu.hanniupk.backend.modular.service
     * @Author: zhanglj
     * @Description: pk接口
     * @Date: 2018/10/9 2:48 PM
     * @Version: 1.0.0
     */
    @Override
    public Callable<ResponseData> getGameUserMatchCallable(FightService fightService, String token) {
        Callable<ResponseData> callable = () -> {
            LogUtil.info("内部线程：" + Thread.currentThread().getName());
            return fightService.getGameUserMatch(token);
        };
        threadPoolTaskExecutor.submit(callable);
        return callable;
    }

    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO
     * @Author zhanglj
     * @Description 异步匹配对手相关信息
     * @Date 2018/10/9 4:08 PM
     * @Param [token]
     **/
    @Override
    public DeferredResult<ResponseData> getGameUserMatchDeferredResult(FightService fightService, String token) {
//        LogUtil.info("外部线程>>>>" + Thread.currentThread().getName() + "<<<<<开始。");
        //设置超时时间
        DeferredResult<ResponseData> result = new DeferredResult<ResponseData>(BackendConstant.DEFERRED_RESULT_TIMEOUT);
        //处理超时事件 采用委托机制
        result.onTimeout(() -> {
            LogUtil.info("DeferredResult超时");
//                result.setResult("超时了!");
        });
        result.onCompletion(new Runnable() {

            @Override
            public void run() {
                //完成后
                LogUtil.info("调用完成");
            }
        });
        threadPoolTaskExecutor.execute(() -> {
            //处理业务逻辑
            LogUtil.info("内部线程：》》》》{}《《《《开始！" + Thread.currentThread().getName());
            //返回结果
            ResponseData responseData = fightService.getGameUserMatch(token);
            result.setResult(responseData);
//                LogUtil.info("内部线程：》》》》{}《《《《结束！" + Thread.currentThread().getName());
        });
//        LogUtil.info("外部线程>>>>" + Thread.currentThread().getName() + "<<<<<结束。");
        return result;
    }

    /**
     * @return java.lang.String
     * @Author zhanglj
     * @Description 邀请好友获取房间id
     * @Date 2018/10/20 12:04 PM
     * @Param [token]
     **/
    @Override
    public ResponseData setInviteFriendRoom(Integer currentUserId, Integer opponentUserId) {
        return ResponseData.success(matchFriends(currentUserId,opponentUserId).getId());
    }
    public RoomVO matchFriends(Integer currentUserId, Integer opponentUserId){
        SysUserDTO currentSysUser = new SysUserDTO();
        currentSysUser.setPlayerId(currentUserId);
        SysUserDTO opponentUser = new SysUserDTO();
        opponentUser.setPlayerId(opponentUserId);
        //生成roomCode,userId拼接6位随机数
        currentSysUser.setRoomCode(Integer.parseInt(currentUserId.toString()+ RandomUtils.generateFixdNumString(6)));
        opponentUser.setRoomCode(Integer.parseInt(opponentUserId.toString()+ RandomUtils.generateFixdNumString(6)));
        RoomVO roomVO = this.getRoomVO(currentSysUser, opponentUser);
        BoundHashOperations<String, String, RoomVO> boundHashOps = this.redisTemplate.boundHashOps(RedisKeyGenerateUtil.getRoomKeyByRoomId(roomVO.getId()));
        boundHashOps.expire(BackendConstant.ROOM_EXPIRE_TIME, TimeUnit.MINUTES);
        boundHashOps.put(roomVO.getId(), roomVO);
        //把随机选择的题放入redis中
        Map<Integer, QuestionDTO> questionsMap = this.redisService.getQuestionByRandom(BackendConstant.QUESTION_NUMBER);
        this.redisService.putRoomQuestionRedis(roomVO.getId(), questionsMap);
        LogUtil.info("roomId=" + roomVO.getId() + ", questionMap=" + questionsMap);
        return roomVO;
    }

    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO
     * @Author zhanglj
     * @Description 匹配对手相关信息
     * @Date 2018/10/9 4:08 PM
     * @Param [token]
     **/
    @Override
    public ResponseData getGameUserMatch(String token) {
        boolean isRobot = false;
        //调用服务获取当前用户信息
        SysUserDTO currentSysUserDTO = this.sysUserServiceConsumer.getSysUserByToken(token);
        //生成roomCode,userId拼接6位随机数
        String roomCode = currentSysUserDTO.getUserId().toString()+ RandomUtils.generateFixdNumString(6);
        currentSysUserDTO.setRoomCode(Integer.parseInt(roomCode));
        //对手信息
        SysUserDTO opponentUserInfo;
        try {
            //两个线程同时到达，立马交换对方信息进行匹配
            opponentUserInfo = exchanger.exchange(currentSysUserDTO, BackendConstant.MATCH_TIMEOUT, TimeUnit.SECONDS);
            //防止用户自己匹配自己
            if (opponentUserInfo.getPlayerId().equals(currentSysUserDTO.getPlayerId())) {
                return ResponseData.error("匹配失败，请重新匹配-matchSelf");
            }
        } catch (Exception e) {
            opponentUserInfo = matchRobot();
            if (null == opponentUserInfo) {
                return ResponseData.error("匹配超时，请重新匹配");
            } else {
                isRobot = true;
            }
        }
        RoomVO room = getRoomVO(currentSysUserDTO, opponentUserInfo);
        BoundHashOperations<String, String, RoomVO> boundHashOps = this.redisTemplate.boundHashOps(RedisKeyGenerateUtil.getRoomKeyByRoomId(room.getId()));
        boundHashOps.expire(BackendConstant.ROOM_EXPIRE_TIME, TimeUnit.MINUTES);
        //把房间信息存入Redis
        boundHashOps.put(room.getId(), room);
        //设置返回结果信息
        GameUserMatchVO gameUserMatchVO = getGameUserMatchVO(opponentUserInfo, room);
        //把随机选择的题放入redis中
        Map<Integer, QuestionDTO> questionsMap = this.redisService.getQuestionByRandom(BackendConstant.QUESTION_NUMBER);
        this.redisService.putRoomQuestionRedis(room.getId(), questionsMap);
        LogUtil.info("roomId=" + room.getId() + ", questionMap=" + questionsMap);
        LogUtil.info("player(id=" + room.getPlayerId1().toString() + "),player(id=" + room.getPlayerId2().toString() + ")匹配成功,房间id=" + room.getId());
        if (isRobot) {
            robotService.setRobotRequestLog(RobotServiceImpl.QUESTION_INDEX_INIT,
                    room.getId(),opponentUserInfo.getUserId(),null,null,null);
        }
        return ResponseData.success(gameUserMatchVO);
    }

    /**
     * 随机匹配一个机器人
     * @return
     */
    private SysUserDTO matchRobot() {
        LogUtil.info("--->匹配个机器人！！！<---");
        List<SysUserDTO> robots = this.sysUserServiceConsumer.getRobotsInactive();
        if (null == robots || robots.size() <=0 ) {
            return null;
        }
        Integer target = new Random().nextInt(robots.size());
        this.sysUserServiceConsumer.setRobotActive(robots.get(target).getUserId());
        SysUserDTO robot = robots.get(target);
        //生成roomCode,userId拼接6位随机数
        String roomCode = robot.getUserId().toString()+ RandomUtils.generateFixdNumString(6);
        robot.setRoomCode(Integer.parseInt(roomCode));
        return robot;
    }


    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO
     * @Author zhanglj
     * @Description 生成匹配信息
     * @Date 2018/11/1 11:34 AM
     * @Param [opponentUserInfo, room]
     **/
    public GameUserMatchVO getGameUserMatchVO(SysUserDTO opponentUserInfo, RoomVO room) {
        GameUserMatchVO gameUserMatchVO = new GameUserMatchVO();
        gameUserMatchVO.setOtherAvatar(opponentUserInfo.getAvatarUrl());
        gameUserMatchVO.setOtherNickName(opponentUserInfo.getNickName());
        gameUserMatchVO.setOtherPlayerId(opponentUserInfo.getPlayerId());
        gameUserMatchVO.setRoomId(room.getId());
        UserPlayer userPlayer = this.userPlayerMapper.selectById(opponentUserInfo.getPlayerId());
        PlayerGrade playerGrade = new PlayerGrade();
        playerGrade.setGradeCode(userPlayer.getGradeCode());
        playerGrade = this.playerGradeMapper.selectOne(playerGrade);
        gameUserMatchVO.setGradeName(playerGrade.getGradeName());
        return gameUserMatchVO;
    }

    /**
     * @return info.hanniu.hanniupk.backend.modular.vo.RoomVO
     * @Author zhanglj
     * @Description 生成roomVO
     * @Date 2018/11/1 11:26 AM
     * @Param [currentSysUserDTO, opponentUserInfo]
     **/
    private RoomVO getRoomVO(SysUserDTO currentSysUserDTO, SysUserDTO opponentUserInfo) {
        RoomVO room = new RoomVO();
        StringBuilder roomIdSb = new StringBuilder("");
        //房间id 生成规则(用户较小的roomCode拼接用户较大的roomCode)
        if (currentSysUserDTO.getRoomCode() > opponentUserInfo.getRoomCode()) {
            roomIdSb.append(opponentUserInfo.getRoomCode().toString()).append(currentSysUserDTO.getRoomCode().toString());
        } else {
            roomIdSb.append(currentSysUserDTO.getRoomCode().toString()).append(opponentUserInfo.getRoomCode().toString());
        }
        room.setId(roomIdSb.toString());
        room.setCreateTime(new Date());
        room.setStatus(RoomStatusEnum.已配对.getCode());
        room.setPlayerId1(currentSysUserDTO.getPlayerId());
        room.setPlayerId2(opponentUserInfo.getPlayerId());
        saveReentrantLockByRoomId(roomIdSb.toString());
        return room;
    }
    private void saveReentrantLockByRoomId(String roomId){
        PkCacheMap.setLockByRoomId(roomId,new ReentrantLock());
    }
}
