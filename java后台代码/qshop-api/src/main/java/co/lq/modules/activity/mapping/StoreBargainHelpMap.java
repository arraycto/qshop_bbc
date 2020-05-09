package co.lq.modules.activity.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.web.vo.StoreBargainUserHelpQueryVo;

/**
 * @author billy
 * @date 2019-12-22
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreBargainHelpMap extends EntityMapper<StoreBargainUserHelpQueryVo, StoreBargainUserHelp> {

}
