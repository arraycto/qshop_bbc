package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerUser;
import co.lq.modules.system.service.dto.SellerDTO;

/**
 * @author billy
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = { SellerRoleMapper.class, SellerDeptMapper.class,
        SellerJobMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerMapper extends BaseMapper<SellerDTO, SellerUser> {

    /**
     * 转换
     *
     * @param sellerUser 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "sellerUser.sellerUserAvatar.realName", target = "avatar")
    SellerDTO toDto(SellerUser sellerUser);
}
