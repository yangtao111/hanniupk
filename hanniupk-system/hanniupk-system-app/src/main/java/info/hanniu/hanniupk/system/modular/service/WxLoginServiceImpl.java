package info.hanniu.hanniupk.system.modular.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import info.hanniu.hanniupk.kernel.model.api.AuthService;
import info.hanniu.hanniupk.kernel.model.dto.SysUserDTO;
import info.hanniu.hanniupk.kernel.model.dto.WxLoginDTO;
import info.hanniu.hanniupk.kernel.model.entity.UserPlayer;
import info.hanniu.hanniupk.kernel.model.util.JsonUtil;
import info.hanniu.hanniupk.kernel.model.util.WxGenderTransUtil;
import info.hanniu.hanniupk.kernel.model.vo.JsCode2sessionVO;
import info.hanniu.hanniupk.kernel.model.vo.LoginResponseVO;
import info.hanniu.hanniupk.kernel.model.vo.PlayerGradeVO;
import info.hanniu.hanniupk.system.modular.consumer.PlayerGradeServiceConsumer;
import info.hanniu.hanniupk.system.modular.consumer.RedisServiceConsumer;
import info.hanniu.hanniupk.system.modular.consumer.UserPlayerServiceConsumer;
import info.hanniu.hanniupk.system.modular.dto.SessionIdValue;
import info.hanniu.hanniupk.system.util.WxUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: WxLoginService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/9/29 15:13
 * @Version: 1.0
 */
@Service
@Transactional
public class WxLoginServiceImpl {
   // private static Logger logger  = LoggerFactory.getLogger(WxLoginServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysUserService sysUserServiceConsumer;
    @Autowired
    private AuthService authServiceConsumer;
    @Autowired
    private UserPlayerServiceConsumer iUserPlayerService;
    @Autowired
    private RedisServiceConsumer iRedisService;
    @Autowired
    private PlayerGradeServiceConsumer playerGradeServiceConsumer;

    public LoginResponseVO getWxLogin(WxLoginDTO wxLogin) {
        String code = wxLogin.getCode();
        Integer userId;
        if(!StringUtils.isEmpty(code)){
            //从微信服务器获取openId和session_key
            JsCode2sessionVO jsCode2sessionVO = WxUtil.jsCode2session(code);
            if (null == jsCode2sessionVO) {
                return null;
            }
            //保存微信用户身份信息到数据库中
            userId = saveSysUser(jsCode2sessionVO.getOpenId(),wxLogin);
        } else {
            userId = wxLogin.getUserId();
        }
        String token = sysUserServiceConsumer.wxLogin(userId);
        return getResponseData(token,userId);
    }

    public LoginResponseVO getResponseData(String token,Integer userId){
        LoginResponseVO loginResponse = new LoginResponseVO();
        SysUserDTO sysUser = sysUserServiceConsumer.getSysUserByUserId(userId);
        if (null != sysUser) {
            BeanUtils.copyProperties(sysUser,loginResponse);
        }
        UserPlayer userPlayer = iUserPlayerService.selectById(userId);
        if (null != userPlayer) {
            BeanUtils.copyProperties(userPlayer,loginResponse);
        }
        if (null != userPlayer) {
            PlayerGradeVO playerGradeVO = playerGradeServiceConsumer.selectByGradeCode(userPlayer.getGradeCode());
            if (null != playerGradeVO) {
                loginResponse.setGradeName(playerGradeVO.getGradeName());
            }
        }
        loginResponse.setToken(token);
        return loginResponse;
    }

    public Integer saveSysUser(String openId,WxLoginDTO wxLogin){
        SysUserDTO sysUserDTO = getSysUserByOpenId(openId);
        Integer userId;
        if (null != sysUserDTO) {
             userId = sysUserServiceConsumer.save(trans2SysUser(openId, sysUserDTO.getUserId(), wxLogin));
        } else {
            userId = sysUserServiceConsumer.save(trans2SysUser(openId,null, wxLogin));
        }
        setInitialUserPlayer(userId);
        return userId;
    }

    /**
     * 根据openId查询sysUser,若没有，返回null。
      * @param openId
     * @return
     */
    public SysUserDTO getSysUserByOpenId(String openId){
        return sysUserServiceConsumer.getSysUserByOpenId(openId);
    }

    /**
     * 设置userPlayer的初始信息，若已存在则不添加。
     * @param userId
     */
    private void setInitialUserPlayer(Integer userId) {
        UserPlayer userPlayer = iUserPlayerService.selectById(userId);
        if (null == userPlayer) {
            iUserPlayerService.saveOrUpdate(getDUserPlayer(userId));
        }
    }

    /**
     * 生产userPlayer的初始数据
     * @return
     */
    private UserPlayer getDUserPlayer(Integer userId) {
        UserPlayer userPlayer = new UserPlayer();
        userPlayer.setGradeCode(1);
        userPlayer.setExperience(0);
        userPlayer.setCoin(0);
        userPlayer.setCreateTime(new Date());
        userPlayer.setId(userId);
        return userPlayer;
    }

    private SysUserDTO trans2SysUser(String opendId,Integer userId,WxLoginDTO wxLogin){
        SysUserDTO sysUser = new SysUserDTO();
        sysUser.setOpenId(opendId);
        sysUser.setNickName(wxLogin.getNickName());
        sysUser.setAvatarUrl(wxLogin.getAvatarUrl());
        sysUser.setSex(WxGenderTransUtil.wX2hanNiu(wxLogin.getGender()));
        if (null != userId) {
            sysUser.setUserId(userId);
        }
        return sysUser;
    }

    /**
     * @Method getSessionId
     * @Author tao
     * @Version  1.0
     * @Description 获取小程序和汗牛服务器业务令牌，并存入redis缓存中
     * @param sessionKey
     * @param openId
     * @Return java.lang.String
     * @Exception
     * @Date 2018/9/30 15:12
     */
    public String getSessionId(String sessionKey, String openId) {
        if (null == sessionKey || null == openId) {
            return "";
        }
        String sessionId = WxUtil.createSessionId();
        iRedisService.setSessionId(sessionId,createSessionIdValue(openId,sessionKey));
        return sessionId;
    }
    public String createSessionIdValue(String sessionKey, String openId){
        SessionIdValue sessionIdValue = new SessionIdValue(openId,sessionKey);
        return JSONObject.toJSONString(sessionIdValue);
    }

    /**
     * @Method isSessionIdExisted
     * @Author tao
     * @Version  1.0
     * @Description 验证小程序和汗牛服务器的令牌是否存在
     * @param sessionId
     * @Return boolean
     * @Exception
     * @Date 2018/9/30 15:11
     */
    public boolean isSessionIdExisted(String sessionId){
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(sessionId);
        SessionIdValue sessionIdValue = (SessionIdValue) JsonUtil.jsonString2class((String)opts.get(),SessionIdValue.class) ;
        return null != sessionIdValue && !"".equals(sessionIdValue.getOpenId().trim())?true:false;
    }

}
