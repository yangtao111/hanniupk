package info.hanniu.hanniupk.biz.dict.modular.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.biz.dict.api.entity.Dict;
import info.hanniu.hanniupk.biz.dict.api.model.DictInfo;

import java.util.List;

/**
 * <p>
 * 基础字典 Mapper 接口
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-24
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 获取字典列表
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午5:21
     */
    List<DictInfo> getDictList(Page page, DictInfo dictInfo);

}
