package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.HomeAdvertise;
import co.lq.modules.shop.mapper.HomeAdvertiseMapper;
import co.lq.modules.shop.service.ApiHomeAdvertiseService;
import co.lq.modules.shop.web.param.HomeAdvertiseQueryParam;
import co.lq.modules.shop.web.vo.HomeAdvertiseQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 广告服务
 *
 * @author songbin
 * @since 2020年3月13日 下午10:41:02
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiHomeAdvertiseServiceImpl extends BaseServiceImpl<HomeAdvertiseMapper, HomeAdvertise>
        implements ApiHomeAdvertiseService {

    private final HomeAdvertiseMapper homeAdvertiseMapper;

    @Override
    public HomeAdvertiseQueryVo getAdvertiseById(Serializable id) throws Exception {
        return homeAdvertiseMapper.getAdvertiseById(id);
    }

    @Override
    public Paging<HomeAdvertiseQueryVo> getAdvertisePageList(HomeAdvertiseQueryParam homeAdvertiseQueryParam) {
        Page page = setPageParam(homeAdvertiseQueryParam, OrderItem.desc("add_time"));
        IPage<HomeAdvertiseQueryVo> iPage = homeAdvertiseMapper.getHomeAdvertisePageList(page, homeAdvertiseQueryParam);
        return new Paging(iPage);
    }
}
