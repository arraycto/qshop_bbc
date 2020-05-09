package co.lq.modules.activity.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.entity.StorePink;
import co.lq.modules.activity.web.vo.StorePinkQueryVo;

/**
 * @author billy
 * @date 2019-10-19
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StorePinkMap extends EntityMapper<StorePinkQueryVo, StorePink> {

}
