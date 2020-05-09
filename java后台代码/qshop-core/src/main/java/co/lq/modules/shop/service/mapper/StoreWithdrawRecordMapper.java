package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreWithdrawRecord;
import co.lq.modules.shop.service.dto.StoreWithdrawRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-04-13
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreWithdrawRecordMapper extends BaseMapper<StoreWithdrawRecordDTO, StoreWithdrawRecord> {

}