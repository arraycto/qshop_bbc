package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CmsSubject;
import co.lq.modules.shop.service.dto.CmsSubjectDTO;
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
public class CmsSubjectMapperImpl implements CmsSubjectMapper {

    @Override
    public CmsSubject toEntity(CmsSubjectDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CmsSubject cmsSubject = new CmsSubject();

        cmsSubject.setId( dto.getId() );
        cmsSubject.setCategoryId( dto.getCategoryId() );
        cmsSubject.setTitle( dto.getTitle() );
        cmsSubject.setPic( dto.getPic() );
        cmsSubject.setProductCount( dto.getProductCount() );
        cmsSubject.setRecommendStatus( dto.getRecommendStatus() );
        cmsSubject.setCollectCount( dto.getCollectCount() );
        cmsSubject.setReadCount( dto.getReadCount() );
        cmsSubject.setCommentCount( dto.getCommentCount() );
        cmsSubject.setAlbumPics( dto.getAlbumPics() );
        cmsSubject.setDescription( dto.getDescription() );
        cmsSubject.setShowStatus( dto.getShowStatus() );
        cmsSubject.setContent( dto.getContent() );
        cmsSubject.setForwardCount( dto.getForwardCount() );
        cmsSubject.setCategoryName( dto.getCategoryName() );
        cmsSubject.setMemberId( dto.getMemberId() );
        cmsSubject.setReward( dto.getReward() );
        cmsSubject.setAreaName( dto.getAreaName() );
        cmsSubject.setSchoolName( dto.getSchoolName() );
        cmsSubject.setMemberName( dto.getMemberName() );
        cmsSubject.setVideoSrc( dto.getVideoSrc() );
        cmsSubject.setType( dto.getType() );
        cmsSubject.setAddTime( dto.getAddTime() );
        cmsSubject.setModifyTime( dto.getModifyTime() );
        cmsSubject.setDeleted( dto.getDeleted() );

        return cmsSubject;
    }

    @Override
    public CmsSubjectDTO toDto(CmsSubject entity) {
        if ( entity == null ) {
            return null;
        }

        CmsSubjectDTO cmsSubjectDTO = new CmsSubjectDTO();

        cmsSubjectDTO.setId( entity.getId() );
        cmsSubjectDTO.setCategoryId( entity.getCategoryId() );
        cmsSubjectDTO.setTitle( entity.getTitle() );
        cmsSubjectDTO.setPic( entity.getPic() );
        cmsSubjectDTO.setProductCount( entity.getProductCount() );
        cmsSubjectDTO.setRecommendStatus( entity.getRecommendStatus() );
        cmsSubjectDTO.setCollectCount( entity.getCollectCount() );
        cmsSubjectDTO.setReadCount( entity.getReadCount() );
        cmsSubjectDTO.setCommentCount( entity.getCommentCount() );
        cmsSubjectDTO.setAlbumPics( entity.getAlbumPics() );
        cmsSubjectDTO.setDescription( entity.getDescription() );
        cmsSubjectDTO.setShowStatus( entity.getShowStatus() );
        cmsSubjectDTO.setContent( entity.getContent() );
        cmsSubjectDTO.setForwardCount( entity.getForwardCount() );
        cmsSubjectDTO.setCategoryName( entity.getCategoryName() );
        cmsSubjectDTO.setMemberId( entity.getMemberId() );
        cmsSubjectDTO.setReward( entity.getReward() );
        cmsSubjectDTO.setAreaName( entity.getAreaName() );
        cmsSubjectDTO.setSchoolName( entity.getSchoolName() );
        cmsSubjectDTO.setMemberName( entity.getMemberName() );
        cmsSubjectDTO.setVideoSrc( entity.getVideoSrc() );
        cmsSubjectDTO.setType( entity.getType() );
        cmsSubjectDTO.setAddTime( entity.getAddTime() );
        cmsSubjectDTO.setModifyTime( entity.getModifyTime() );
        cmsSubjectDTO.setDeleted( entity.getDeleted() );

        return cmsSubjectDTO;
    }

    @Override
    public List<CmsSubject> toEntity(List<CmsSubjectDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CmsSubject> list = new ArrayList<CmsSubject>( dtoList.size() );
        for ( CmsSubjectDTO cmsSubjectDTO : dtoList ) {
            list.add( toEntity( cmsSubjectDTO ) );
        }

        return list;
    }

    @Override
    public List<CmsSubjectDTO> toDto(List<CmsSubject> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CmsSubjectDTO> list = new ArrayList<CmsSubjectDTO>( entityList.size() );
        for ( CmsSubject cmsSubject : entityList ) {
            list.add( toDto( cmsSubject ) );
        }

        return list;
    }
}
