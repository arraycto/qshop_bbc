package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.service.dto.StoreProductDTO;

/**
 * @author billy
 * @date 2019-10-04
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreProductMapper extends EntityMapper<StoreProductDTO, StoreProduct> {

    /**
     * 转换
     *
     * @param storeProduct 原始数据
     * @return /
     */
    @Override
    @Mappings({ @Mapping(source = "storeProduct.brand.name", target = "brandName"),
            @Mapping(source = "storeProduct.brand.id", target = "brandId"),
            @Mapping(source = "storeProduct.storeCategory.id", target = "storeCateId"),
            @Mapping(source = "storeProduct.storeCategory.cateName", target = "storeCateName") })
    StoreProductDTO toDto(StoreProduct storeProduct);
}
