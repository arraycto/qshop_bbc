package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.PlatformUser;
import co.lq.modules.system.service.dto.PlatformUserDTO;

/**
 * @author billy
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = { RoleMapper.class, DeptMapper.class,
        JobMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlatformUserMapper extends BaseMapper<PlatformUserDTO, PlatformUser> {

    /**
     * 转换
     *
     * @param platformUser 原始数据
     * @return /
     */
    @Override
    @Mapping(source = "platformUser.platformUserAvatar.realName", target = "avatar")
    PlatformUserDTO toDto(PlatformUser platformUser);
}
