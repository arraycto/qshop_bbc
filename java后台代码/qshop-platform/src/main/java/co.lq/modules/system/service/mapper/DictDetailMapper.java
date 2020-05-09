package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.DictDetail;
import co.lq.modules.system.service.dto.DictDetailDTO;

/**
 * @author billy
 * @date 2019-04-10
 */
@Mapper(componentModel = "spring", uses = { DictSmallMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends BaseMapper<DictDetailDTO, DictDetail> {

}
