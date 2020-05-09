package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.StoreProduct;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;

/**
 * @author billy
 * @date 2019-10-19
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreProductMap extends EntityMapper<StoreProductQueryVo, StoreProduct> {

}
