package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.CmsSubject;
import co.lq.modules.shop.service.dto.CmsSubjectDTO;

/**
 * @author billy
 * @date 2020-03-27
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CmsSubjectMapper extends BaseMapper<CmsSubjectDTO, CmsSubject> {

}
