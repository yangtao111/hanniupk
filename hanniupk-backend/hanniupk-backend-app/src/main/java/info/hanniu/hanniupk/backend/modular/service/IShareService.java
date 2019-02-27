package info.hanniu.hanniupk.backend.modular.service;

import info.hanniu.hanniupk.core.reqres.response.ResponseData;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: IShareService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/14 14:39
 * @Version: 1.0
 */
public interface IShareService {

    void shareToFriend(String userId);

    void giveUpShare(String userId);

    ResponseData checkShareStatus(String userId);

    void shareOutLine(String userId);

    ResponseData shareOnLine(String userId);
}
