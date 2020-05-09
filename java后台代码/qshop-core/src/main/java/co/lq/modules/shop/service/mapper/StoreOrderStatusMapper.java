package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.StoreOrderStatus;
import co.lq.modules.shop.service.dto.StoreOrderStatusDTO;

/**
 * @author billy
 * @date 2019-11-02
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreOrderStatusMapper extends EntityMapper<StoreOrderStatusDTO, StoreOrderStatus> {

}
