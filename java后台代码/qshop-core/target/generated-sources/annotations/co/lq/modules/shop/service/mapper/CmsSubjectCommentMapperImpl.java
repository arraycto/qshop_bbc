package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CmsSubjectComment;
import co.lq.modules.shop.service.dto.CmsSubjectCommentDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-09T22:21:13+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_40 (Oracle Corporation)"
)
@Component
public class CmsSubjectCommentMapperImpl implements CmsSubjectCommentMapper {

    @Override
    public CmsSubjectComment toEntity(CmsSubjectCommentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CmsSubjectComment cmsSubjectComment = new CmsSubjectComment();

        cmsSubjectComment.setId( dto.getId() );
        cmsSubjectComment.setSubjectId( dto.getSubjectId() );
        cmsSubjectComment.setMemberNickName( dto.getMemberNickName() );
        cmsSubjectComment.setMemberIcon( dto.getMemberIcon() );
        cmsSubjectComment.setContent( dto.getContent() );
        cmsSubjectComment.setCreateTime( dto.getCreateTime() );
        cmsSubjectComment.setShowStatus( dto.getShowStatus() );
        cmsSubjectComment.setAddTime( dto.getAddTime() );
        cmsSubjectComment.setModifyTime( dto.getModifyTime() );
        cmsSubjectComment.setDeleted( dto.getDeleted() );

        return cmsSubjectComment;
    }

    @Override
    public CmsSubjectCommentDTO toDto(CmsSubjectComment entity) {
        if ( entity == null ) {
            return null;
        }

        CmsSubjectCommentDTO cmsSubjectCommentDTO = new CmsSubjectCommentDTO();

        cmsSubjectCommentDTO.setId( entity.getId() );
        cmsSubjectCommentDTO.setSubjectId( entity.getSubjectId() );
        cmsSubjectCommentDTO.setMemberNickName( entity.getMemberNickName() );
        cmsSubjectCommentDTO.setMemberIcon( entity.getMemberIcon() );
        cmsSubjectCommentDTO.setContent( entity.getContent() );
        cmsSubjectCommentDTO.setCreateTime( entity.getCreateTime() );
        cmsSubjectCommentDTO.setShowStatus( entity.getShowStatus() );
        cmsSubjectCommentDTO.setAddTime( entity.getAddTime() );
        cmsSubjectCommentDTO.setModifyTime( entity.getModifyTime() );
        cmsSubjectCommentDTO.setDeleted( entity.getDeleted() );

        return cmsSubjectCommentDTO;
    }

    @Override
    public List<CmsSubjectComment> toEntity(List<CmsSubjectCommentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CmsSubjectComment> list = new ArrayList<CmsSubjectComment>( dtoList.size() );
        for ( CmsSubjectCommentDTO cmsSubjectCommentDTO : dtoList ) {
            list.add( toEntity( cmsSubjectCommentDTO ) );
        }

        return list;
    }

    @Override
    public List<CmsSubjectCommentDTO> toDto(List<CmsSubjectComment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CmsSubjectCommentDTO> list = new ArrayList<CmsSubjectCommentDTO>( entityList.size() );
        for ( CmsSubjectComment cmsSubjectComment : entityList ) {
            list.add( toDto( cmsSubjectComment ) );
        }

        return list;
    }
}
