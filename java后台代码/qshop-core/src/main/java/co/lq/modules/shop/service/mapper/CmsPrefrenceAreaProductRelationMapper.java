package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.CmsPrefrenceAreaProductRelation;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaProductRelationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-04-11
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CmsPrefrenceAreaProductRelationMapper extends BaseMapper<CmsPrefrenceAreaProductRelationDTO, CmsPrefrenceAreaProductRelation> {

}