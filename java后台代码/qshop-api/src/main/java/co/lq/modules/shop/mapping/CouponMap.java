package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.StoreCouponUser;
import co.lq.modules.shop.web.vo.StoreCouponUserQueryVo;

/**
 * @author billy
 * @date 2019-10-26
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponMap extends EntityMapper<StoreCouponUserQueryVo, StoreCouponUser> {

}
