package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.core.enums.ShareCyclicSendEnum;
import info.hanniu.hanniupk.backend.core.enums.SocketResponseTypeEnum;
import info.hanniu.hanniupk.backend.core.variable.PkCacheMap;
import info.hanniu.hanniupk.backend.core.variable.ShareCyclicSendMessageMap;
import info.hanniu.hanniupk.backend.core.variable.WebSocketMap;
import info.hanniu.hanniupk.backend.modular.consumer.SysUserServiceConsumer;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.dto.PkOnceMoreRequestDTO;
import info.hanniu.hanniupk.backend.modular.vo.GameUserMatchVO;
import info.hanniu.hanniupk.backend.modular.vo.RoomVO;
import info.hanniu.hanniupk.backend.socket.MessageWebSocket;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: InviteFriendServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/6 21:12
 * @Version: 1.0
 */
@Service
public class InviteFriendServiceImpl {
    @Autowired
    private FightServiceImpl fightService;
    @Autowired
    private PkSocketServiceImpl pkSocketService;
    @Autowired
    private SysUserServiceConsumer sysUserServiceConsumer;
    @Autowired
    private RedisServiceImpl redisService;

    /**
     * 向被邀请人发送消息
     *
     * @param pkOnceMoreRequest
     */
    public void inviteFriend(PkOnceMoreRequestDTO pkOnceMoreRequest) {
        pkSocketService.sendSingleMessage(pkOnceMoreRequest.getCurrentUserId(),pkOnceMoreRequest.getOpponentUserId());
    }

    public void pkWithFriend(PkOnceMoreRequestDTO pkOnceMoreRequest) {
        //设置为已经接受邀请
        redisService.setShareUsed(pkOnceMoreRequest.getRandomUserId());
        //保存好友关系
        sysUserServiceConsumer.saveUserFriend(pkOnceMoreRequest.getCurrentUserId(), pkOnceMoreRequest.getOpponentUserId());
        // 匹配房间
        RoomVO room = fightService.matchFriends(pkOnceMoreRequest.getCurrentUserId(), pkOnceMoreRequest.getOpponentUserId());
        // 返回信息
        sendPkWithFriendMessage(getUsersInfo(pkOnceMoreRequest, room), pkOnceMoreRequest);
    }

    private Map<Integer, GameUserMatchVO> getUsersInfo(PkOnceMoreRequestDTO pkOnceMoreRequest, RoomVO room) {
        if (null != pkOnceMoreRequest && null != pkOnceMoreRequest.getCurrentUserId() && null != pkOnceMoreRequest.getOpponentUserId()) {
            Map<Integer, GameUserMatchVO> map = new HashMap<>(2);
            map.put(pkOnceMoreRequest.getCurrentUserId(),
                    fightService.getGameUserMatchVO(sysUserServiceConsumer.getSysUserByUserId(pkOnceMoreRequest.getCurrentUserId()),
                            room));
            map.put(pkOnceMoreRequest.getOpponentUserId(),
                    fightService.getGameUserMatchVO(sysUserServiceConsumer.getSysUserByUserId(pkOnceMoreRequest.getOpponentUserId()),
                            room));
            return map;
        }
        return null;
    }

    private void sendPkWithFriendMessage(Map<Integer, GameUserMatchVO> gameUserMatchVOS, PkOnceMoreRequestDTO pkOnceMoreRequest) {
        if (isUserInfoExist(gameUserMatchVOS)) {
            setResponseType(gameUserMatchVOS);
            pkSocketService.sendSingleMessage(ResponseData.success(gameUserMatchVOS.get(pkOnceMoreRequest.getOpponentUserId())),
                    pkOnceMoreRequest.getCurrentUserId());
            pkSocketService.sendSingleMessage(ResponseData.success(gameUserMatchVOS.get(pkOnceMoreRequest.getCurrentUserId())),
                    pkOnceMoreRequest.getOpponentUserId());
            ShareCyclicSendMessageMap.shareCyclicSendMessageMap.put(pkOnceMoreRequest.getCurrentUserId(),
                    ShareCyclicSendEnum.SHARE_CYCLIC_SEND_YES.getCode());
           /* this.sendCurrentUserIdMessageCyclic(gameUserMatchVOS.get(pkOnceMoreRequest.getCurrentUserId()),
                    pkOnceMoreRequest.getOpponentUserId());*/
        } else {
            LogUtil.info("没能获取到用户信息！");
        }
    }

   /* private void sendCurrentUserIdMessageCyclic(GameUserMatchVO gameUserMatchVO,Integer currentUserId){
        Integer times = 0;
        while (true) {
            if (null != ShareCyclicSendMessageMap.shareCyclicSendMessageMap.get(currentUserId)
                    && ShareCyclicSendEnum.SHARE_CYCLIC_SEND_YES.getCode().equals(ShareCyclicSendMessageMap.shareCyclicSendMessageMap.get(currentUserId))
                    && times<Integer.valueOf("10")) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pkSocketService.sendSingleMessage(gameUserMatchVO, currentUserId);
                times = times +1;
            } else {
                break;
            }
        }
    }*/

    private boolean isUserInfoExist(Map<Integer, GameUserMatchVO> gameUserMatchVOS) {
        boolean b = true;
        if (null == gameUserMatchVOS || gameUserMatchVOS.size() < PkCacheMap.PK_PLAYERS_SIZE) {
            b = false;
        } else {
            for (Map.Entry<Integer,GameUserMatchVO> entry : gameUserMatchVOS.entrySet()) {
                if (null == entry.getValue()) {
                    b = false;
                }
            }
        }
        return b;
    }

    private void setResponseType(Map<Integer, GameUserMatchVO> gameUserMatchVOS){
        gameUserMatchVOS.forEach((id,gameUserMatch)->{
            gameUserMatch.setType(SocketResponseTypeEnum.PK_WITH_FRIEND_RESPONSE.getCode());
        });
    }
}
