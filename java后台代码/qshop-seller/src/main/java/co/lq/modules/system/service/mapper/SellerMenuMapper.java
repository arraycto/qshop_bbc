package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerMenu;
import co.lq.modules.system.service.dto.SellerMenuDTO;

/**
 * @author billy
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerMenuMapper extends BaseMapper<SellerMenuDTO, SellerMenu> {

}
