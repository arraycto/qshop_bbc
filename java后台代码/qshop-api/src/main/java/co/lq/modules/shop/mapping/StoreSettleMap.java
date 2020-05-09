package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.StoreSettle;
import co.lq.modules.shop.web.vo.StoreSettleQueryVo;

/**
 * 店铺入驻信息
 *
 * @author songbin
 * @since 2020年3月31日 下午2:15:05
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreSettleMap extends EntityMapper<StoreSettleQueryVo, StoreSettle> {

}
