package co.lq.modules.shop.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import co.lq.modules.shop.domain.UserBill;

/**
 * 访问数据库user_bill
 *
 * @author songbin
 * @since 2020年3月19日 下午6:02:47
 */
@Repository
public interface SellerUserBillMapper extends BaseMapper<UserBill> {
}
