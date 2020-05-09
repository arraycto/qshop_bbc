package co.lq.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.system.domain.SellerJob;
import co.lq.modules.system.service.dto.SellerJobDTO;

/**
 * @author billy
 * @date 2019-03-29
 */
@Mapper(componentModel = "spring", uses = { SellerDeptMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SellerJobMapper extends BaseMapper<SellerJobDTO, SellerJob> {

    /**
     * 转Dto
     *
     * @param sellerJob 原始数据
     * @param deptSuperiorName /
     * @return /
     */
    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    SellerJobDTO toDto(SellerJob sellerJob, String deptSuperiorName);
}
