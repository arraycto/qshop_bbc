package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreOrderReturnReason;
import co.lq.modules.shop.service.dto.StoreOrderReturnReasonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-03-29
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreOrderReturnReasonMapper extends BaseMapper<StoreOrderReturnReasonDTO, StoreOrderReturnReason> {

}