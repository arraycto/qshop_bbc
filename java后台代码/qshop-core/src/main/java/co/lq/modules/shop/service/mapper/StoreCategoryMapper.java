package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreCategory;
import co.lq.modules.shop.service.dto.StoreCategoryDTO;

/**
 * @author billy
 * @date 2020-03-10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreCategoryMapper extends BaseMapper<StoreCategoryDTO, StoreCategory> {

}
