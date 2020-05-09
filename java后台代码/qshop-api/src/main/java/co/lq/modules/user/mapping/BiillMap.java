package co.lq.modules.user.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.user.entity.UserBill;
import co.lq.modules.user.web.vo.UserBillQueryVo;

/**
 * @author billy
 * @date 2019-10-26
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BiillMap extends EntityMapper<UserBillQueryVo, UserBill> {

}
