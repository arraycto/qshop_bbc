package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerJob;
import co.lq.modules.system.service.dto.SellerJobSmallDTO;

/**
 * @author billy
 * @date 2019-03-29
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerJobSmallMapper extends BaseMapper<SellerJobSmallDTO, SellerJob> {

}
