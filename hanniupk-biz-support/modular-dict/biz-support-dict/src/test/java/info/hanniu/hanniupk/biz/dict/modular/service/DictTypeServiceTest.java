package info.hanniu.hanniupk.biz.dict.modular.service;

import base.BaseJunit;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import info.hanniu.hanniupk.biz.dict.api.entity.DictType;
import info.hanniu.hanniupk.biz.dict.api.model.DictTypeInfo;
import info.hanniu.hanniupk.core.page.PageFactory;
import info.hanniu.hanniupk.kernel.model.enums.StatusEnum;
import info.hanniu.hanniupk.kernel.model.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static info.hanniu.hanniupk.biz.dict.core.exception.DictExceptionEnum.REPEAT_DICT_TYPE;

/**
 * 字典测试类
 *
 * @author fengshuonan
 * @date 2018-07-25-下午12:24
 */
public class DictTypeServiceTest extends BaseJunit {

    @Autowired
    private DictTypeService dictTypeService;

    @Before
    public void setUp() {
        DictType dictType = new DictType();
        dictType.setDictTypeCode("prepareDict");
        dictType.setDictTypeName("预留字典");
        dictTypeService.addDictType(dictType);
    }

    @Test
    public void deleteDictType() {

        List<DictType> dictTypes = dictTypeService.selectList(new EntityWrapper<>());

        //删除指定字典类型
        dictTypeService.deleteDictType(dictTypes.get(0).getDictTypeId());
    }

    @Test
    public void addDictType() {

        boolean delete = dictTypeService.delete(new EntityWrapper<>());
        Assert.assertTrue(delete);

        //新增字典 code为testDict
        DictType dictType = new DictType();
        dictType.setDictTypeCode("testDictXX");
        dictType.setDictTypeName("测试的字典类型名称");
        dictTypeService.addDictType(dictType);

        //再次新增，提示字典重复
        DictType dictTypeRepeat = new DictType();
        dictTypeRepeat.setDictTypeCode("testDictXX");
        dictTypeRepeat.setDictTypeName("测试的字典类型名称");

        try {
            dictTypeService.addDictType(dictType);
        } catch (ServiceException e) {
            Assert.assertEquals(e.getCode(), REPEAT_DICT_TYPE.getCode());
        }

        //查询字典数量和名称
        List<DictType> dictTypes = dictTypeService.selectList(new EntityWrapper<>());
        Assert.assertEquals(dictTypes.size(), 1);
        Assert.assertEquals(dictTypes.get(0).getDictTypeName(), "测试的字典类型名称");
    }

    @Test
    public void updateDictType() {
        Wrapper<DictType> wrapper = new EntityWrapper<DictType>().eq("DICT_TYPE_NAME", "预留字典");
        DictType dictType = dictTypeService.selectOne(wrapper);
        dictType.setDictTypeName("更改后的测试的字典类型名称");
        this.dictTypeService.updateDictType(dictType);
    }

    @Test
    public void getDictTypeList() {
        DictTypeInfo dictTypeInfo = new DictTypeInfo();
        List<DictTypeInfo> dictTypeList =
                this.dictTypeService.getDictTypeList(PageFactory.defaultPage(), dictTypeInfo);
        Assert.assertEquals(1, dictTypeList.size());
    }

    @Test
    public void updateDictTypeStatus() {
        List<DictType> dictTypes = dictTypeService.selectList(new EntityWrapper<>());
        this.dictTypeService.updateDictTypeStatus(dictTypes.get(0).getDictTypeId(), StatusEnum.ENABLE.getCode());
    }

}
