package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreCategoryAttr;
import co.lq.modules.shop.service.dto.StoreCategoryAttrDTO;
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
public class StoreCategoryAttrMapperImpl implements StoreCategoryAttrMapper {

    @Override
    public StoreCategoryAttr toEntity(StoreCategoryAttrDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCategoryAttr storeCategoryAttr = new StoreCategoryAttr();

        storeCategoryAttr.setId( dto.getId() );
        storeCategoryAttr.setStoreId( dto.getStoreId() );
        storeCategoryAttr.setCategory( dto.getCategory() );
        storeCategoryAttr.setAttrName( dto.getAttrName() );
        storeCategoryAttr.setAttrValue( dto.getAttrValue() );
        storeCategoryAttr.setIsShow( dto.getIsShow() );
        storeCategoryAttr.setAddTime( dto.getAddTime() );
        storeCategoryAttr.setModifyTime( dto.getModifyTime() );
        storeCategoryAttr.setDeleted( dto.getDeleted() );

        return storeCategoryAttr;
    }

    @Override
    public StoreCategoryAttrDTO toDto(StoreCategoryAttr entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCategoryAttrDTO storeCategoryAttrDTO = new StoreCategoryAttrDTO();

        storeCategoryAttrDTO.setId( entity.getId() );
        storeCategoryAttrDTO.setStoreId( entity.getStoreId() );
        storeCategoryAttrDTO.setCategory( entity.getCategory() );
        storeCategoryAttrDTO.setAttrName( entity.getAttrName() );
        storeCategoryAttrDTO.setAttrValue( entity.getAttrValue() );
        storeCategoryAttrDTO.setIsShow( entity.getIsShow() );
        storeCategoryAttrDTO.setAddTime( entity.getAddTime() );
        storeCategoryAttrDTO.setModifyTime( entity.getModifyTime() );
        storeCategoryAttrDTO.setDeleted( entity.getDeleted() );

        return storeCategoryAttrDTO;
    }

    @Override
    public List<StoreCategoryAttr> toEntity(List<StoreCategoryAttrDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCategoryAttr> list = new ArrayList<StoreCategoryAttr>( dtoList.size() );
        for ( StoreCategoryAttrDTO storeCategoryAttrDTO : dtoList ) {
            list.add( toEntity( storeCategoryAttrDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCategoryAttrDTO> toDto(List<StoreCategoryAttr> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCategoryAttrDTO> list = new ArrayList<StoreCategoryAttrDTO>( entityList.size() );
        for ( StoreCategoryAttr storeCategoryAttr : entityList ) {
            list.add( toDto( storeCategoryAttr ) );
        }

        return list;
    }
}
