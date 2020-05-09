package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreGiftsCategory;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-03-27
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreGiftsCategoryMapper extends BaseMapper<StoreGiftsCategoryDTO, StoreGiftsCategory> {

}