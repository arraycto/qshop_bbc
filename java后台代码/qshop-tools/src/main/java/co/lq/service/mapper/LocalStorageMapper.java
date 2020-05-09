package co.lq.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.domain.LocalStorage;
import co.lq.service.dto.LocalStorageDTO;

/**
 * @author billy
 * @date 2019-09-05
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends BaseMapper<LocalStorageDTO, LocalStorage> {

}
