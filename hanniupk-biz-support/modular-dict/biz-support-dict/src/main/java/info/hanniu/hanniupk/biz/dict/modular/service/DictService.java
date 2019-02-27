package info.hanniu.hanniupk.biz.dict.modular.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import info.hanniu.hanniupk.biz.dict.api.entity.Dict;
import info.hanniu.hanniupk.biz.dict.api.model.DictInfo;
import info.hanniu.hanniupk.biz.dict.api.model.TreeDictInfo;
import info.hanniu.hanniupk.biz.dict.modular.mapper.DictMapper;
import info.hanniu.hanniupk.core.page.PageFactory;
import info.hanniu.hanniupk.core.treebuild.DefaultTreeBuildFactory;
import info.hanniu.hanniupk.core.util.ToolUtil;
import info.hanniu.hanniupk.kernel.model.enums.StatusEnum;
import info.hanniu.hanniupk.kernel.model.exception.RequestEmptyException;
import info.hanniu.hanniupk.kernel.model.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static info.hanniu.hanniupk.biz.dict.core.exception.DictExceptionEnum.*;

/**
 * <p>
 * 基础字典 服务实现类
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-24
 */
@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    /**
     * 新增字典
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午3:17
     */
    public void addDict(Dict dict) {
        if (ToolUtil.isOneEmpty(dict, dict.getDictCode(), dict.getDictName())) {
            throw new RequestEmptyException();
        }

        dict.setDictId(null);

        if (dict.getParentId() == null) {
            dict.setParentId(-1L);      //默认的根节点
        } else {
            //判断字典的父id是否存在
            Dict parentDict = this.selectById(dict.getParentId());
            if (parentDict == null) {
                throw new ServiceException(PARENT_NOT_EXISTED);
            }
        }

        dict.setStatus(StatusEnum.ENABLE.getCode());
        this.insert(dict);
    }

    /**
     * 修改字典
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午3:35
     */
    public void updateDict(Dict dict) {
        if (ToolUtil.isOneEmpty(dict, dict.getDictId(), dict.getDictCode(), dict.getDictName())) {
            throw new RequestEmptyException();
        }

        //不能修改类型
        dict.setDictTypeCode(null);

        //判断编码是否重复
        Wrapper<Dict> wrapper = new EntityWrapper<Dict>()
                .eq("DICT_CODE", dict.getDictCode());
        int dicts = this.selectCount(wrapper);
        if (dicts > 0) {
            throw new ServiceException(REPEAT_DICT_TYPE);
        }

        Dict oldDict = this.selectById(dict.getDictId());
        if (oldDict == null) {
            throw new ServiceException(NOT_EXISTED);
        }

        ToolUtil.copyProperties(dict, oldDict);
        this.updateById(oldDict);
    }

    /**
     * 删除字典
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午4:53
     */
    public void deleteDict(Long dictId) {
        if (ToolUtil.isEmpty(dictId)) {
            throw new RequestEmptyException();
        }

        this.deleteById(dictId);
    }

    /**
     * 更新字典状态
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午4:53
     */
    public void updateDictStatus(Long dictId, Integer status) {
        if (ToolUtil.isOneEmpty(dictId, status)) {
            throw new RequestEmptyException();
        }

        //检查状态是否合法
        if (StatusEnum.toEnum(status) == null) {
            throw new ServiceException(WRONG_STATUS);
        }

        Dict dict = this.selectById(dictId);
        dict.setStatus(status);
        this.updateById(dict);
    }

    /**
     * 获取字典列表
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午5:18
     */
    public List<DictInfo> getDictList(Page page, DictInfo dictInfo) {
        if (page == null) {
            page = PageFactory.defaultPage();
        }

        if (dictInfo == null) {
            dictInfo = new DictInfo();
        }

        return baseMapper.getDictList(page, dictInfo);
    }

    /**
     * 获取字典列表
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午5:18
     */
    public List<Dict> getDictListByTypeCode(String dictTypeCode) {

        if (ToolUtil.isEmpty(dictTypeCode)) {
            throw new RequestEmptyException("字典type编码为空！");
        }

        return this.selectList(
                new EntityWrapper<Dict>().eq("DICT_TYPE_CODE", dictTypeCode));
    }

    /**
     * 获取树形字典列表
     *
     * @author fengshuonan
     * @Date 2018/7/25 下午5:53
     */
    public List<TreeDictInfo> getTreeDictList(String dictTypeCode) {
        if (ToolUtil.isEmpty(dictTypeCode)) {
            throw new RequestEmptyException();
        }

        //获取对应字典类型的所有字典列表
        Wrapper<Dict> wrapper = new EntityWrapper<Dict>().eq("DICT_TYPE_CODE", dictTypeCode);
        List<Dict> dicts = this.selectList(wrapper);
        if (dicts == null || dicts.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<TreeDictInfo> treeDictInfos = new ArrayList<>();
        for (Dict dict : dicts) {
            TreeDictInfo treeDictInfo = new TreeDictInfo();
            treeDictInfo.setDictId(dict.getDictId());
            treeDictInfo.setDictCode(dict.getDictCode());
            treeDictInfo.setParentId(dict.getParentId());
            treeDictInfo.setDictName(dict.getDictName());
            treeDictInfos.add(treeDictInfo);
        }

        //构建菜单树
        return new DefaultTreeBuildFactory<TreeDictInfo>().doTreeBuild(treeDictInfos);
    }
}
