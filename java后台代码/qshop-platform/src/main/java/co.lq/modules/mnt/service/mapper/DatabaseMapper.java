package co.lq.modules.mnt.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.mnt.domain.Database;
import co.lq.modules.mnt.service.dto.DatabaseDto;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DatabaseMapper extends BaseMapper<DatabaseDto, Database> {

}
