package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.FeightTemplate;
import co.lq.modules.shop.web.param.FeightTemplateQueryParam;
import co.lq.modules.shop.web.vo.FeightTemplateQueryVo;

/**
 * <p>
 * 运费模版 服务类
 * </p>
 *
 * @author billy
 * @since 2020-05-03
 */
public interface ApiFeightTemplateService extends BaseService<FeightTemplate> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    FeightTemplateQueryVo getFeightTemplateById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param feightTemplateQueryParam
     * @return
     */
    Paging<FeightTemplateQueryVo> getFeightTemplatePageList(FeightTemplateQueryParam feightTemplateQueryParam)
            throws Exception;

}
