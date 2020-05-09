package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreBargainUser;
import co.lq.modules.activity.service.dto.StoreBargainUserDTO;

/**
 * 砍价
 *
 * @author songbin
 * @since 2020年3月22日 下午3:57:02
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreBargainUserMapper extends EntityMapper<StoreBargainUserDTO, StoreBargainUser> {
}
