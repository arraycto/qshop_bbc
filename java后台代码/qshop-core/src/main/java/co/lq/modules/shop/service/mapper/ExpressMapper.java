package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.Express;
import co.lq.modules.shop.service.dto.ExpressDTO;

/**
 * @author billy
 * @date 2019-12-12
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpressMapper extends EntityMapper<ExpressDTO, Express> {

}
