package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerRole;
import co.lq.modules.system.service.dto.SellerRoleSmallDTO;

/**
 * @author billy
 * @date 2019-5-23
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerRoleSmallMapper extends BaseMapper<SellerRoleSmallDTO, SellerRole> {

}
