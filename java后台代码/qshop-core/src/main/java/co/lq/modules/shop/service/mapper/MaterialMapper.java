package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.Material;
import co.lq.modules.shop.service.dto.MaterialDto;

/**
 * @author billy
 * @date 2020-01-09
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MaterialMapper extends BaseMapper<MaterialDto, Material> {

}
