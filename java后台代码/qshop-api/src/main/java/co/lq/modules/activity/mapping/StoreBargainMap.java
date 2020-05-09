package co.lq.modules.activity.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.entity.StoreBargain;
import co.lq.modules.activity.web.vo.StoreBargainQueryVo;

/**
 * @author billy
 * @date 2019-12-21
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreBargainMap extends EntityMapper<StoreBargainQueryVo, StoreBargain> {

}
