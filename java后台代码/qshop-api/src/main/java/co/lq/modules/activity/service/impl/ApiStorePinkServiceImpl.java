package co.lq.modules.activity.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.exception.ErrorRequestException;
import co.lq.modules.activity.entity.StorePink;
import co.lq.modules.activity.mapper.StoreCombinationMapper;
import co.lq.modules.activity.mapper.StorePinkMapper;
import co.lq.modules.activity.mapping.StorePinkMap;
import co.lq.modules.activity.service.ApiStoreCombinationService;
import co.lq.modules.activity.service.ApiStorePinkService;
import co.lq.modules.activity.web.dto.PinkDTO;
import co.lq.modules.activity.web.dto.PinkInfoDTO;
import co.lq.modules.activity.web.param.StorePinkQueryParam;
import co.lq.modules.activity.web.vo.StoreCombinationQueryVo;
import co.lq.modules.activity.web.vo.StorePinkQueryVo;
import co.lq.modules.order.entity.StoreOrder;
import co.lq.modules.order.service.StoreOrderService;
import co.lq.modules.order.web.param.RefundParam;
import co.lq.modules.order.web.vo.StoreOrderQueryVo;
import co.lq.modules.shop.web.vo.StoreCartQueryVo;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.OrderUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 拼团表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-11-19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiStorePinkServiceImpl extends BaseServiceImpl<StorePinkMapper, StorePink>
        implements ApiStorePinkService {

    @Autowired
    private StorePinkMapper            storePinkMapper;
    @Autowired
    private StoreCombinationMapper     storeCombinationMapper;

    @Autowired
    private ApiStoreCombinationService combinationService;
    @Autowired
    private StoreOrderService          storeOrderService;
    @Autowired
    private UserService                userService;

    @Autowired
    private StorePinkMap               pinkMap;

    /**
     * 取消拼团
     *
     * @param uid
     * @param cid
     * @param pinkId
     */
    @Override
    public void removePink(long uid, long cid, long pinkId) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("id", pinkId).eq("uid", uid).eq("cid", cid).eq("k_id", 0).eq("is_refund", 0).eq("status", 1).gt(
                "stop_time", OrderUtil.getSecondTimestampTwo());
        StorePink pink = storePinkMapper.selectOne(wrapper);

        if (pink == null) {
            throw new ErrorRequestException("拼团不存在或已经取消");
        }

        Map<String, Object> map = getPinkMemberAndPinK(pink);
        List<StorePink> pinkAll = (List<StorePink>) map.get("pinkAll");
        StorePink pinkT = (StorePink) map.get("pinkT");
        List<Long> idAll = (List<Long>) map.get("idAll");
        List<Long> uidAll = (List<Long>) map.get("uidAll");
        int count = (int) map.get("count");
        if (count < 1) {
            pinkComplete(uidAll, idAll, uid, pinkT);

            throw new ErrorRequestException("拼团已完成，无法取消");
        }
        //如果团长取消拼团，团队还有人，就把后面的人作为下一任团长
        StorePink nextPinkT = null;
        if (pinkAll.size() > 0) {
            nextPinkT = pinkAll.get(0);
        }

        //先退团长的money
        RefundParam param = new RefundParam();
        param.setUni(pinkT.getOrderId());
        param.setText("拼团取消开团");
        storeOrderService.orderApplyRefund(param, pinkT.getUid());
        orderPinkFailAfter(pinkT.getUid(), pinkT.getId());

        if (ObjectUtil.isNotNull(nextPinkT)) {
            QueryWrapper<StorePink> wrapperO = new QueryWrapper<>();
            StorePink storePinkO = new StorePink();
            storePinkO.setKId(0L);
            storePinkO.setStatus(1);
            storePinkO.setStopTime(OrderUtil.getSecondTimestampTwo() + "");
            storePinkO.setId(nextPinkT.getId());
            storePinkMapper.updateById(storePinkO);

            //原有团长的数据变更成新团长下面
            wrapperO.eq("k_id", pinkT.getId());
            StorePink storePinkT = new StorePink();
            storePinkT.setKId(nextPinkT.getId());
            storePinkMapper.update(storePinkT, wrapperO);

            //update order

            StoreOrder storeOrder = new StoreOrder();
            storeOrder.setPinkId(nextPinkT.getId());

            storeOrderService.updateById(storeOrder);

        }
    }

    /**
     * 计算还差几人拼团
     *
     * @param pink
     * @return
     */
    @Override
    public int surplusPeople(StorePink pink) {
        List<StorePink> listT = new ArrayList<>();
        if (pink.getKId() > 0) {
            listT = getPinkMember(pink.getKId());
        } else {
            listT = getPinkMember(pink.getId());
        }

        return pink.getPeople() - (listT.size() + 1);
    }

    /**
     * 处理团员
     *
     * @param pinkAll
     * @return
     */
    @Override
    public List<StorePinkQueryVo> handPinkAll(List<StorePink> pinkAll) {

        List<StorePinkQueryVo> list = pinkMap.toDto(pinkAll);
        for (StorePinkQueryVo queryVo : list) {
            UserQueryVo userQueryVo = userService.getUserById(queryVo.getUid());
            queryVo.setAvatar(userQueryVo.getAvatar());
            queryVo.setNickname(userQueryVo.getNickname());
        }
        return list;
    }

    /**
     * 处理团长
     *
     * @param pinkT
     * @return
     */
    @Override
    public StorePinkQueryVo handPinkT(StorePink pinkT) {
        StorePinkQueryVo pinkQueryVo = pinkMap.toDto(pinkT);
        UserQueryVo userQueryVo = userService.getUserById(pinkQueryVo.getUid());
        pinkQueryVo.setAvatar(userQueryVo.getAvatar());
        pinkQueryVo.setNickname(userQueryVo.getNickname());

        return pinkQueryVo;
    }

    @Override
    public String getCurrentPinkOrderId(long id, long uid) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("uid", uid);
        StorePink pink = storePinkMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(pink)) {
            QueryWrapper<StorePink> wrapperT = new QueryWrapper<>();
            wrapperT.eq("k_id", id).eq("uid", uid);
            pink = storePinkMapper.selectOne(wrapperT);
            if (ObjectUtil.isNull(pink)) {
                return "";
            }
        }
        return pink.getOrderId();
    }

    /**
     * 获取当前拼团数据
     *
     * @param id
     * @param uid
     * @return
     */
    @Override
    public StorePink getCurrentPink(long id, long uid) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("uid", uid);
        StorePink pink = storePinkMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(pink)) {
            QueryWrapper<StorePink> wrapperT = new QueryWrapper<>();
            wrapperT.eq("k_id", id).eq("uid", uid);
            pink = storePinkMapper.selectOne(wrapperT);
            if (ObjectUtil.isNull(pink)) {
                pink.setOrderId("");
            }
        }
        return pink;
    }

    /**
     * 拼团明细
     *
     * @param id
     * @param uid
     */
    @Override
    public PinkInfoDTO pinkInfo(long id, long uid) {
        PinkDTO pinkDTO = getPinkUserOneT(id);
        if (ObjectUtil.isNull(pinkDTO)) {
            throw new ErrorRequestException("拼团不存在");
        }

        PinkInfoDTO infoDTO = new PinkInfoDTO();

        StorePink pink = getPinkUserOne(id);
        if (pink.getIsRefund() > 0) {
            throw new ErrorRequestException("订单已退款");
        }

        int isOk = 0;//判断拼团是否完成
        int userBool = 0;//判断当前用户是否在团内  0未在 1在
        int pinkBool = 0;//判断拼团是否成功  0未 1是 -1结束

        Map<String, Object> map = getPinkMemberAndPinK(pink);
        List<StorePink> pinkAll = (List<StorePink>) map.get("pinkAll");
        StorePink pinkT = (StorePink) map.get("pinkT");
        List<Long> idAll = (List<Long>) map.get("idAll");
        List<Long> uidAll = (List<Long>) map.get("uidAll");
        int count = (int) map.get("count");
        if (count <= 0) {
            count = 0;
        }
        if (pinkT.getStatus() == 2) {
            pinkBool = 1;
            isOk = 1;

        } else if (pinkT.getStatus() == 3) {
            pinkBool = -1;
            isOk = 0;
        } else {
            if (count < 1) {//组团完成
                isOk = 1;
                pinkBool = pinkComplete(uidAll, idAll, uid, pinkT);
            } else {
                pinkBool = pinkFail(pinkAll, pinkT, pinkBool);
            }
        }

        //团员
        if (ObjectUtil.isNotNull(pinkAll)) {
            for (StorePink storePink : pinkAll) {
                if (storePink.getUid() == uid) {
                    userBool = 1;
                }
            }
        }

        //团长
        if (pinkT.getUid() == uid) {
            userBool = 1;
        }

        StoreCombinationQueryVo storeCombinationQueryVo = storeCombinationMapper.getCombDetail(pink.getCid());
        if (ObjectUtil.isNull(storeCombinationQueryVo)) {
            throw new ErrorRequestException("拼团不存在或已下架");
        }

        UserQueryVo userInfo = userService.getUserById(uid);

        infoDTO.setCount(count);
        infoDTO.setCurrentPinkOrder(getCurrentPinkOrderId(id, uid));
        infoDTO.setIsOk(isOk);
        infoDTO.setPinkAll(handPinkAll(pinkAll));
        infoDTO.setPinkBool(pinkBool);
        infoDTO.setPinkT(handPinkT(pinkT));
        infoDTO.setStoreCombination(storeCombinationQueryVo);
        infoDTO.setUserBool(userBool);
        infoDTO.setUserInfo(userInfo);

        return infoDTO;

    }

    @Override
    public PinkDTO getPinkUserOneT(long id) {
        return storePinkMapper.getPinkUserOne(id);
    }

    @Override
    public int pinkIngCount(long id) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("status", 1);
        return storePinkMapper.selectCount(wrapper);
    }

    /**
     * 获取拼团的团员
     *
     * @param kid
     * @return
     */
    @Override
    public List<StorePink> getPinkMember(long kid) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("k_id", kid).eq("is_refund", 0).orderByAsc("id");
        return storePinkMapper.selectList(wrapper);
    }

    /**
     * 获取一条拼团数据
     *
     * @param id
     * @return
     */
    @Override
    public StorePink getPinkUserOne(long id) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return storePinkMapper.selectOne(wrapper);
    }

    /**
     * 拼团人数完成时，判断全部人都是未退款状态
     *
     * @return
     */
    @Override
    public boolean getPinkStatus(List<Long> idAll) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.in("id", idAll).eq("is_refund", 0);
        int count = storePinkMapper.selectCount(wrapper);
        if (count == 0)
            return true;
        return false;
    }

    /**
     * 设置结束时间
     *
     * @param idAll
     */
    @Override
    public void setPinkStopTime(List<Long> idAll) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.in("id", idAll);

        StorePink storePink = new StorePink();
        storePink.setStopTime(OrderUtil.getSecondTimestampTwo() + "");
        storePink.setStatus(2);

        storePinkMapper.update(storePink, wrapper);
    }

    /**
     * 拼团完成更改数据写入内容
     *
     * @param uidAll
     * @param idAll
     * @param uid
     * @param pinkT
     */
    @Override
    public int pinkComplete(List<Long> uidAll, List<Long> idAll, long uid, StorePink pinkT) {
        boolean pinkStatus = getPinkStatus(idAll);
        int pinkBool = 6;
        if (!pinkStatus) {
            setPinkStopTime(idAll);//更改状态
            if (uidAll.contains(uid)) {
                pinkBool = 1;
            } else {
                pinkBool = 3;
            }
            //todo 模板消息
        }

        return pinkBool;

    }

    /**
     * 拼团失败退款之后
     *
     * @param uid
     * @param pid
     */
    @Override
    public void orderPinkFailAfter(long uid, long pid) {
        StorePink storePink = new StorePink();
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("id", pid);
        storePink.setStatus(3);
        storePink.setStopTime(OrderUtil.getSecondTimestampTwo() + "");
        storePinkMapper.update(storePink, wrapper);

        QueryWrapper<StorePink> wrapperT = new QueryWrapper<>();
        wrapperT.eq("k_id", pid);
        storePinkMapper.update(storePink, wrapperT);
        //todo 模板消息
    }

    /**
     * 拼团失败 退款
     *
     * @param pinkAll 拼团数据,不包括团长
     * @param pinkT 团长数据
     * @param pinkBool
     */
    @Override
    public int pinkFail(List<StorePink> pinkAll, StorePink pinkT, int pinkBool) {
        int now = OrderUtil.getSecondTimestampTwo();

        //拼团时间超时  退款
        if (Integer.valueOf(pinkT.getStopTime()) < now) {
            pinkBool = -1;
            pinkAll.add(pinkT);
            for (StorePink storePink : pinkAll) {
                RefundParam param = new RefundParam();
                param.setUni(storePink.getOrderId());
                param.setText("拼团时间超时");
                storeOrderService.orderApplyRefund(param, storePink.getUid());
                orderPinkFailAfter(pinkT.getUid(), storePink.getId());
            }
        }

        return pinkBool;
    }

    /**
     * 获取参团人和团长和拼团总人数
     *
     * @param pink
     * @return
     */
    @Override
    public Map<String, Object> getPinkMemberAndPinK(StorePink pink) {
        Map<String, Object> map = new LinkedHashMap<>();
        //查找拼团团员和团长
        List<StorePink> pinkAll = null;
        StorePink pinkT = null;
        List<Long> idAll = new ArrayList<>();
        List<Long> uidAll = new ArrayList<>();
        int count = 0;
        if (pink.getKId() > 0) {
            pinkAll = getPinkMember(pink.getKId());
            pinkT = getPinkUserOne(pink.getKId());
        } else {
            pinkAll = getPinkMember(pink.getId());
            pinkT = pink;
        }
        //收集拼团用户id和拼团id
        for (StorePink storePink : pinkAll) {
            idAll.add(storePink.getId());
            uidAll.add(storePink.getUid());
        }
        idAll.add(pinkT.getId());
        uidAll.add(pinkT.getUid());
        //还差几人
        count = pinkT.getPeople() - (pinkAll.size() + 1);

        map.put("pinkAll", pinkAll);
        map.put("pinkT", pinkT);
        map.put("count", count);
        map.put("idAll", idAll);
        map.put("uidAll", uidAll);

        return map;
    }

    /**
     * 创建拼团
     *
     * @param order
     */
    @Override
    public void createPink(StoreOrderQueryVo order) {
        StoreCombinationQueryVo combinationQueryVo = combinationService
                .getStoreCombinationById(order.getCombinationId());
        order = storeOrderService.handleOrder(order);
        if (ObjectUtil.isNotNull(combinationQueryVo)) {
            StorePink storePink = new StorePink();
            storePink.setUid(order.getUid());
            storePink.setOrderId(order.getOrderId());
            storePink.setOrderIdKey(order.getId());
            storePink.setTotalNum(order.getTotalNum());
            storePink.setTotalPrice(order.getPayPrice());
            storePink.setKId(0L);
            List<StoreCartQueryVo> cartInfo = order.getCartInfo();
            for (StoreCartQueryVo queryVo : cartInfo) {
                storePink.setCid(queryVo.getCombinationId());
                storePink.setPid(queryVo.getProductId());
                storePink.setPrice(queryVo.getProductInfo().getPrice());
            }
            storePink.setPeople(combinationQueryVo.getPeople());
            storePink.setStopTime(
                    OrderUtil.getSecondTimestampTwo() + (combinationQueryVo.getEffectiveTime() * 3600) + "");
            storePink.setAddTime(OrderUtil.getSecondTimestampTwo() + "");
            if (order.getPinkId() > 0) {
                if (getIsPinkUid(order.getPinkId(), order.getUid()) > 0) {
                    return;
                }
                storePink.setKId(order.getPinkId());
                storePink.setStopTime("0");
                save(storePink);

                //处理拼团完成
                Map<String, Object> map = getPinkMemberAndPinK(storePink);
                StorePink pinkT = (StorePink) map.get("pinkT");
                if (pinkT.getStatus() == 1) {
                    int count = (int) map.get("count");
                    if (count == 0) {
                        //处理成功
                        pinkComplete((List<Long>) map.get("uidAll"), (List<Long>) map.get("idAll"), order.getUid(),
                                pinkT);
                    } else {
                        pinkFail((List<StorePink>) map.get("pinkAll"), pinkT, 0);
                    }
                }

            } else {
                save(storePink);
                //pink_id更新到order表
                StoreOrder storeOrder = new StoreOrder();
                storeOrder.setPinkId(storePink.getId());
                storeOrder.setId(order.getId());
                storeOrderService.updateById(storeOrder);
            }

            //todo 模板消息
        }
    }

    /**
     * 判断用户是否在团内
     *
     * @param id
     * @param uid
     * @return
     */
    @Override
    public int getIsPinkUid(long id, long uid) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("is_refund", 0).eq("uid", uid).and(i -> i.eq("k_id", id).or().eq("id", id));
        return storePinkMapper.selectCount(wrapper);
    }

    /**
     * 获取拼团完成的商品总件数
     *
     * @return
     */
    @Override
    public int getPinkOkSumTotalNum() {
        return storePinkMapper.sumNum();
    }

    /**
     * 获取拼团完成的用户
     *
     * @param uid
     * @return
     */
    @Override
    public List<String> getPinkOkList(long uid) {
        List<String> list = new ArrayList<>();
        List<PinkDTO> pinkDTOList = storePinkMapper.getPinkOkList(uid);
        for (PinkDTO pinkDTO : pinkDTOList) {
            list.add(pinkDTO.getNickname() + "拼团成功");
        }
        //list = pinkDTOList.stream().map(PinkDTO::getNickname).collect(Collectors.toList());
        return list;
    }

    @Override
    public int getPinkPeople(long kid, int people) {
        QueryWrapper<StorePink> wrapper = new QueryWrapper<>();
        wrapper.eq("k_id", kid).eq("is_refund", 0);
        int count = storePinkMapper.selectCount(wrapper) + 1;
        return people - count;
    }

    /**
     * 获取团长拼团数据
     *
     * @param cid
     * @param isAll
     * @return
     */
    @Override
    public Map<String, Object> getPinkAll(long cid, boolean isAll) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PinkDTO> list = storePinkMapper.getPinks(cid);
        if (isAll) {
            List<Long> pindAll = new ArrayList<>();
            for (PinkDTO pinkDTO : list) {
                pinkDTO.setCount(String.valueOf(getPinkPeople(pinkDTO.getId(), pinkDTO.getPeople())));
                Date date = DateUtil.parse(OrderUtil.stampToDate(pinkDTO.getStopTime()));
                System.out.println(date);
                pinkDTO.setH(String.valueOf(DateUtil.hour(date, true)));
                pinkDTO.setI(String.valueOf(DateUtil.minute(date)));
                pinkDTO.setS(String.valueOf(DateUtil.second(date)));
                pindAll.add(pinkDTO.getId());
            }

            map.put("pindAll", pindAll);

        }

        map.put("list", list);

        return map;

    }

    @Override
    public StorePinkQueryVo getStorePinkById(Serializable id) {
        return storePinkMapper.getStorePinkById(id);
    }

    @Override
    public Paging<StorePinkQueryVo> getStorePinkPageList(StorePinkQueryParam storePinkQueryParam) throws Exception {
        Page page = setPageParam(storePinkQueryParam, OrderItem.desc("create_time"));
        IPage<StorePinkQueryVo> iPage = storePinkMapper.getStorePinkPageList(page, storePinkQueryParam);
        return new Paging(iPage);
    }

}
