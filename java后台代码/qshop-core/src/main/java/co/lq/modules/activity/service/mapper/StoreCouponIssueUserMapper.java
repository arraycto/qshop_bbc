package co.lq.modules.activity.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.activity.domain.StoreCouponIssueUser;
import co.lq.modules.activity.service.dto.StoreCouponIssueUserDTO;

/**
 * @author billy
 * @date 2019-11-09
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreCouponIssueUserMapper extends EntityMapper<StoreCouponIssueUserDTO, StoreCouponIssueUser> {

}
