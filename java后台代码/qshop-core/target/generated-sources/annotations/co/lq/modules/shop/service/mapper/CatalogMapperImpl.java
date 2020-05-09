package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.service.dto.CatalogDTO;
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
public class CatalogMapperImpl implements CatalogMapper {

    @Override
    public Catalog toEntity(CatalogDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Catalog catalog = new Catalog();

        catalog.setId( dto.getId() );
        catalog.setName( dto.getName() );
        catalog.setKeywords( dto.getKeywords() );
        catalog.setDescs( dto.getDescs() );
        catalog.setPid( dto.getPid() );
        catalog.setIconUrl( dto.getIconUrl() );
        catalog.setPicUrl( dto.getPicUrl() );
        catalog.setLevel( dto.getLevel() );
        catalog.setSortOrder( dto.getSortOrder() );
        catalog.setCreateTime( dto.getCreateTime() );
        catalog.setModifyTime( dto.getModifyTime() );
        catalog.setDeleted( dto.getDeleted() );
        List<Long> list = dto.getCatalogIdList();
        if ( list != null ) {
            catalog.setCatalogIdList( new ArrayList<Long>( list ) );
        }
        else {
            catalog.setCatalogIdList( null );
        }

        return catalog;
    }

    @Override
    public CatalogDTO toDto(Catalog entity) {
        if ( entity == null ) {
            return null;
        }

        CatalogDTO catalogDTO = new CatalogDTO();

        catalogDTO.setId( entity.getId() );
        catalogDTO.setName( entity.getName() );
        catalogDTO.setKeywords( entity.getKeywords() );
        catalogDTO.setDescs( entity.getDescs() );
        catalogDTO.setPid( entity.getPid() );
        catalogDTO.setIconUrl( entity.getIconUrl() );
        catalogDTO.setPicUrl( entity.getPicUrl() );
        catalogDTO.setLevel( entity.getLevel() );
        catalogDTO.setSortOrder( entity.getSortOrder() );
        catalogDTO.setCreateTime( entity.getCreateTime() );
        catalogDTO.setModifyTime( entity.getModifyTime() );
        catalogDTO.setDeleted( entity.getDeleted() );
        List<Long> list = entity.getCatalogIdList();
        if ( list != null ) {
            catalogDTO.setCatalogIdList( new ArrayList<Long>( list ) );
        }
        else {
            catalogDTO.setCatalogIdList( null );
        }

        return catalogDTO;
    }

    @Override
    public List<Catalog> toEntity(List<CatalogDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Catalog> list = new ArrayList<Catalog>( dtoList.size() );
        for ( CatalogDTO catalogDTO : dtoList ) {
            list.add( toEntity( catalogDTO ) );
        }

        return list;
    }

    @Override
    public List<CatalogDTO> toDto(List<Catalog> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CatalogDTO> list = new ArrayList<CatalogDTO>( entityList.size() );
        for ( Catalog catalog : entityList ) {
            list.add( toDto( catalog ) );
        }

        return list;
    }
}
