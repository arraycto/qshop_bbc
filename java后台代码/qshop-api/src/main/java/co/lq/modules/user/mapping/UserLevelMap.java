package co.lq.modules.user.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.user.entity.UserLevel;
import co.lq.modules.user.web.vo.UserLevelQueryVo;

/**
 * @author billy
 * @date 2019-12-7
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLevelMap extends EntityMapper<UserLevelQueryVo, UserLevel> {

}
