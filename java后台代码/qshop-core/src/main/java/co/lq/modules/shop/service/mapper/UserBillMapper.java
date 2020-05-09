package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.UserBill;
import co.lq.modules.shop.service.dto.UserBillDTO;

/**
 * @author billy
 * @date 2019-11-06
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserBillMapper extends EntityMapper<UserBillDTO, UserBill> {

}
