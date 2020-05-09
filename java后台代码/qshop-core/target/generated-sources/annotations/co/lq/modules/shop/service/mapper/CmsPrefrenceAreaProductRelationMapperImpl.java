package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CmsPrefrenceAreaProductRelation;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaProductRelationDTO;
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
public class CmsPrefrenceAreaProductRelationMapperImpl implements CmsPrefrenceAreaProductRelationMapper {

    @Override
    public CmsPrefrenceAreaProductRelation toEntity(CmsPrefrenceAreaProductRelationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CmsPrefrenceAreaProductRelation cmsPrefrenceAreaProductRelation = new CmsPrefrenceAreaProductRelation();

        cmsPrefrenceAreaProductRelation.setId( dto.getId() );
        cmsPrefrenceAreaProductRelation.setPrefrenceAreaId( dto.getPrefrenceAreaId() );
        cmsPrefrenceAreaProductRelation.setProductId( dto.getProductId() );
        cmsPrefrenceAreaProductRelation.setAddTime( dto.getAddTime() );
        cmsPrefrenceAreaProductRelation.setModifyTime( dto.getModifyTime() );
        cmsPrefrenceAreaProductRelation.setDeleted( dto.getDeleted() );

        return cmsPrefrenceAreaProductRelation;
    }

    @Override
    public CmsPrefrenceAreaProductRelationDTO toDto(CmsPrefrenceAreaProductRelation entity) {
        if ( entity == null ) {
            return null;
        }

        CmsPrefrenceAreaProductRelationDTO cmsPrefrenceAreaProductRelationDTO = new CmsPrefrenceAreaProductRelationDTO();

        cmsPrefrenceAreaProductRelationDTO.setId( entity.getId() );
        cmsPrefrenceAreaProductRelationDTO.setPrefrenceAreaId( entity.getPrefrenceAreaId() );
        cmsPrefrenceAreaProductRelationDTO.setProductId( entity.getProductId() );
        cmsPrefrenceAreaProductRelationDTO.setAddTime( entity.getAddTime() );
        cmsPrefrenceAreaProductRelationDTO.setModifyTime( entity.getModifyTime() );
        cmsPrefrenceAreaProductRelationDTO.setDeleted( entity.getDeleted() );

        return cmsPrefrenceAreaProductRelationDTO;
    }

    @Override
    public List<CmsPrefrenceAreaProductRelation> toEntity(List<CmsPrefrenceAreaProductRelationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CmsPrefrenceAreaProductRelation> list = new ArrayList<CmsPrefrenceAreaProductRelation>( dtoList.size() );
        for ( CmsPrefrenceAreaProductRelationDTO cmsPrefrenceAreaProductRelationDTO : dtoList ) {
            list.add( toEntity( cmsPrefrenceAreaProductRelationDTO ) );
        }

        return list;
    }

    @Override
    public List<CmsPrefrenceAreaProductRelationDTO> toDto(List<CmsPrefrenceAreaProductRelation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CmsPrefrenceAreaProductRelationDTO> list = new ArrayList<CmsPrefrenceAreaProductRelationDTO>( entityList.size() );
        for ( CmsPrefrenceAreaProductRelation cmsPrefrenceAreaProductRelation : entityList ) {
            list.add( toDto( cmsPrefrenceAreaProductRelation ) );
        }

        return list;
    }
}
