package co.lq.modules.mnt.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.mnt.domain.DeployHistory;
import co.lq.modules.mnt.service.dto.DeployHistoryDto;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeployHistoryMapper extends BaseMapper<DeployHistoryDto, DeployHistory> {

}
