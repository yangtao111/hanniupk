package info.hanniu.hanniupk.biz.log.modular.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.biz.log.api.entity.CommonLog;
import info.hanniu.hanniupk.biz.log.modular.model.CommonLogCondition;
import info.hanniu.hanniupk.biz.log.modular.model.CommonLogParams;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-30
 */
public interface CommonLogMapper extends BaseMapper<CommonLog> {

    /**
     * 获取常规日志总数量
     *
     * @author fengshuonan
     * @Date 2018/7/31 下午5:51
     */
    Long getCommonLogCount();

    /**
     * 获取常规日志列表
     *
     * @author fengshuonan
     * @Date 2018/7/31 下午5:51
     */
    List<CommonLog> getCommonLogList(CommonLogParams commonLogParams);

    /**
     * 获取常规日志列表(条件查询)
     *
     * @author fengshuonan
     * @Date 2018/7/31 下午5:51
     */
    List<CommonLog> getCommonLogListByCondition(CommonLogCondition commonLogCondition);

    /**
     * 获取常规日志列表(条件查询和分页)
     *
     * @author fengshuonan
     * @Date 2018/7/31 下午5:51
     */
    List<CommonLog> getCommonLogListPageByCondition(Page<CommonLog> page, CommonLogCondition commonLogCondition);

}
