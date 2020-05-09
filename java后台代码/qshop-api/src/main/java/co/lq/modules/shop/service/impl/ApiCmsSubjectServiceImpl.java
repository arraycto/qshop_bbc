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
import co.lq.modules.shop.entity.CmsSubject;
import co.lq.modules.shop.mapper.CmsSubjectMapper;
import co.lq.modules.shop.service.ApiCmsSubjectService;
import co.lq.modules.shop.web.param.CmsSubjectQueryParam;
import co.lq.modules.shop.web.vo.CmsSubjectQueryVo;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 专题表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-04-19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiCmsSubjectServiceImpl extends BaseServiceImpl<CmsSubjectMapper, CmsSubject>
        implements ApiCmsSubjectService {

    @Autowired
    private CmsSubjectMapper cmsSubjectMapper;

    @Override
    public CmsSubjectQueryVo getCmsSubjectById(Serializable id) throws Exception {
        return cmsSubjectMapper.getCmsSubjectById(id);
    }

    @Override
    public Paging<CmsSubjectQueryVo> getCmsSubjectPageList(CmsSubjectQueryParam cmsSubjectQueryParam) throws Exception {
        Page page = setPageParam(cmsSubjectQueryParam, OrderItem.desc("add_time"));
        IPage<CmsSubjectQueryVo> iPage = cmsSubjectMapper.getCmsSubjectPageList(page, cmsSubjectQueryParam);
        return new Paging(iPage);
    }

    @Override
    public int incReadCount(Long id) {
        return cmsSubjectMapper.incReadCount(id);
    }

}
