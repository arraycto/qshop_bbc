package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.UserRecharge;
import co.lq.modules.shop.service.dto.UserRechargeDto;

/**
 * @author billy
 * @date 2020-03-02
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRechargeMapper extends BaseMapper<UserRechargeDto, UserRecharge> {

}
