package co.lq.modules.mnt.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.mnt.domain.Deploy;
import co.lq.modules.mnt.service.dto.DeployDto;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Mapper(componentModel = "spring", uses = { AppMapper.class,
        ServerDeployMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeployMapper extends BaseMapper<DeployDto, Deploy> {

}
