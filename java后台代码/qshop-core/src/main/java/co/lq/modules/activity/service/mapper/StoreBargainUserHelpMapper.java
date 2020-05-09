package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreBargainUserHelp;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpDTO;

/**
 * 砍价
 *
 * @author songbin
 * @since 2020年3月22日 下午4:41:07
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreBargainUserHelpMapper extends EntityMapper<StoreBargainUserHelpDTO, StoreBargainUserHelp> {
}
