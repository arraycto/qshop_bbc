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
import co.lq.modules.shop.entity.FeightTemplate;
import co.lq.modules.shop.mapper.FeightTemplateMapper;
import co.lq.modules.shop.service.ApiFeightTemplateService;
import co.lq.modules.shop.web.param.FeightTemplateQueryParam;
import co.lq.modules.shop.web.vo.FeightTemplateQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 运费模版 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-05-03
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiFeightTemplateServiceImpl extends BaseServiceImpl<FeightTemplateMapper, FeightTemplate>
        implements ApiFeightTemplateService {

    @Autowired
    private FeightTemplateMapper feightTemplateMapper;

    @Override
    public FeightTemplateQueryVo getFeightTemplateById(Serializable id) throws Exception {
        return feightTemplateMapper.getFeightTemplateById(id);
    }

    @Override
    public Paging<FeightTemplateQueryVo> getFeightTemplatePageList(FeightTemplateQueryParam feightTemplateQueryParam)
            throws Exception {
        Page page = setPageParam(feightTemplateQueryParam, OrderItem.desc("create_time"));
        IPage<FeightTemplateQueryVo> iPage = feightTemplateMapper.getFeightTemplatePageList(page,
                feightTemplateQueryParam);
        return new Paging(iPage);
    }

}
