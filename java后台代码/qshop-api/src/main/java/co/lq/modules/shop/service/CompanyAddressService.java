package co.lq.modules.shop.service;

import co.lq.modules.shop.entity.CompanyAddress;
import co.lq.common.service.BaseService;
import co.lq.modules.shop.web.param.CompanyAddressQueryParam;
import co.lq.modules.shop.web.vo.CompanyAddressQueryVo;
import co.lq.common.web.vo.Paging;

import java.io.Serializable;

/**
 * <p>
 * 公司收发货地址表 服务类
 * </p>
 *
 * @author billy
 * @since 2020-05-05
 */
public interface CompanyAddressService extends BaseService<CompanyAddress> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    CompanyAddressQueryVo getCompanyAddressById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     * @param companyAddressQueryParam
     * @return
     */
    Paging<CompanyAddressQueryVo> getCompanyAddressPageList(CompanyAddressQueryParam companyAddressQueryParam) throws Exception;

}
