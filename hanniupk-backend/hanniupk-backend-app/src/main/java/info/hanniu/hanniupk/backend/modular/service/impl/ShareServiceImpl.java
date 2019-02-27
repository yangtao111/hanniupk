package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.core.enums.BackendResponseStatusEnum;
import info.hanniu.hanniupk.backend.core.enums.ShareStatusEnum;
import info.hanniu.hanniupk.backend.modular.service.IShareService;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.model.enums.ResponseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: ShareServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/14 14:41
 * @Version: 1.0
 */
@Service
public class ShareServiceImpl implements IShareService {

    @Autowired
    private RedisServiceImpl redisService;

    @Override
    public void shareToFriend(String userId) {
        redisService.setShareStatus(userId);
    }

    @Override
    public void giveUpShare(String userId) {
        redisService.giveUpShare(userId);
    }

    @Override
    public ResponseData checkShareStatus(String userId) {
        return returnShareStatusCode(redisService.getShareStatus(userId));
    }
    private ResponseData returnShareStatusCode(Integer shareStatus){
        if (ShareStatusEnum.SHARE_USED.getCode().equals(shareStatus)) {
            return ResponseData.success(BackendResponseStatusEnum.SHARE_USED_SUCCESS.getCode(),
                    ShareStatusEnum.SHARE_USED.getMessage(),null);
        } else if (ShareStatusEnum.SHARE_GIVE_UP.getCode().equals(shareStatus)) {
            return ResponseData.success(BackendResponseStatusEnum.SHARE_GIVE_UP_SUCCESS.getCode(),
                    ShareStatusEnum.SHARE_GIVE_UP.getMessage(),null);
        } else if (ShareStatusEnum.SHARE_TIME_OUT.getCode().equals(shareStatus)) {
            return ResponseData.success(BackendResponseStatusEnum.SHARE_TIME_OUT_SUCCESS.getCode(),
                    ShareStatusEnum.SHARE_TIME_OUT.getMessage(),null);
        } else if(ShareStatusEnum.SHARE_OUT_LINE.getCode().equals(shareStatus)){
            return ResponseData.success(BackendResponseStatusEnum.SHARE_OUT_LINE.getCode(),
                    ShareStatusEnum.SHARE_OUT_LINE.getMessage(),null);
        }else {
            return ResponseData.success(ResponseStatusEnum.DEFAULT_SUCCESS.getCode(),
                    ShareStatusEnum.SHARE_NORMAL.getMessage(),null);
        }
    }

    @Override
    public void shareOutLine(String userId) {
        Integer status = redisService.getShareStatus(userId);
        //只有正常状态下，可以修改为掉线状态
        if (ShareStatusEnum.SHARE_NORMAL.getCode().equals(status)) {
            redisService.setShareOutLine(userId);
        }
    }

    @Override
    public ResponseData shareOnLine(String userId) {
        Integer status = redisService.getShareStatus(userId);
        if (ShareStatusEnum.SHARE_OUT_LINE.getCode().equals(status)) {
            redisService.setShareOnLine(userId);
            return ResponseData.success(ResponseStatusEnum.DEFAULT_SUCCESS.getCode(),
                    ShareStatusEnum.SHARE_NORMAL.getMessage(),null);
        } else {
            return returnShareStatusCode(status);
        }
    }

}
