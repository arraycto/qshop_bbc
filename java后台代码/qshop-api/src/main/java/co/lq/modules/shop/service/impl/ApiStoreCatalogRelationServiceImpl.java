package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCatalogRelation;
import co.lq.modules.shop.mapper.StoreCatalogRelationMapper;
import co.lq.modules.shop.service.ApiStoreCatalogRelationService;
import co.lq.modules.shop.web.param.StoreCatalogRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreCatalogRelationQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 店铺类目关联表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-04-23
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCatalogRelationServiceImpl extends
        BaseServiceImpl<StoreCatalogRelationMapper, StoreCatalogRelation> implements ApiStoreCatalogRelationService {

    @Autowired
    private StoreCatalogRelationMapper storeCatalogRelationMapper;

    @Override
    public StoreCatalogRelationQueryVo getStoreCatalogRelationById(Serializable id) throws Exception {
        return storeCatalogRelationMapper.getStoreCatalogRelationById(id);
    }

    @Override
    public Paging<StoreCatalogRelationQueryVo> getStoreCatalogRelationPageList(StoreCatalogRelationQueryParam storeCatalogRelationQueryParam)
            throws Exception {
        Page page = setPageParam(storeCatalogRelationQueryParam, OrderItem.desc("create_time"));
        IPage<StoreCatalogRelationQueryVo> iPage = storeCatalogRelationMapper.getStoreCatalogRelationPageList(page,
                storeCatalogRelationQueryParam);
        return new Paging(iPage);
    }

}
