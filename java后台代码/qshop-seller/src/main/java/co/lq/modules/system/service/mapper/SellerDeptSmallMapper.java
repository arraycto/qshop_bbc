package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerDept;
import co.lq.modules.system.service.dto.SellerDeptSmallDTO;

/**
 * @author billy
 * @date 2019-03-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerDeptSmallMapper extends BaseMapper<SellerDeptSmallDTO, SellerDept> {

}
