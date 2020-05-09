package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.UserExtract;
import co.lq.modules.activity.service.dto.UserExtractDTO;

/**
 * @author billy
 * @date 2019-11-14
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserExtractMapper extends EntityMapper<UserExtractDTO, UserExtract> {

}
