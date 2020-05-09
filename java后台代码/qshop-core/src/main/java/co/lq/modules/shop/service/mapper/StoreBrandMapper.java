package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreBrand;
import co.lq.modules.shop.service.dto.StoreBrandDTO;

/**
 * @author billy
 * @date 2020-03-27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreBrandMapper extends BaseMapper<StoreBrandDTO, StoreBrand> {

    /**
     * 转换
     *
     * @param storeBrand 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "storeBrand.shop.name", target = "shopName")
    StoreBrandDTO toDto(StoreBrand storeBrand);
}
