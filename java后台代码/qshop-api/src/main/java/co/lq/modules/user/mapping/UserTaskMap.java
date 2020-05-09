package co.lq.modules.user.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.user.entity.UserTask;
import co.lq.modules.user.web.vo.UserTaskQueryVo;

/**
 * @author billy
 * @date 2019-12-7
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTaskMap extends EntityMapper<UserTaskQueryVo, UserTask> {

}
