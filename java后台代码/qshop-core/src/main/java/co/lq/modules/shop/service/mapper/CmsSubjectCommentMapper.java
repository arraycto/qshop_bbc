package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.CmsSubjectComment;
import co.lq.modules.shop.service.dto.CmsSubjectCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-03-27
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CmsSubjectCommentMapper extends BaseMapper<CmsSubjectCommentDTO, CmsSubjectComment> {

}