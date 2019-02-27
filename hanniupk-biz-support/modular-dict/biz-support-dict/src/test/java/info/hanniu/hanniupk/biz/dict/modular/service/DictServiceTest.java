package info.hanniu.hanniupk.biz.dict.modular.service;

import base.BaseJunit;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import info.hanniu.hanniupk.biz.dict.api.entity.Dict;
import info.hanniu.hanniupk.biz.dict.api.entity.DictType;
import info.hanniu.hanniupk.biz.dict.api.model.DictInfo;
import info.hanniu.hanniupk.biz.dict.api.model.TreeDictInfo;
import info.hanniu.hanniupk.core.page.PageFactory;
import info.hanniu.hanniupk.kernel.model.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static info.hanniu.hanniupk.biz.dict.core.exception.DictExceptionEnum.REPEAT_DICT_TYPE;


public class DictServiceTest extends BaseJunit {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictTypeService dictTypeService;

    @Before
    public void setUp() {

        //删除所有字典
        boolean delete = dictService.delete(new EntityWrapper<>());
        Assert.assertTrue(delete);

        //新增字典类型
        DictType dictType = new DictType();
        dictType.setDictTypeCode("testDictType");
        dictType.setDictTypeName("字典类型aaa");
        dictTypeService.addDictType(dictType);

        //在字典类型下 关联两个具体字典
        Dict dictA = new Dict();
        dictA.setDictCode("testA");
        dictA.setDictName("A类型");
        dictA.setDictTypeCode("testDictType");
        dictService.addDict(dictA);

        Dict dictB = new Dict();
        dictB.setDictCode("testB");
        dictB.setDictName("B类型");
        dictB.setDictTypeCode("testDictType");
        dictService.addDict(dictB);
    }

    @Test
    public void addDict() {
        Dict dictB = new Dict();
        dictB.setDictCode("testC");
        dictB.setDictName("C类型");
        dictB.setDictTypeCode("testDictType");
        dictService.addDict(dictB);

        List<Dict> dicts = dictService.selectList(new EntityWrapper<>());
        Assert.assertEquals(dicts.size(), 3);
    }

    @Test
    public void updateDict() {
        List<Dict> dicts = dictService.selectList(new EntityWrapper<Dict>().orderBy("CREATE_TIME", true));

        //获取到testA
        Dict dict = dicts.get(0);
        dict.setStatus(2);
        dict.setDictCode("BBBB");
        dictService.updateDict(dict);

        //从新查询
        List<Dict> newDicts = dictService.selectList(new EntityWrapper<Dict>().orderBy("CREATE_TIME", true));
        Dict newDict = newDicts.get(0);
        Assert.assertEquals(newDict.getDictCode(), "BBBB");

        //判断编码是否重复
        dict.setDictCode("testA");
        try {
            dictService.updateDict(dict);
        } catch (ServiceException e) {
            Assert.assertEquals(e.getCode(), REPEAT_DICT_TYPE.getCode());
        }
    }

    @Test
    public void deleteDict() {
        List<Dict> dicts = dictService.selectList(new EntityWrapper<Dict>().orderBy("CREATE_TIME", true));
        Dict dict = dicts.get(0);
        dictService.deleteDict(dict.getDictId());

        List<Dict> dictsNew = dictService.selectList(new EntityWrapper<Dict>().orderBy("CREATE_TIME", true));
        Assert.assertEquals(dictsNew.size(), 1);
    }

    @Test
    public void updateDictStatus() {
        List<Dict> dicts = dictService.selectList(new EntityWrapper<Dict>().orderBy("CREATE_TIME", true));
        Dict dict = dicts.get(0);
        dictService.updateDictStatus(dict.getDictId(), 2);

        List<Dict> dictsNew = dictService.selectList(new EntityWrapper<Dict>().orderBy("CREATE_TIME", true));
        Dict dictNew = dictsNew.get(0);
        Assert.assertEquals(dictNew.getStatus(), Integer.valueOf(2));
    }

    @Test
    public void getDictList() {
        List<DictInfo> dictList = dictService.getDictList(PageFactory.defaultPage(), new DictInfo());
        Assert.assertEquals(dictList.size(), 2);
    }

    @Test
    public void getDictListByTypeCode() {
        List<Dict> testDictType = dictService.getDictListByTypeCode("testDictTypeXXX");
        Assert.assertTrue(testDictType.isEmpty());

        List<Dict> testDictTypes = dictService.getDictListByTypeCode("testDictType");
        Assert.assertTrue(!testDictTypes.isEmpty());
    }

    @Test
    public void getTreeDictList() {
        List<TreeDictInfo> testDictType = dictService.getTreeDictList("testDictType");
        Assert.assertTrue(!testDictType.isEmpty());
    }
}