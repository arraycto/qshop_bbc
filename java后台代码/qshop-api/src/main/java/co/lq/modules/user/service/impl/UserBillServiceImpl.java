package co.lq.modules.user.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.mapper.UserBillMapper;
import co.lq.modules.user.mapping.BiillMap;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.web.dto.BillDTO;
import co.lq.modules.user.web.dto.BillOrderDTO;
import co.lq.modules.user.web.dto.BillOrderRecordDTO;
import co.lq.modules.user.web.param.UserBillQueryParam;
import co.lq.modules.user.web.vo.UserBillQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户账单表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserBillServiceImpl extends BaseServiceImpl<UserBillMapper, UserBill> implements UserBillService {

    private final UserBillMapper userBillMapper;

    private final BiillMap       biillMap;

    /**
     * 签到了多少次
     *
     * @param uid
     * @return
     */
    @Override
    public int cumulativeAttendance(long uid) {
        QueryWrapper<UserBill> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("category", "integral").eq("type", "sign").eq("pm", 1);
        return userBillMapper.selectCount(wrapper);
    }

    @Override
    public Map<String, Object> spreadOrder(long uid, int page, int limit) {
        QueryWrapper<UserBill> wrapper = new QueryWrapper<>();
        wrapper.in("uid", uid).eq("type", "brokerage").eq("category", "now_money").orderByDesc("time").groupBy("time");
        Page<UserBill> pageModel = new Page<>(page, limit);
        List<String> list = userBillMapper.getBillOrderList(wrapper, pageModel);

        //        QueryWrapper<UserBill> wrapperT = new QueryWrapper<>();
        //        wrapperT.in("uid",uid).eq("type","brokerage")
        //                .eq("category","now_money");

        int count = (int) pageModel.getTotal();
        List<BillOrderDTO> listT = new ArrayList<>();
        for (String str : list) {
            BillOrderDTO billOrderDTO = new BillOrderDTO();
            List<BillOrderRecordDTO> orderRecordDTOS = userBillMapper.getBillOrderRList(str, uid);
            billOrderDTO.setChild(orderRecordDTOS);
            billOrderDTO.setCount(orderRecordDTOS.size());
            billOrderDTO.setTime(str);

            listT.add(billOrderDTO);
        }

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("list", listT);
        map.put("count", count);

        return map;
    }

    @Override
    public List<BillDTO> getUserBillList(int page, int limit, long uid, int type) {
        QueryWrapper<UserBill> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).orderByDesc("add_time").groupBy("time");
        String str = "";
        switch (type) {
            case 0:
                wrapper.eq("category", "now_money");
                str = "recharge,brokerage,pay_product,system_add,pay_product_refund,system_sub";
                wrapper.in("type", Arrays.asList(str.split(",")));
                break;
            case 1:
                wrapper.eq("category", "now_money");
                wrapper.eq("type", "pay_product");
                break;
            case 2:
                wrapper.eq("category", "now_money");
                wrapper.eq("type", "recharge");
                break;
            case 3:
                wrapper.eq("category", "now_money");
                wrapper.eq("type", "brokerage");
                break;
            case 4:
                wrapper.eq("category", "now_money");
                wrapper.eq("type", "extract");
                break;
            case 5:
                wrapper.eq("category", "integral");
                wrapper.eq("type", "sign");
                break;
            default:
                wrapper.eq("category", "now_money");
                str = "recharge,brokerage,pay_product,system_add,pay_product_refund,system_sub";
                wrapper.in("type", Arrays.asList(str.split(",")));
                break;
        }
        Page<UserBill> pageModel = new Page<>(page, limit);
        List<BillDTO> billDTOList = userBillMapper.getBillList(wrapper, pageModel);
        for (BillDTO billDTO : billDTOList) {
            QueryWrapper<UserBill> wrapperT = new QueryWrapper<>();
            wrapperT.in("id", Arrays.asList(billDTO.getIds().split(",")));
            billDTO.setList(userBillMapper.getUserBillList(wrapperT));

        }

        return billDTOList;
    }

    @Override
    public double getBrokerage(long uid) {
        return userBillMapper.sumPrice(uid);
    }

    @Override
    public double yesterdayCommissionSum(long uid) {
        return userBillMapper.sumYesterdayPrice(uid);
    }

    @Override
    public List<UserBillQueryVo> userBillList(long uid, int page, int limit, String category) {
        QueryWrapper<UserBill> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1).eq("uid", uid).eq("category", category).orderByDesc("add_time");
        Page<UserBill> pageModel = new Page<>(page, limit);

        IPage<UserBill> pageList = userBillMapper.selectPage(pageModel, wrapper);
        return biillMap.toDto(pageList.getRecords());
    }

    @Override
    public UserBillQueryVo getUserBillById(Serializable id) throws Exception {
        return userBillMapper.getUserBillById(id);
    }

    @Override
    public Paging<UserBillQueryVo> getUserBillPageList(UserBillQueryParam userBillQueryParam) throws Exception {
        Page page = setPageParam(userBillQueryParam, OrderItem.desc("add_time"));
        IPage<UserBillQueryVo> iPage = userBillMapper.getUserBillPageList(page, userBillQueryParam);
        return new Paging(iPage);
    }

}
