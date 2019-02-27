package info.hanniu.hanniupk.backend.modular.service;

import javax.websocket.Session;
import java.util.List;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service
 * @ClassName: IThreadTaskService
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/11 13:30
 * @Version: 1.0
 */
public interface IThreadTaskService<T,V> {
    V run(T t);
}
