package info.hanniu.hanniupk.biz.dict.modular.provider;

import com.baomidou.mybatisplus.plugins.Page;
import info.hanniu.hanniupk.biz.dict.api.DictTypeApi;
import info.hanniu.hanniupk.biz.dict.api.entity.DictType;
import info.hanniu.hanniupk.biz.dict.api.model.DictTypeInfo;
import info.hanniu.hanniupk.biz.dict.modular.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-24
 */
@RestController
public class DictTypeServiceProvider implements DictTypeApi {

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 获取字典类型列表
     *
     * @param dictTypeInfo 查询条件封装
     * @author fengshuonan
     * @Date 2018/7/25 下午12:36
     */
    public List<DictTypeInfo> getDictTypeList(@RequestBody DictTypeInfo dictTypeInfo, @RequestParam("pageNo") Integer pageNo,
                                              @RequestParam("pageSize") Integer pageSize) {
        return dictTypeService.getDictTypeList(new Page(pageNo, pageSize), dictTypeInfo);
    }

    /**
     * 添加字典类型
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午1:43
     */
    public void addDictType(@RequestBody DictType dictType) {
        dictTypeService.addDictType(dictType);
    }

    /**
     * 修改字典类型
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午2:28
     */
    public void updateDictType(@RequestBody DictType dictType) {
        dictTypeService.updateDictType(dictType);
    }

    /**
     * 删除字典类型
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午2:43
     */
    public void deleteDictType(@RequestParam("dictTypeId") Long dictTypeId) {
        dictTypeService.deleteDictType(dictTypeId);
    }

    /**
     * 修改字典状态
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午2:43
     */
    public void updateDictTypeStatus(@RequestParam("dictTypeId") Long dictTypeId, @RequestParam("status") Integer status) {
        dictTypeService.updateDictTypeStatus(dictTypeId, status);
    }
}
