package co.lq.mp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.mp.domain.Cache;
import co.lq.mp.service.dto.CacheDTO;

/**
 * @author billy
 * @date 2019-10-06
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CacheMapper extends EntityMapper<CacheDTO, Cache> {

}
