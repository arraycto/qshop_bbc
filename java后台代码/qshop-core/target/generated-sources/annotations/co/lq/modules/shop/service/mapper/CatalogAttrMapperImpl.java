package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CatalogAttr;
import co.lq.modules.shop.service.dto.CatalogAttrDTO;
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
public class CatalogAttrMapperImpl implements CatalogAttrMapper {

    @Override
    public CatalogAttr toEntity(CatalogAttrDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CatalogAttr catalogAttr = new CatalogAttr();

        catalogAttr.setId( dto.getId() );
        catalogAttr.setAttrName( dto.getAttrName() );
        catalogAttr.setIsSpec( dto.getIsSpec() );
        catalogAttr.setPic( dto.getPic() );
        catalogAttr.setIsShow( dto.getIsShow() );
        catalogAttr.setSelectType( dto.getSelectType() );
        catalogAttr.setInputType( dto.getInputType() );
        catalogAttr.setInputList( dto.getInputList() );
        catalogAttr.setSort( dto.getSort() );
        catalogAttr.setFilterType( dto.getFilterType() );
        catalogAttr.setSearchType( dto.getSearchType() );
        catalogAttr.setRelatedStatus( dto.getRelatedStatus() );
        catalogAttr.setHandAddStatus( dto.getHandAddStatus() );
        catalogAttr.setAddTime( dto.getAddTime() );
        catalogAttr.setModifyTime( dto.getModifyTime() );
        catalogAttr.setDeleted( dto.getDeleted() );
        catalogAttr.setCatalogId( dto.getCatalogId() );

        return catalogAttr;
    }

    @Override
    public CatalogAttrDTO toDto(CatalogAttr entity) {
        if ( entity == null ) {
            return null;
        }

        CatalogAttrDTO catalogAttrDTO = new CatalogAttrDTO();

        catalogAttrDTO.setId( entity.getId() );
        catalogAttrDTO.setAttrName( entity.getAttrName() );
        catalogAttrDTO.setSelectType( entity.getSelectType() );
        catalogAttrDTO.setInputType( entity.getInputType() );
        catalogAttrDTO.setInputList( entity.getInputList() );
        catalogAttrDTO.setSort( entity.getSort() );
        catalogAttrDTO.setFilterType( entity.getFilterType() );
        catalogAttrDTO.setSearchType( entity.getSearchType() );
        catalogAttrDTO.setRelatedStatus( entity.getRelatedStatus() );
        catalogAttrDTO.setHandAddStatus( entity.getHandAddStatus() );
        catalogAttrDTO.setIsSpec( entity.getIsSpec() );
        catalogAttrDTO.setPic( entity.getPic() );
        catalogAttrDTO.setIsShow( entity.getIsShow() );
        catalogAttrDTO.setAddTime( entity.getAddTime() );
        catalogAttrDTO.setModifyTime( entity.getModifyTime() );
        catalogAttrDTO.setDeleted( entity.getDeleted() );
        catalogAttrDTO.setCatalogId( entity.getCatalogId() );

        return catalogAttrDTO;
    }

    @Override
    public List<CatalogAttr> toEntity(List<CatalogAttrDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CatalogAttr> list = new ArrayList<CatalogAttr>( dtoList.size() );
        for ( CatalogAttrDTO catalogAttrDTO : dtoList ) {
            list.add( toEntity( catalogAttrDTO ) );
        }

        return list;
    }

    @Override
    public List<CatalogAttrDTO> toDto(List<CatalogAttr> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CatalogAttrDTO> list = new ArrayList<CatalogAttrDTO>( entityList.size() );
        for ( CatalogAttr catalogAttr : entityList ) {
            list.add( toDto( catalogAttr ) );
        }

        return list;
    }
}
