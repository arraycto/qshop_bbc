package co.lq.modules.mnt.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.mnt.domain.App;
import co.lq.modules.mnt.service.dto.AppDto;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppMapper extends BaseMapper<AppDto, App> {

}
