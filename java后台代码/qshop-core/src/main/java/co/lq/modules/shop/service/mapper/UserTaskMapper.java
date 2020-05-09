package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.UserTask;
import co.lq.modules.shop.service.dto.UserTaskDTO;

/**
 * @author billy
 * @date 2019-12-04
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTaskMapper extends EntityMapper<UserTaskDTO, UserTask> {

}
