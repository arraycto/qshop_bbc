package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.PlatformRole;
import co.lq.modules.system.service.dto.PlatformRoleDTO;

/**
 * @author billy
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = { MenuMapper.class,
        DeptMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<PlatformRoleDTO, PlatformRole> {

}
