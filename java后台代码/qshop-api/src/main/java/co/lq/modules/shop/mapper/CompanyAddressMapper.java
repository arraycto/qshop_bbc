package co.lq.modules.shop.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import co.lq.modules.shop.entity.CompanyAddress;
import co.lq.modules.shop.web.param.CompanyAddressQueryParam;
import co.lq.modules.shop.web.vo.CompanyAddressQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <p>
 * 公司收发货地址表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-05-05
 */
@Repository
public interface CompanyAddressMapper extends BaseMapper<CompanyAddress> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    CompanyAddressQueryVo getCompanyAddressById(Serializable id);

    /**
     * 获取分页对象
     * @param page
     * @param companyAddressQueryParam
     * @return
     */
    IPage<CompanyAddressQueryVo> getCompanyAddressPageList(@Param("page") Page page, @Param("param") CompanyAddressQueryParam companyAddressQueryParam);

}
