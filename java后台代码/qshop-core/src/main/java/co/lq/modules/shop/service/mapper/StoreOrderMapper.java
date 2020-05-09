package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.service.dto.StoreOrderDTO;

/**
 * @author billy
 * @date 2019-10-14
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreOrderMapper extends EntityMapper<StoreOrderDTO, StoreOrder> {

}
