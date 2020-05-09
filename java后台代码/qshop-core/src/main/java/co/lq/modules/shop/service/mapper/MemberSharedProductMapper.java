package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.MemberSharedProduct;
import co.lq.modules.shop.service.dto.MemberSharedProductDTO;

/**
 * @author billy
 * @date 2020-03-11
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberSharedProductMapper extends BaseMapper<MemberSharedProductDTO, MemberSharedProduct> {

}
