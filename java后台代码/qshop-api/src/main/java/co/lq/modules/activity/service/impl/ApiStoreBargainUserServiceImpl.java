package co.lq.modules.activity.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.activity.entity.StoreBargainUser;
import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.mapper.StoreBargainUserMapper;
import co.lq.modules.activity.mapping.StoreBargainUserMap;
import co.lq.modules.activity.service.ApiStoreBargainService;
import co.lq.modules.activity.service.ApiStoreBargainUserHelpService;
import co.lq.modules.activity.service.ApiStoreBargainUserService;
import co.lq.modules.activity.web.param.StoreBargainUserQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainQueryVo;
import co.lq.modules.activity.web.vo.StoreBargainUserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户参与砍价表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Slf4j
@Service
public class ApiStoreBargainUserServiceImpl extends BaseServiceImpl<StoreBargainUserMapper, StoreBargainUser>
        implements ApiStoreBargainUserService {

    @Autowired
    private StoreBargainUserMapper         storeBargainUserMapper;
    @Autowired
    private StoreBargainUserMap            storeBargainUserMap;

    @Autowired
    private ApiStoreBargainService         apiStoreBargainService;
    @Autowired
    private ApiStoreBargainUserHelpService apiStoreBargainUserHelpService;

    /**
     * 修改用户砍价状态
     *
     * @param bargainId 砍价产品id
     * @param uid 用户id
     */
    @Override
    public void setBargainUserStatus(long bargainId, long uid) {
        StoreBargainUser storeBargainUser = getBargainUserInfo(bargainId, uid);
        if (ObjectUtil.isNull(storeBargainUser))
            return;

        if (storeBargainUser.getStatus() != 1)
            return;
        double price = NumberUtil
                .sub(NumberUtil.sub(storeBargainUser.getBargainPrice(), storeBargainUser.getBargainPriceMin()),
                        storeBargainUser.getPrice())
                .doubleValue();
        if (price > 0)
            return;

        storeBargainUser.setStatus(3);

        storeBargainUserMapper.updateById(storeBargainUser);
    }

    /**
     * 砍价取消
     *
     * @param bargainId
     * @param uid
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bargainCancel(long bargainId, long uid) {
        StoreBargainUser storeBargainUser = getBargainUserInfo(bargainId, uid);
        if (ObjectUtil.isNull(storeBargainUser))
            throw new ErrorRequestException("数据不存在");

        if (storeBargainUser.getStatus() != 1)
            throw new ErrorRequestException("状态错误");

        storeBargainUser.setIsDel(1);

        storeBargainUserMapper.updateById(storeBargainUser);
    }

    /**
     * 获取用户的砍价产品
     *
     * @param bargainUserUid
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StoreBargainUserQueryVo> bargainUserList(long bargainUserUid, int page, int limit) {
        Page<StoreBargainUser> pageModel = new Page<>(page, limit);
        return storeBargainUserMapper.getBargainUserList(bargainUserUid, pageModel);
    }

    /**
     * 判断用户是否还可以砍价
     *
     * @param bargainId 砍价产品id
     * @param bargainUserUid 开启砍价用户id
     * @param uid 当前用户id
     * @return
     */
    @Override
    public boolean isBargainUserHelp(long bargainId, long bargainUserUid, long uid) {
        StoreBargainUser storeBargainUser = getBargainUserInfo(bargainId, bargainUserUid);
        StoreBargainQueryVo storeBargainQueryVo = apiStoreBargainService.getStoreBargainById(bargainId);
        if (ObjectUtil.isNull(storeBargainUser) || ObjectUtil.isNull(storeBargainQueryVo)) {
            return false;
        }

        int count = apiStoreBargainUserHelpService
                .count(new QueryWrapper<StoreBargainUserHelp>().eq("bargain_id", bargainId)
                        .eq("bargain_user_id", storeBargainUser.getId())
                        .eq("uid", uid));

        if (count == 0)
            return true;

        return false;
    }

    /**
     * 添加砍价记录
     *
     * @param bargainId
     * @param uid
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setBargain(Long bargainId, Long uid) {
        if (ObjectUtil.isNotNull(getBargainUserInfo(bargainId, uid)))
            return;
        StoreBargainQueryVo storeBargainQueryVo = apiStoreBargainService.getStoreBargainById(bargainId);
        StoreBargainUser storeBargainUser = StoreBargainUser.builder()
                .bargainId(bargainId)
                .uid(uid)
                .bargainPrice(storeBargainQueryVo.getPrice())
                .bargainPriceMin(storeBargainQueryVo.getMinPrice())
                .price(BigDecimal.ZERO)
                .status(1)
                .isDel(0)
                .addTime(OrderUtil.getSecondTimestampTwo())
                .build();

        storeBargainUserMapper.insert(storeBargainUser);

    }

    /**
     * 获取用户可以砍掉的价格
     *
     * @param id
     * @return
     */
    @Override
    public double getBargainUserDiffPrice(long id) {
        StoreBargainUserQueryVo storeBargainUserQueryVo = getStoreBargainUserById(id);
        return NumberUtil.sub(storeBargainUserQueryVo.getBargainPrice(), storeBargainUserQueryVo.getBargainPriceMin())
                .doubleValue();
    }

    /**
     * 获取某个用户参与砍价信息
     *
     * @param bargainId
     * @param uid
     * @return
     */
    @Override
    public StoreBargainUser getBargainUserInfo(long bargainId, long uid) {
        QueryWrapper<StoreBargainUser> wrapper = new QueryWrapper<>();
        wrapper.eq("bargain_id", bargainId).eq("uid", uid).eq("is_del", 0).last("limit 1");
        return storeBargainUserMapper.selectOne(wrapper);
    }

    /**
     * 获取参与砍价的用户列表
     *
     * @param bargainId 砍价id
     * @param status 状态 1 进行中 2 结束失败 3结束成功
     * @return
     */
    @Override
    public List<StoreBargainUserQueryVo> getBargainUserList(long bargainId, int status) {
        QueryWrapper<StoreBargainUser> wrapper = new QueryWrapper<>();
        wrapper.eq("bargain_id", bargainId).eq("status", status);
        return storeBargainUserMap.toDto(storeBargainUserMapper.selectList(wrapper));
    }

    @Override
    public StoreBargainUserQueryVo getStoreBargainUserById(Serializable id) {
        return storeBargainUserMapper.getStoreBargainUserById(id);
    }

    @Override
    public Paging<StoreBargainUserQueryVo> getStoreBargainUserPageList(StoreBargainUserQueryParam storeBargainUserQueryParam)
            throws Exception {
        Page page = setPageParam(storeBargainUserQueryParam, OrderItem.desc("create_time"));
        IPage<StoreBargainUserQueryVo> iPage = storeBargainUserMapper.getStoreBargainUserPageList(page,
                storeBargainUserQueryParam);
        return new Paging(iPage);
    }

}
