package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.PlatformJob;
import co.lq.modules.system.service.dto.PlatformJobDTO;

/**
 * @author billy
 * @date 2019-03-29
 */
@Mapper(componentModel = "spring", uses = { DeptMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<PlatformJobDTO, PlatformJob> {

    /**
     * 转Dto
     *
     * @param platformJob 原始数据
     * @param deptSuperiorName /
     * @return /
     */
    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    PlatformJobDTO toDto(PlatformJob platformJob, String deptSuperiorName);
}
