package co.lq.modules.manage.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.manage.entity.Express;
import co.lq.modules.manage.web.param.ExpressQueryParam;
import co.lq.modules.manage.web.vo.ExpressQueryVo;

/**
 * <p>
 * 快递公司表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-12-13
 */
public interface ExpressService extends BaseService<Express> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    ExpressQueryVo getExpressById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param expressQueryParam
     * @return
     */
    Paging<ExpressQueryVo> getExpressPageList(ExpressQueryParam expressQueryParam) throws Exception;

}
