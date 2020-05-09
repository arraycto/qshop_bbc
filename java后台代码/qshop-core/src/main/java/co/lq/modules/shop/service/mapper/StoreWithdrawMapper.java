package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreWithdraw;
import co.lq.modules.shop.service.dto.StoreWithdrawDTO;

/**
 * @author billy
 * @date 2020-04-13
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreWithdrawMapper extends BaseMapper<StoreWithdrawDTO, StoreWithdraw> {

    /**
     * 转换
     *
     * @param StoreWithdraw 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "StoreWithdraw.shop.name", target = "shopName")
    StoreWithdrawDTO toDto(StoreWithdraw StoreWithdraw);
}
