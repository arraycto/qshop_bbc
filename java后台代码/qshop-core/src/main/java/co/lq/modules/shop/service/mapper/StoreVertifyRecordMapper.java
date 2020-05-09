package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreVertifyRecord;
import co.lq.modules.shop.service.dto.StoreVertifyRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-03-28
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreVertifyRecordMapper extends BaseMapper<StoreVertifyRecordDTO, StoreVertifyRecord> {

}