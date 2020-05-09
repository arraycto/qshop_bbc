package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CategoryAttr;
import co.lq.modules.shop.service.dto.CategoryAttrDTO;
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
public class CategoryAttrMapperImpl implements CategoryAttrMapper {

    @Override
    public CategoryAttr toEntity(CategoryAttrDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CategoryAttr categoryAttr = new CategoryAttr();

        categoryAttr.setId( dto.getId() );
        categoryAttr.setCategory( dto.getCategory() );
        categoryAttr.setAttrName( dto.getAttrName() );
        categoryAttr.setAttrValue( dto.getAttrValue() );
        categoryAttr.setIsShow( dto.getIsShow() );
        categoryAttr.setAddTime( dto.getAddTime() );
        categoryAttr.setModifyTime( dto.getModifyTime() );
        categoryAttr.setDeleted( dto.getDeleted() );

        return categoryAttr;
    }

    @Override
    public CategoryAttrDTO toDto(CategoryAttr entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryAttrDTO categoryAttrDTO = new CategoryAttrDTO();

        categoryAttrDTO.setId( entity.getId() );
        categoryAttrDTO.setCategory( entity.getCategory() );
        categoryAttrDTO.setAttrName( entity.getAttrName() );
        categoryAttrDTO.setAttrValue( entity.getAttrValue() );
        categoryAttrDTO.setIsShow( entity.getIsShow() );
        categoryAttrDTO.setAddTime( entity.getAddTime() );
        categoryAttrDTO.setModifyTime( entity.getModifyTime() );
        categoryAttrDTO.setDeleted( entity.getDeleted() );

        return categoryAttrDTO;
    }

    @Override
    public List<CategoryAttr> toEntity(List<CategoryAttrDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CategoryAttr> list = new ArrayList<CategoryAttr>( dtoList.size() );
        for ( CategoryAttrDTO categoryAttrDTO : dtoList ) {
            list.add( toEntity( categoryAttrDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryAttrDTO> toDto(List<CategoryAttr> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryAttrDTO> list = new ArrayList<CategoryAttrDTO>( entityList.size() );
        for ( CategoryAttr categoryAttr : entityList ) {
            list.add( toDto( categoryAttr ) );
        }

        return list;
    }
}
