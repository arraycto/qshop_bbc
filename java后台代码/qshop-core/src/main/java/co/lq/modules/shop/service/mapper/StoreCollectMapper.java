package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreCollect;
import co.lq.modules.shop.service.dto.StoreCollectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-04-05
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreCollectMapper extends BaseMapper<StoreCollectDTO, StoreCollect> {

}