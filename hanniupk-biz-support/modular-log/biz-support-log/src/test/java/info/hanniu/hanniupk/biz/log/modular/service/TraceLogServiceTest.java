package info.hanniu.hanniupk.biz.log.modular.service;

import base.BaseJunit;
import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.biz.log.api.entity.TraceLog;
import info.hanniu.hanniupk.biz.log.modular.mapper.TraceLogMapper;
import info.hanniu.hanniupk.biz.log.modular.model.TraceLogCondition;
import info.hanniu.hanniupk.biz.log.modular.model.TraceLogParams;
import info.hanniu.hanniupk.kernel.model.page.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 日志测试类
 *
 * @author fengshuonan
 * @date 2018-07-25-下午12:24
 */
public class TraceLogServiceTest extends BaseJunit {

    @Autowired
    private TraceLogMapper traceLogMapper;

    @Test
    public void getCommonLogCount() {
        Long traceLogCount = traceLogMapper.getTraceLogCount();
        System.err.println(traceLogCount);
    }

    @Test
    public void getCommonLogList() {

        long current = System.currentTimeMillis();

        Long traceLogCount = traceLogMapper.getTraceLogCount();
        TraceLogParams traceLogParams = new TraceLogParams();

        traceLogParams.setGtValue(traceLogCount);
        traceLogParams.setPageSize(100);
        traceLogParams.setPageNo(20000);

        List<TraceLog> traceLogList = traceLogMapper.getTraceLogList(traceLogParams);

        PageResult<TraceLog> pageResult = new PageResult<>();
        pageResult.setRows(traceLogList);
        pageResult.setTotalRows(traceLogCount);
        pageResult.setPage(20000);
        pageResult.setPageSize(100);

        long a = traceLogCount % 100 == 0 ? 0 : 1;
        pageResult.setTotalPage((int) (a + traceLogCount / 100));

        //System.err.println(pageResult);
        System.out.println(System.currentTimeMillis() - current);
    }

    @Test
    public void getCommonLogListByCondition() {

        long current = System.currentTimeMillis();

        TraceLogCondition traceLogCondition = new TraceLogCondition();
        traceLogCondition.setRpcPhase("L1");
        traceLogCondition.setPageSize(100);
        traceLogCondition.setLimitOffset(1000000L);

        List<TraceLog> traceLog = traceLogMapper.getTraceLogListByCondition(traceLogCondition);

        PageResult<TraceLog> pageResult = new PageResult<>();
        pageResult.setRows(traceLog);
        pageResult.setPage(1);
        pageResult.setPageSize(100);

        //pageResult.setTotalRows(commonLogCount);
        //long a = commonLogCount % 100 == 0 ? 0 : 1;
        //pageResult.setTotalPage((int) (a + commonLogCount / 100));

        //System.err.println(pageResult);
        System.err.println(System.currentTimeMillis() - current);

    }

    @Test
    public void getCommonLogListPageByCondition() {

        long current = System.currentTimeMillis();

        TraceLogCondition traceLogCondition = new TraceLogCondition();
        traceLogCondition.setRpcPhase("L1");

        List<TraceLog> commonLogList = traceLogMapper.getTraceLogListPageByCondition(new Page<>(100000, 100),
                traceLogCondition);

        PageResult<TraceLog> pageResult = new PageResult<>();
        pageResult.setRows(commonLogList);
        pageResult.setPage(100000);
        pageResult.setPageSize(100);

        //pageResult.setTotalRows(commonLogCount);
        //long a = commonLogCount % 100 == 0 ? 0 : 1;
        //pageResult.setTotalPage((int) (a + commonLogCount / 100));

        //System.err.println(pageResult);
        System.err.println(System.currentTimeMillis() - current);

    }
}
