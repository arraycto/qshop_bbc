package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.SystemStore;
import co.lq.modules.shop.web.vo.SystemStoreQueryVo;

/**
 * @author billy
 * @date 2020-03-04
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemStoreMap extends EntityMapper<SystemStoreQueryVo, SystemStore> {

}
