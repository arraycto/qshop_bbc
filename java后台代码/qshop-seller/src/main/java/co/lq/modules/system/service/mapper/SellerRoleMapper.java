package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerRole;
import co.lq.modules.system.service.dto.SellerRoleDTO;

/**
 * @author billy
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = { SellerMenuMapper.class,
        SellerDeptMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerRoleMapper extends BaseMapper<SellerRoleDTO, SellerRole> {

}
