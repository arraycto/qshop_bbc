package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.WechatUser;
import co.lq.modules.user.web.param.WechatUserQueryParam;
import co.lq.modules.user.web.vo.WechatUserQueryVo;

/**
 * <p>
 * 微信用户表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
public interface WechatUserService extends BaseService<WechatUser> {

    WechatUser getUserAppInfo(String openid);

    WechatUser getUserInfo(String openid);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    WechatUserQueryVo getWechatUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param wechatUserQueryParam
     * @return
     */
    Paging<WechatUserQueryVo> getWechatUserPageList(WechatUserQueryParam wechatUserQueryParam) throws Exception;

}
