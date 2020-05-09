package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreSystemConfig;
import co.lq.modules.shop.service.dto.StoreSystemConfigDTO;

/**
 * @author billy
 * @date 2020-03-11
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreSystemConfigMapper extends BaseMapper<StoreSystemConfigDTO, StoreSystemConfig> {

}
