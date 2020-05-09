package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.OrderRefund;
import co.lq.modules.shop.service.dto.OrderRefundDTO;

/**
* @author billy
* @date 2020-03-29
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderRefundMapper extends BaseMapper<OrderRefundDTO, OrderRefund> {

    /**
     * 转换
     *
     * @param orderRefund 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "orderRefund.shop.name", target = "shopName")
    OrderRefundDTO toDto(OrderRefund orderRefund);
}