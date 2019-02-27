package info.hanniu.hanniupk.biz.log.modular.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import info.hanniu.hanniupk.biz.log.api.entity.TraceLog;
import info.hanniu.hanniupk.biz.log.modular.factory.TraceLogFactory;
import info.hanniu.hanniupk.biz.log.modular.mapper.TraceLogMapper;
import info.hanniu.hanniupk.biz.log.modular.model.TraceLogCondition;
import info.hanniu.hanniupk.biz.log.modular.model.TraceLogParams;
import info.hanniu.hanniupk.kernel.model.page.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-30
 */
@Service
public class TraceLogService extends ServiceImpl<TraceLogMapper, TraceLog> {

    /**
     * 获取调用链日志列表（没有查询条件的）
     *
     * @author fengshuonan
     * @Date 2018/8/1 下午4:10
     */
    public PageResult<TraceLog> getTraceLogList(TraceLogParams traceLogParams) {
        Long traceLogCount = this.baseMapper.getTraceLogCount();

        if (traceLogCount == null) {
            traceLogCount = 0L;
        }

        if (traceLogParams.getGtValue() == null) {
            traceLogParams.setGtValue(traceLogCount);
        }

        List<TraceLog> commonLogList = this.baseMapper.getTraceLogList(traceLogParams);
        return TraceLogFactory.getResponse(commonLogList, traceLogCount, traceLogParams);
    }

    /**
     * 获取调用链日志列表（带查询条件的）
     *
     * @author fengshuonan
     * @Date 2018/8/1 下午4:10
     */
    public PageResult<TraceLog> getTraceLogListByCondition(TraceLogCondition traceLogCondition) {
        List<TraceLog> commonLogList = this.baseMapper.getTraceLogListByCondition(traceLogCondition);
        return TraceLogFactory.getResponseCondition(commonLogList, traceLogCondition);
    }
}
