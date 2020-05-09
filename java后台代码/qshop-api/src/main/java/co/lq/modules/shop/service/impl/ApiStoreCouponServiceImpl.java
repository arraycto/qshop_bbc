package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreCoupon;
import co.lq.modules.shop.mapper.StoreCouponMapper;
import co.lq.modules.shop.service.ApiStoreCouponService;
import co.lq.modules.shop.web.param.StoreCouponQueryParam;
import co.lq.modules.shop.web.vo.StoreCouponQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiStoreCouponServiceImpl extends BaseServiceImpl<StoreCouponMapper, StoreCoupon>
        implements ApiStoreCouponService {

    private final StoreCouponMapper storeCouponMapper;

    @Override
    public StoreCouponQueryVo getStoreCouponById(Serializable id) {
        return storeCouponMapper.getStoreCouponById(id);
    }

    @Override
    public Paging<StoreCouponQueryVo> getStoreCouponPageList(StoreCouponQueryParam storeCouponQueryParam)
            throws Exception {
        Page page = setPageParam(storeCouponQueryParam, OrderItem.desc("create_time"));
        IPage<StoreCouponQueryVo> iPage = storeCouponMapper.getStoreCouponPageList(page, storeCouponQueryParam);
        return new Paging(iPage);
    }

}
