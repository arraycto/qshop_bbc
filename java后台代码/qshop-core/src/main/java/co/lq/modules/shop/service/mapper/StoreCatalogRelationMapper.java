package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreCatalogRelation;
import co.lq.modules.shop.service.dto.StoreCatalogRelationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-04-23
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreCatalogRelationMapper extends BaseMapper<StoreCatalogRelationDTO, StoreCatalogRelation> {

}