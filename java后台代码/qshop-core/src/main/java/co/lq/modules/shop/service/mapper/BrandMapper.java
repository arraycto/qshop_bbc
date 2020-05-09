package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.Brand;
import co.lq.modules.shop.service.dto.BrandDTO;

/**
 * @author billy
 * @date 2020-03-27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper extends BaseMapper<BrandDTO, Brand> {

    /**
     * 转换
     *
     * @param brand 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "brand.catalog.name", target = "catalogName")
    BrandDTO toDto(Brand brand);
}
