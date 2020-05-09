package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.CmsPrefrenceArea;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-04-11
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CmsPrefrenceAreaMapper extends BaseMapper<CmsPrefrenceAreaDTO, CmsPrefrenceArea> {

}