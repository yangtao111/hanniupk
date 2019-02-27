package info.hanniu.hanniupk.biz.file.modular.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.biz.file.api.entity.Fileinfo;

import java.util.List;

/**
 * <p>
 * 文件信息表 Mapper 接口
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-27
 */
public interface FileinfoMapper extends BaseMapper<Fileinfo> {

    /**
     * 获取fileinfo列表
     *
     * @author fengshuonan
     * @Date 2018/7/27 下午4:19
     */
    List<Fileinfo> getFileInfoList(Page page, Fileinfo fileinfo);

}
