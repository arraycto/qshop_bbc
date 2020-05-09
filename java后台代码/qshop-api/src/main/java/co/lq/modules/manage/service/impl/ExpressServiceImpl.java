package co.lq.modules.manage.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.manage.entity.Express;
import co.lq.modules.manage.mapper.ExpressMapper;
import co.lq.modules.manage.service.ExpressService;
import co.lq.modules.manage.web.param.ExpressQueryParam;
import co.lq.modules.manage.web.vo.ExpressQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 快递公司表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-13
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ExpressServiceImpl extends BaseServiceImpl<ExpressMapper, Express> implements ExpressService {

    private final ExpressMapper expressMapper;

    @Override
    public ExpressQueryVo getExpressById(Serializable id) {
        return expressMapper.getExpressById(id);
    }

    @Override
    public Paging<ExpressQueryVo> getExpressPageList(ExpressQueryParam expressQueryParam) throws Exception {
        Page page = setPageParam(expressQueryParam, OrderItem.desc("create_time"));
        IPage<ExpressQueryVo> iPage = expressMapper.getExpressPageList(page, expressQueryParam);
        return new Paging(iPage);
    }

}
