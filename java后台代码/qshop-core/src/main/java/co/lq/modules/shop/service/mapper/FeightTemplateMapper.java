package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.FeightTemplate;
import co.lq.modules.shop.service.dto.FeightTemplateDTO;

/**
 * @author billy
 * @date 2020-03-27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeightTemplateMapper extends BaseMapper<FeightTemplateDTO, FeightTemplate> {

    /**
     * 转换
     *
     * @param feightTemplate 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "feightTemplate.shop.name", target = "shopName")
    FeightTemplateDTO toDto(FeightTemplate feightTemplate);
}
