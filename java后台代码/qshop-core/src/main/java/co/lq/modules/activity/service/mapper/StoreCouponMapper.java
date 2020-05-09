package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreCoupon;
import co.lq.modules.activity.service.dto.StoreCouponDTO;

/**
 * @author billy
 * @date 2019-11-09
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreCouponMapper extends EntityMapper<StoreCouponDTO, StoreCoupon> {

}
