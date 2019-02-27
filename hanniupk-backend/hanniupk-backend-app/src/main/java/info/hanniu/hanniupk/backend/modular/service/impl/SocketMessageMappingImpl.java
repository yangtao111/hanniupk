package info.hanniu.hanniupk.backend.modular.service.impl;

import com.alibaba.fastjson.JSON;
import info.hanniu.hanniupk.backend.core.enums.SocketRequestTypeEnum;
import info.hanniu.hanniupk.backend.modular.dto.PKRequestDTO;
import info.hanniu.hanniupk.backend.modular.dto.PkOnceMoreRequestDTO;
import info.hanniu.hanniupk.backend.modular.dto.SocketRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: SocketMessageMappingImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/2 20:52
 * @Version: 1.0
 */
@Service
public class SocketMessageMappingImpl {
    private static Logger logger = LoggerFactory.getLogger(SocketMessageMappingImpl.class);

    @Autowired
    private AsyncServiceImpl asyncService;
    @Autowired
    private PkSocketServiceImpl pkSocketService;
    @Autowired
    private InviteFriendServiceImpl inviteFriendService;

    public void  run(String message){
        logger.info("------>messageSocket.onMessage;来自客户端的消息:<------" + message);
        SocketRequestDTO socketRequestDTO = JSON.parseObject(message,SocketRequestDTO.class);
        if (isPkRequest(socketRequestDTO.getType())) {
            onPkMessage(socketRequestDTO.getPkRequest());
        } else if (isOnceMoreRequest(socketRequestDTO.getType())) {
            onInviteFriendMessage(socketRequestDTO.getPkOnceMoreRequest());
        } else if (isPkWithFriend(socketRequestDTO.getType())) {
            onPkWithFriendMessage(socketRequestDTO.getPkOnceMoreRequest());
        }
    }

    /**
     * 是否为pk的请求
     * @param type
     * @return
     */
    private boolean isPkRequest(Integer type){
        return type.equals(SocketRequestTypeEnum.PK_REQUEST.getCode());
    }

    /**
     * 是否为再来一局的请求
     * @param type
     * @return
     */
    private boolean isOnceMoreRequest(Integer type){
        return type.equals(SocketRequestTypeEnum.ONCE_MORE_REQUEST.getCode());
    }

    /**
     * 是否为邀请好友对战的请求
     * @param type
     * @return
     */
    private boolean isPkWithFriend(Integer type){
        return type.equals(SocketRequestTypeEnum.PK_WITH_FRIEND_REQUEST.getCode());
    }
    private void onPkMessage(PKRequestDTO pkRequest){
        asyncService.getDeferredResult(this.pkSocketService, pkRequest);
    }
    private void onInviteFriendMessage(PkOnceMoreRequestDTO pkOnceMoreRequest){
        inviteFriendService.inviteFriend(pkOnceMoreRequest);
    }
    private void onPkWithFriendMessage(PkOnceMoreRequestDTO pkOnceMoreRequest){
        inviteFriendService.pkWithFriend(pkOnceMoreRequest);
    }
}
