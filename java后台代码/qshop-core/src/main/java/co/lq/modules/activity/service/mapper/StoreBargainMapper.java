package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreBargain;
import co.lq.modules.activity.service.dto.StoreBargainDTO;

/**
 * @author billy
 * @date 2019-12-22
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreBargainMapper extends EntityMapper<StoreBargainDTO, StoreBargain> {

}
