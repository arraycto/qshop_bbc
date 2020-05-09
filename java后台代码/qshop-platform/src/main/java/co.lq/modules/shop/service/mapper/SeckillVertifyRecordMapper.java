package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.SeckillVertifyRecord;
import co.lq.modules.shop.service.dto.SeckillVertifyRecordDTO;

/**
 * @author billy
 * @date 2020-03-11
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeckillVertifyRecordMapper extends BaseMapper<SeckillVertifyRecordDTO, SeckillVertifyRecord> {

}
