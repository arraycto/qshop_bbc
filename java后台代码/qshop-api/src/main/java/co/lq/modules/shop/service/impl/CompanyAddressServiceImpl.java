package co.lq.modules.shop.service.impl;

import co.lq.modules.shop.entity.CompanyAddress;
import co.lq.modules.shop.mapper.CompanyAddressMapper;
import co.lq.modules.shop.service.CompanyAddressService;
import co.lq.modules.shop.web.param.CompanyAddressQueryParam;
import co.lq.modules.shop.web.vo.CompanyAddressQueryVo;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;


/**
 * <p>
 * 公司收发货地址表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2020-05-05
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyAddressServiceImpl extends BaseServiceImpl<CompanyAddressMapper, CompanyAddress> implements CompanyAddressService {

    @Autowired
    private CompanyAddressMapper companyAddressMapper;

    @Override
    public CompanyAddressQueryVo getCompanyAddressById(Serializable id) throws Exception{
        return companyAddressMapper.getCompanyAddressById(id);
    }

    @Override
    public Paging<CompanyAddressQueryVo> getCompanyAddressPageList(CompanyAddressQueryParam companyAddressQueryParam) throws Exception{
        Page page = setPageParam(companyAddressQueryParam,OrderItem.desc("create_time"));
        IPage<CompanyAddressQueryVo> iPage = companyAddressMapper.getCompanyAddressPageList(page,companyAddressQueryParam);
        return new Paging(iPage);
    }

}
