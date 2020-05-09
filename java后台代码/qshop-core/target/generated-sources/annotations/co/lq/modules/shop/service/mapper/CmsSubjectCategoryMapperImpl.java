package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CmsSubjectCategory;
import co.lq.modules.shop.service.dto.CmsSubjectCategoryDTO;
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
public class CmsSubjectCategoryMapperImpl implements CmsSubjectCategoryMapper {

    @Override
    public CmsSubjectCategory toEntity(CmsSubjectCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CmsSubjectCategory cmsSubjectCategory = new CmsSubjectCategory();

        cmsSubjectCategory.setId( dto.getId() );
        cmsSubjectCategory.setName( dto.getName() );
        cmsSubjectCategory.setIcon( dto.getIcon() );
        cmsSubjectCategory.setSubjectCount( dto.getSubjectCount() );
        cmsSubjectCategory.setShowStatus( dto.getShowStatus() );
        cmsSubjectCategory.setSort( dto.getSort() );
        cmsSubjectCategory.setAddTime( dto.getAddTime() );
        cmsSubjectCategory.setModifyTime( dto.getModifyTime() );
        cmsSubjectCategory.setDeleted( dto.getDeleted() );

        return cmsSubjectCategory;
    }

    @Override
    public CmsSubjectCategoryDTO toDto(CmsSubjectCategory entity) {
        if ( entity == null ) {
            return null;
        }

        CmsSubjectCategoryDTO cmsSubjectCategoryDTO = new CmsSubjectCategoryDTO();

        cmsSubjectCategoryDTO.setId( entity.getId() );
        cmsSubjectCategoryDTO.setName( entity.getName() );
        cmsSubjectCategoryDTO.setIcon( entity.getIcon() );
        cmsSubjectCategoryDTO.setSubjectCount( entity.getSubjectCount() );
        cmsSubjectCategoryDTO.setShowStatus( entity.getShowStatus() );
        cmsSubjectCategoryDTO.setSort( entity.getSort() );
        cmsSubjectCategoryDTO.setAddTime( entity.getAddTime() );
        cmsSubjectCategoryDTO.setModifyTime( entity.getModifyTime() );
        cmsSubjectCategoryDTO.setDeleted( entity.getDeleted() );

        return cmsSubjectCategoryDTO;
    }

    @Override
    public List<CmsSubjectCategory> toEntity(List<CmsSubjectCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CmsSubjectCategory> list = new ArrayList<CmsSubjectCategory>( dtoList.size() );
        for ( CmsSubjectCategoryDTO cmsSubjectCategoryDTO : dtoList ) {
            list.add( toEntity( cmsSubjectCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CmsSubjectCategoryDTO> toDto(List<CmsSubjectCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CmsSubjectCategoryDTO> list = new ArrayList<CmsSubjectCategoryDTO>( entityList.size() );
        for ( CmsSubjectCategory cmsSubjectCategory : entityList ) {
            list.add( toDto( cmsSubjectCategory ) );
        }

        return list;
    }
}
