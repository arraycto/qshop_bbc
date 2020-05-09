package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.CmsSubject;
import co.lq.modules.shop.web.param.CmsSubjectQueryParam;
import co.lq.modules.shop.web.vo.CmsSubjectQueryVo;

/**
 * <p>
 * 专题表 服务类
 * </p>
 *
 * @author billy
 * @since 2020-04-19
 */
public interface ApiCmsSubjectService extends BaseService<CmsSubject> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CmsSubjectQueryVo getCmsSubjectById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param cmsSubjectQueryParam
     * @return
     */
    Paging<CmsSubjectQueryVo> getCmsSubjectPageList(CmsSubjectQueryParam cmsSubjectQueryParam) throws Exception;

    int incReadCount(Long id);

}
