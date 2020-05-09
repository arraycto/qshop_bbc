package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.CategoryAttr;
import co.lq.modules.shop.service.dto.CategoryAttrDTO;

/**
 * @author billy
 * @date 2020-03-10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryAttrMapper extends BaseMapper<CategoryAttrDTO, CategoryAttr> {

}
