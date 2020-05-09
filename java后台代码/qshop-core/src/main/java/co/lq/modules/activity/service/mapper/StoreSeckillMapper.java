package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreSeckill;
import co.lq.modules.activity.service.dto.StoreSeckillDTO;

/**
 * @author billy
 * @date 2019-12-14
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreSeckillMapper extends EntityMapper<StoreSeckillDTO, StoreSeckill> {

}
