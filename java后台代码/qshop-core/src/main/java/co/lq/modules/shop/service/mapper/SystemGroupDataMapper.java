package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.SystemGroupData;
import co.lq.modules.shop.service.dto.SystemGroupDataDTO;

/**
 * @author billy
 * @date 2019-10-18
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemGroupDataMapper extends EntityMapper<SystemGroupDataDTO, SystemGroupData> {

}
