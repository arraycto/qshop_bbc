package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.service.dto.CatalogDTO;

/**
 * @author billy
 * @date 2020-03-10
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogMapper extends BaseMapper<CatalogDTO, Catalog> {

}
