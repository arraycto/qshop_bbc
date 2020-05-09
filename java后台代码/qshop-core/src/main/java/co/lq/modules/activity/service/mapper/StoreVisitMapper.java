package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreVisit;
import co.lq.modules.activity.service.dto.StoreVisitDTO;

/**
 * @author billy
 * @date 2019-11-18
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreVisitMapper extends EntityMapper<StoreVisitDTO, StoreVisit> {

}
