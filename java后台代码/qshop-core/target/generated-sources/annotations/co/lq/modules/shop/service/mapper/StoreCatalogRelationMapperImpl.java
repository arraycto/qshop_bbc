package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreCatalogRelation;
import co.lq.modules.shop.service.dto.StoreCatalogRelationDTO;
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
public class StoreCatalogRelationMapperImpl implements StoreCatalogRelationMapper {

    @Override
    public StoreCatalogRelation toEntity(StoreCatalogRelationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCatalogRelation storeCatalogRelation = new StoreCatalogRelation();

        storeCatalogRelation.setId( dto.getId() );
        storeCatalogRelation.setCatalogId( dto.getCatalogId() );
        storeCatalogRelation.setStoreId( dto.getStoreId() );
        storeCatalogRelation.setAddTime( dto.getAddTime() );
        storeCatalogRelation.setModifyTime( dto.getModifyTime() );
        storeCatalogRelation.setDeleted( dto.getDeleted() );

        return storeCatalogRelation;
    }

    @Override
    public StoreCatalogRelationDTO toDto(StoreCatalogRelation entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCatalogRelationDTO storeCatalogRelationDTO = new StoreCatalogRelationDTO();

        storeCatalogRelationDTO.setId( entity.getId() );
        storeCatalogRelationDTO.setCatalogId( entity.getCatalogId() );
        storeCatalogRelationDTO.setStoreId( entity.getStoreId() );
        storeCatalogRelationDTO.setAddTime( entity.getAddTime() );
        storeCatalogRelationDTO.setModifyTime( entity.getModifyTime() );
        storeCatalogRelationDTO.setDeleted( entity.getDeleted() );

        return storeCatalogRelationDTO;
    }

    @Override
    public List<StoreCatalogRelation> toEntity(List<StoreCatalogRelationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCatalogRelation> list = new ArrayList<StoreCatalogRelation>( dtoList.size() );
        for ( StoreCatalogRelationDTO storeCatalogRelationDTO : dtoList ) {
            list.add( toEntity( storeCatalogRelationDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCatalogRelationDTO> toDto(List<StoreCatalogRelation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCatalogRelationDTO> list = new ArrayList<StoreCatalogRelationDTO>( entityList.size() );
        for ( StoreCatalogRelation storeCatalogRelation : entityList ) {
            list.add( toDto( storeCatalogRelation ) );
        }

        return list;
    }
}
