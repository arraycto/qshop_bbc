package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CmsSubjectProductRelation;
import co.lq.modules.shop.service.dto.CmsSubjectProductRelationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-09T22:21:12+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_40 (Oracle Corporation)"
)
@Component
public class CmsSubjectProductRelationMapperImpl implements CmsSubjectProductRelationMapper {

    @Override
    public CmsSubjectProductRelation toEntity(CmsSubjectProductRelationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CmsSubjectProductRelation cmsSubjectProductRelation = new CmsSubjectProductRelation();

        cmsSubjectProductRelation.setId( dto.getId() );
        cmsSubjectProductRelation.setSubjectId( dto.getSubjectId() );
        cmsSubjectProductRelation.setProductId( dto.getProductId() );
        cmsSubjectProductRelation.setStoreId( dto.getStoreId() );
        cmsSubjectProductRelation.setAddTime( dto.getAddTime() );
        cmsSubjectProductRelation.setModifyTime( dto.getModifyTime() );
        cmsSubjectProductRelation.setDeleted( dto.getDeleted() );

        return cmsSubjectProductRelation;
    }

    @Override
    public CmsSubjectProductRelationDTO toDto(CmsSubjectProductRelation entity) {
        if ( entity == null ) {
            return null;
        }

        CmsSubjectProductRelationDTO cmsSubjectProductRelationDTO = new CmsSubjectProductRelationDTO();

        cmsSubjectProductRelationDTO.setId( entity.getId() );
        cmsSubjectProductRelationDTO.setSubjectId( entity.getSubjectId() );
        cmsSubjectProductRelationDTO.setProductId( entity.getProductId() );
        cmsSubjectProductRelationDTO.setStoreId( entity.getStoreId() );
        cmsSubjectProductRelationDTO.setAddTime( entity.getAddTime() );
        cmsSubjectProductRelationDTO.setModifyTime( entity.getModifyTime() );
        cmsSubjectProductRelationDTO.setDeleted( entity.getDeleted() );

        return cmsSubjectProductRelationDTO;
    }

    @Override
    public List<CmsSubjectProductRelation> toEntity(List<CmsSubjectProductRelationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CmsSubjectProductRelation> list = new ArrayList<CmsSubjectProductRelation>( dtoList.size() );
        for ( CmsSubjectProductRelationDTO cmsSubjectProductRelationDTO : dtoList ) {
            list.add( toEntity( cmsSubjectProductRelationDTO ) );
        }

        return list;
    }

    @Override
    public List<CmsSubjectProductRelationDTO> toDto(List<CmsSubjectProductRelation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CmsSubjectProductRelationDTO> list = new ArrayList<CmsSubjectProductRelationDTO>( entityList.size() );
        for ( CmsSubjectProductRelation cmsSubjectProductRelation : entityList ) {
            list.add( toDto( cmsSubjectProductRelation ) );
        }

        return list;
    }
}
