package co.lq.modules.wechat.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.wechat.entity.WechatTemplate;
import co.lq.modules.wechat.mapper.WechatTemplateMapper;
import co.lq.modules.wechat.service.ApiWechatTemplateService;
import co.lq.modules.wechat.web.param.WechatTemplateQueryParam;
import co.lq.modules.wechat.web.vo.WechatTemplateQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 微信模板 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-10
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiWechatTemplateServiceImpl extends BaseServiceImpl<WechatTemplateMapper, WechatTemplate>
        implements ApiWechatTemplateService {

    private final WechatTemplateMapper wechatTemplateMapper;

    @Override
    public WechatTemplateQueryVo getWechatTemplateById(Serializable id) throws Exception {
        return wechatTemplateMapper.getWechatTemplateById(id);
    }

    @Override
    public Paging<WechatTemplateQueryVo> getWechatTemplatePageList(WechatTemplateQueryParam wechatTemplateQueryParam)
            throws Exception {
        Page page = setPageParam(wechatTemplateQueryParam, OrderItem.desc("create_time"));
        IPage<WechatTemplateQueryVo> iPage = wechatTemplateMapper.getWechatTemplatePageList(page,
                wechatTemplateQueryParam);
        return new Paging(iPage);
    }

}
