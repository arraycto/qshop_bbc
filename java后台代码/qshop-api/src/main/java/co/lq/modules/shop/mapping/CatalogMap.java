package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.Catalog;
import co.lq.utils.CatalogDTO;

/**
 * 平台类目
 *
 * @author songbin
 * @since 2020年3月11日 下午9:05:30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogMap extends EntityMapper<CatalogDTO, Catalog> {

}
