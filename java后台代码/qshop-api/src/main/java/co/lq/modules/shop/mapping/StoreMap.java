package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.Store;
import co.lq.utils.StoreDTO;

/**
 * 店铺
 *
 * @author songbin
 * @since 2020年3月12日 下午9:22:44
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreMap extends EntityMapper<StoreDTO, Store> {

}
