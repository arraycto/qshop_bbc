package co.lq.modules.shop.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.modules.shop.entity.Catalog;
import co.lq.modules.shop.mapper.CatalogMapper;
import co.lq.modules.shop.mapping.CatalogMap;
import co.lq.modules.shop.service.ApiCatalogService;
import co.lq.modules.shop.web.vo.CatalogQueryVo;
import co.lq.utils.CatalogDTO;
import co.lq.utils.TreeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 平台类目服务实现
 *
 * @author songbin
 * @since 2020年3月11日 下午5:45:46
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiCatalogServiceImpl extends BaseServiceImpl<CatalogMapper, Catalog> implements ApiCatalogService {

    private final CatalogMapper categoryMapper;

    private final CatalogMap    catalogMap;

    @Override
    public CatalogQueryVo getCatalogById(Serializable id) throws Exception {
        return categoryMapper.getCatalogById(id);
    }

    @Override
    public List<CatalogDTO> getList() {
        QueryWrapper<Catalog> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0).orderByAsc("sort_order");
        List<CatalogDTO> list = catalogMap.toDto(baseMapper.selectList(wrapper));
        return TreeUtil.listCatalogTreeConverter(list, 0);
    }

    @Override
    public List<String> getAllChilds(int catid) {
        QueryWrapper<Catalog> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0).eq("id", catid);

        List<CatalogDTO> list = catalogMap.toDto(baseMapper.selectList(wrapper));

        System.out.println(TreeUtil.getChildListCatalog(list, new CatalogDTO()));
        return null;
    }

    @Override
    public List<CatalogDTO> getFirstList() {
        QueryWrapper<Catalog> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", 0).eq("deleted", 0).orderByAsc("sort_order");
        List<CatalogDTO> list = catalogMap.toDto(baseMapper.selectList(wrapper));
        return list;
    }
}
