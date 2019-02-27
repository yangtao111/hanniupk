package info.hanniu.hanniupk.biz.log.core.listener;

import com.alibaba.fastjson.JSONObject;
import info.hanniu.hanniupk.biz.log.api.entity.CommonLog;
import info.hanniu.hanniupk.biz.log.api.entity.TraceLog;
import info.hanniu.hanniupk.biz.log.modular.service.CommonLogService;
import info.hanniu.hanniupk.biz.log.modular.service.TraceLogService;
import info.hanniu.hanniupk.kernel.logger.constants.KafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息日志监听器
 *
 * @author fengshuonan
 * @date 2018-07-31-下午1:58
 */
@Component
@Slf4j
public class LogMessageListener {

    @Autowired
    private CommonLogService commonLogService;

    @Autowired
    private TraceLogService traceLogService;

    /**
     * 监听消息队列中的普通日志
     *
     * @author fengshuonan
     * @Date 2018/7/31 下午4:45
     */
    @KafkaListener(id = "commonLogList", topics = KafkaConstants.LOG_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void commonLogList(List<JSONObject> list) {
        long startTime = System.currentTimeMillis();
        log.info("获取到一批commonLog数据，数量为：" + list.size());

        //转化为对应的实体
        ArrayList<CommonLog> commonLogs = new ArrayList<>(list.size());
        for (JSONObject jsonObject : list) {
            commonLogs.add(jsonObject.toJavaObject(CommonLog.class));
        }

        //插入到数据库
        commonLogService.insertBatch(commonLogs, commonLogs.size());

        log.info("获取到一批commonLog数据，处理完成！用时：" + (System.currentTimeMillis() - startTime));
    }

    /**
     * 监听消息队列中的调用链日志
     *
     * @author fengshuonan
     * @Date 2018/7/31 下午4:46
     */
    @KafkaListener(id = "traceLogList", topics = KafkaConstants.TRACE_LOG_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void traceLogList(List<JSONObject> list) {
        long startTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("获取到一批traceLog数据，数量为：" + list.size());
        }

        //转化为对应的实体
        ArrayList<TraceLog> traceLogs = new ArrayList<>(list.size());
        for (JSONObject jsonObject : list) {
            traceLogs.add(jsonObject.toJavaObject(TraceLog.class));
        }

        //插入到数据库
        traceLogService.insertBatch(traceLogs, traceLogs.size());

        if (log.isDebugEnabled()) {
            log.debug("获取到一批traceLog数据，处理完成！用时：" + (System.currentTimeMillis() - startTime));
        }
    }

}
