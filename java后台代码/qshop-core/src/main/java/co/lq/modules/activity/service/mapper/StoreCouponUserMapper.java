package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreCouponUser;
import co.lq.modules.activity.service.dto.StoreCouponUserDTO;

/**
 * @author billy
 * @date 2019-11-10
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreCouponUserMapper extends EntityMapper<StoreCouponUserDTO, StoreCouponUser> {

}
