package co.lq.modules.user.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.WechatUser;
import co.lq.modules.user.mapper.WechatUserMapper;
import co.lq.modules.user.service.WechatUserService;
import co.lq.modules.user.web.param.WechatUserQueryParam;
import co.lq.modules.user.web.vo.WechatUserQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 微信用户表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class WechatUserServiceImpl extends BaseServiceImpl<WechatUserMapper, WechatUser> implements WechatUserService {

    private final WechatUserMapper wechatUserMapper;

    @Override
    public WechatUser getUserAppInfo(String openid) {
        QueryWrapper<WechatUser> wrapper = new QueryWrapper<>();
        wrapper.eq("routine_openid", openid).last("limit 1");
        return wechatUserMapper.selectOne(wrapper);
    }

    @Override
    public WechatUser getUserInfo(String openid) {
        QueryWrapper<WechatUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid).last("limit 1");
        return wechatUserMapper.selectOne(wrapper);
    }

    @Override
    public WechatUserQueryVo getWechatUserById(Serializable id) {
        return wechatUserMapper.getWechatUserById(id);
    }

    @Override
    public Paging<WechatUserQueryVo> getWechatUserPageList(WechatUserQueryParam wechatUserQueryParam) throws Exception {
        Page page = setPageParam(wechatUserQueryParam, OrderItem.desc("create_time"));
        IPage<WechatUserQueryVo> iPage = wechatUserMapper.getWechatUserPageList(page, wechatUserQueryParam);
        return new Paging(iPage);
    }

}
