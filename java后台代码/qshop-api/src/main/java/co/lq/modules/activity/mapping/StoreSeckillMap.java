package co.lq.modules.activity.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.entity.StoreSeckill;
import co.lq.modules.activity.web.vo.StoreSeckillQueryVo;

/**
 * @author billy
 * @date 2019-12-17
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreSeckillMap extends EntityMapper<StoreSeckillQueryVo, StoreSeckill> {

}
