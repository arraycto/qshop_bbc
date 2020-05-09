package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.SystemUser;
import co.lq.modules.shop.web.vo.SellerUserQueryVo;

/**
 * 卖家帐号
 *
 * @author songbin
 * @since 2020年3月31日 下午3:19:16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerUserMap extends EntityMapper<SellerUserQueryVo, SystemUser> {

}
