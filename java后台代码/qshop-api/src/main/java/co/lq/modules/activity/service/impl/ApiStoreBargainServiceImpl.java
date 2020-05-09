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
import co.lq.modules.activity.entity.StoreBargain;
import co.lq.modules.activity.entity.StoreBargainUser;
import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.mapper.StoreBargainMapper;
import co.lq.modules.activity.mapping.StoreBargainMap;
import co.lq.modules.activity.service.ApiStoreBargainService;
import co.lq.modules.activity.service.ApiStoreBargainUserHelpService;
import co.lq.modules.activity.service.ApiStoreBargainUserService;
import co.lq.modules.activity.web.dto.BargainCountDTO;
import co.lq.modules.activity.web.dto.BargainDTO;
import co.lq.modules.activity.web.dto.TopCountDTO;
import co.lq.modules.activity.web.param.StoreBargainQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainQueryVo;
import co.lq.modules.order.entity.StoreOrder;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.user.service.UserService;
import co.lq.utils.OrderUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 砍价表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Slf4j
@Service
public class ApiStoreBargainServiceImpl extends BaseServiceImpl<StoreBargainMapper, StoreBargain>
        implements ApiStoreBargainService {

    @Autowired
    private StoreBargainMapper             storeBargainMapper;
    @Autowired
    private StoreBargainMap                storeBargainMap;

    @Autowired
    private ApiStoreBargainUserService     apiStoreBargainUserService;
    @Autowired
    private UserService                    userService;
    @Autowired
    private StoreOrderService              storeOrderService;
    @Autowired
    private ApiStoreBargainUserHelpService apiStoreBargainUserHelpService;

    /**
     * 退回库存销量
     *
     * @param num
     * @param bargainId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incStockDecSales(int num, long bargainId) {
        storeBargainMapper.incStockDecSales(num, bargainId);
    }

    /**
     * 增加销量 减少库存
     *
     * @param num
     * @param bargainId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decStockIncSales(int num, long bargainId) {
        storeBargainMapper.decStockIncSales(num, bargainId);
    }

    @Override
    public StoreBargain getBargain(long bargainId) {
        QueryWrapper<StoreBargain> wrapper = new QueryWrapper<>();
        int nowTime = OrderUtil.getSecondTimestampTwo();
        wrapper.eq("id", bargainId).eq("is_del", 0).eq("status", 1).le("start_time", nowTime).ge("stop_time", nowTime);
        return storeBargainMapper.selectOne(wrapper);
    }

    /**
     * 开始帮助好友砍价
     *
     * @param bargainId 砍价产品id
     * @param bargainUserUid 开启砍价用户id
     * @param uid 当前用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doHelp(long bargainId, long bargainUserUid, long uid) {
        //开始真正的砍价
        StoreBargainUser storeBargainUser = apiStoreBargainUserService.getBargainUserInfo(bargainId, bargainUserUid);

        //判断是否已经帮助过
        int count = apiStoreBargainUserHelpService
                .count(new QueryWrapper<StoreBargainUserHelp>().eq("bargain_id", bargainId)
                        .eq("bargain_user_id", storeBargainUser.getId())
                        .eq("uid", uid));
        if (count > 0) {
            return;
        }

        StoreBargainQueryVo storeBargainQueryVo = getStoreBargainById(bargainId);
        //用户可以砍掉的金额 好友砍价之前获取可以砍价金额
        double coverPrice = NumberUtil.sub(storeBargainUser.getBargainPrice(), storeBargainUser.getBargainPriceMin())
                .doubleValue();

        double random = 0d;
        if (coverPrice > 0) {
            //用户剩余要砍掉的价格
            double surplusPrice = NumberUtil.sub(coverPrice, storeBargainUser.getPrice()).doubleValue();
            if (surplusPrice == 0) {
                return;
            }

            //生成一个区间随机数
            random = OrderUtil.randomNumber(storeBargainQueryVo.getBargainMinPrice().doubleValue(),
                    storeBargainQueryVo.getBargainMaxPrice().doubleValue());
            if (random > surplusPrice) {
                random = surplusPrice;
            }
        }

        //添加砍价帮助表
        StoreBargainUserHelp storeBargainUserHelp = StoreBargainUserHelp.builder()
                .uid(uid)
                .bargainId(bargainId)
                .bargainUserId(storeBargainUser.getId())
                .price(BigDecimal.valueOf(random))
                .addTime(OrderUtil.getSecondTimestampTwo())
                .build();
        apiStoreBargainUserHelpService.save(storeBargainUserHelp);

        //更新砍价参与表
        StoreBargainUser bargainUser = StoreBargainUser.builder()
                .id(storeBargainUser.getId())
                .price(BigDecimal.valueOf(NumberUtil.add(storeBargainUser.getPrice().doubleValue(), random)))
                .build();

        apiStoreBargainUserService.updateById(bargainUser);
    }

    /**
     * 顶部统计
     *
     * @param bargainId
     * @return
     */
    @Override
    public TopCountDTO topCount(long bargainId) {
        TopCountDTO topCountDTO = TopCountDTO.builder()
                .lookCount(storeBargainMapper.lookCount())
                .shareCount(storeBargainMapper.shareCount())
                .userCount(apiStoreBargainUserService.count())
                .build();
        if (bargainId > 0) {
            addBargainShare(bargainId);
        }
        return topCountDTO;
    }

    /**
     * 砍价 砍价帮总人数、剩余金额、进度条、已经砍掉的价格
     *
     * @param bargainId
     * @param uid
     */
    @Override
    public BargainCountDTO helpCount(long bargainId, long uid) {
        StoreBargainUser storeBargainUser = apiStoreBargainUserService.getBargainUserInfo(bargainId, uid);
        if (ObjectUtil.isNull(storeBargainUser)) {
            BargainCountDTO bargainCountDTO = BargainCountDTO.builder()
                    .count(0)
                    .alreadyPrice(0d)
                    .status(0)
                    .pricePercent(0)
                    .price(0d)
                    .build();

            return bargainCountDTO;
        }

        int count = apiStoreBargainUserHelpService.getBargainUserHelpPeopleCount(bargainId, storeBargainUser.getId());
        //用户可以砍掉的价格
        double diffPrice = NumberUtil.sub(storeBargainUser.getBargainPrice(), storeBargainUser.getBargainPriceMin())
                .doubleValue();
        //砍价进度条百分比
        int pricePercent = 0;
        if (diffPrice <= 0) {
            pricePercent = 100;
        } else {
            pricePercent = NumberUtil
                    .round(NumberUtil.mul(NumberUtil.div(storeBargainUser.getPrice(), diffPrice), 100), 0)
                    .intValue();
        }

        BargainCountDTO bargainCountDTO = BargainCountDTO.builder()
                .count(count)
                .alreadyPrice(storeBargainUser.getPrice().doubleValue())
                .status(storeBargainUser.getStatus())
                .pricePercent(pricePercent)
                .price(NumberUtil.sub(diffPrice, storeBargainUser.getPrice()).doubleValue())
                .build();

        return bargainCountDTO;
    }

    /**
     * 砍价支付成功订单数量
     *
     * @param bargainId 砍价id
     * @return int
     */
    @Override
    public int getBargainPayCount(long bargainId) {
        return storeOrderService
                .count(new QueryWrapper<StoreOrder>().eq("bargain_id", bargainId).eq("paid", 1).eq("refund_status", 0));
    }

    /**
     * 增加分享次数
     *
     * @param id
     */
    @Override
    public void addBargainShare(long id) {
        storeBargainMapper.addBargainShare(id);
    }

    /**
     * 增加浏览次数
     *
     * @param id
     */
    @Override
    public void addBargainLook(long id) {
        storeBargainMapper.addBargainLook(id);
    }

    /**
     * 砍价详情
     *
     * @param id 砍价id
     * @param uid 用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BargainDTO getDetail(long id, long uid) {
        QueryWrapper<StoreBargain> wrapper = new QueryWrapper<>();
        wrapper.eq("is_del", 0).eq("status", 1).eq("id", id);

        StoreBargain storeBargain = storeBargainMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(storeBargain)) {
            throw new ErrorRequestException("砍价已结束");
        }

        addBargainLook(id);

        StoreBargainQueryVo storeBargainQueryVo = storeBargainMap.toDto(storeBargain);

        BargainDTO bargainDTO = BargainDTO.builder()
                .bargain(storeBargainQueryVo)
                .userInfo(userService.getUserById(uid))
                .bargainSumCount(getBargainPayCount(id))
                .build();

        return bargainDTO;
    }

    /**
     * 获取砍价商品列表
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StoreBargainQueryVo> getList(int page, int limit) {
        Page<StoreBargain> pageModel = new Page<>(page, limit);
        QueryWrapper<StoreBargain> wrapper = new QueryWrapper<>();
        int nowTime = OrderUtil.getSecondTimestampTwo();
        wrapper.eq("is_del", 0).eq("status", 1).ge("stock", 0).eq("status", 1).lt("start_time", nowTime).gt("stop_time",
                nowTime);

        List<StoreBargainQueryVo> storeBargainQueryVos = storeBargainMap
                .toDto(storeBargainMapper.selectPage(pageModel, wrapper).getRecords());

        storeBargainQueryVos.forEach(item -> {
            item.setPeople(apiStoreBargainUserService.getBargainUserList(item.getId(), 1).size());
        });

        return storeBargainQueryVos;
    }

    @Override
    public StoreBargainQueryVo getStoreBargainById(Serializable id) {
        return storeBargainMapper.getStoreBargainById(id);
    }

    @Override
    public Paging<StoreBargainQueryVo> getStoreBargainPageList(StoreBargainQueryParam storeBargainQueryParam)
            throws Exception {
        Page page = setPageParam(storeBargainQueryParam, OrderItem.desc("create_time"));
        IPage<StoreBargainQueryVo> iPage = storeBargainMapper.getStoreBargainPageList(page, storeBargainQueryParam);
        return new Paging(iPage);
    }

}
