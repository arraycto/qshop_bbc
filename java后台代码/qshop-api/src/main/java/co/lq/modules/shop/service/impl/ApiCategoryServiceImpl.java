package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.Category;
import co.lq.modules.shop.mapper.CategoryMapper;
import co.lq.modules.shop.mapping.CategoryMap;
import co.lq.modules.shop.service.ApiCategoryService;
import co.lq.modules.shop.web.vo.CategoryQueryVo;
import co.lq.utils.CateDTO;
import co.lq.utils.TreeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-22
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiCategoryServiceImpl extends BaseServiceImpl<CategoryMapper, Category> implements ApiCategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryMap    categoryMap;

    @Override
    public CategoryQueryVo getStoreCategoryById(Serializable id) throws Exception {
        return categoryMapper.getStoreCategoryById(id);
    }

    @Override
    public List<CateDTO> getList() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("is_show", 1).orderByAsc("sort");
        List<CateDTO> list = categoryMap.toDto(baseMapper.selectList(wrapper));
        return TreeUtil.list2TreeConverter(list, 0);
    }

    @Override
    public List<String> getAllChilds(int catid) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("is_show", 1).eq("id", catid);

        List<CateDTO> list = categoryMap.toDto(baseMapper.selectList(wrapper));

        System.out.println(TreeUtil.getChildList(list, new CateDTO()));
        return null;
    }
}
