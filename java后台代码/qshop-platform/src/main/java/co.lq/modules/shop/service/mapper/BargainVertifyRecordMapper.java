package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.BargainVertifyRecord;
import co.lq.modules.shop.service.dto.BargainVertifyRecordDTO;

/**
 * @author billy
 * @date 2020-03-11
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BargainVertifyRecordMapper extends BaseMapper<BargainVertifyRecordDTO, BargainVertifyRecord> {

}
