package co.lq.modules.monitor.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.monitor.domain.Server;
import co.lq.modules.monitor.service.dto.ServerDTO;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerMapper extends BaseMapper<ServerDTO, Server> {

}
