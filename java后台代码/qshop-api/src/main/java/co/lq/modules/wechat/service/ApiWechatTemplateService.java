package co.lq.modules.wechat.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.wechat.entity.WechatTemplate;
import co.lq.modules.wechat.web.param.WechatTemplateQueryParam;
import co.lq.modules.wechat.web.vo.WechatTemplateQueryVo;

/**
 * <p>
 * 微信模板 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-10
 */
public interface ApiWechatTemplateService extends BaseService<WechatTemplate> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    WechatTemplateQueryVo getWechatTemplateById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param wechatTemplateQueryParam
     * @return
     */
    Paging<WechatTemplateQueryVo> getWechatTemplatePageList(WechatTemplateQueryParam wechatTemplateQueryParam)
            throws Exception;

}
