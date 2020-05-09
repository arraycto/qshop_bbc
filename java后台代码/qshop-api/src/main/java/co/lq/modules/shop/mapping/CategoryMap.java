package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.Category;
import co.lq.utils.CateDTO;

/**
 * @author billy
 * @date 2019-10-19
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMap extends EntityMapper<CateDTO, Category> {

}
