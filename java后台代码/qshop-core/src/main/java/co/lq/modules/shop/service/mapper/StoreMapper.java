package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.dto.ShopDTO;

/**
 * @author billy
 * @date 2020-03-10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreMapper extends BaseMapper<ShopDTO, Shop> {

}
