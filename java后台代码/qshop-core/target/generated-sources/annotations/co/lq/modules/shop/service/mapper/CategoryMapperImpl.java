package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Category;
import co.lq.modules.shop.service.dto.CategoryDTO;
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
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( dto.getId() );
        category.setPid( dto.getPid() );
        category.setCateName( dto.getCateName() );
        category.setSort( dto.getSort() );
        category.setPic( dto.getPic() );
        category.setIsShow( dto.getIsShow() );
        category.setAddTime( dto.getAddTime() );
        category.setCatalogId( dto.getCatalogId() );
        category.setCommissionRate( dto.getCommissionRate() );

        return category;
    }

    @Override
    public CategoryDTO toDto(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setPid( entity.getPid() );
        categoryDTO.setCateName( entity.getCateName() );
        categoryDTO.setSort( entity.getSort() );
        categoryDTO.setPic( entity.getPic() );
        categoryDTO.setIsShow( entity.getIsShow() );
        categoryDTO.setAddTime( entity.getAddTime() );
        categoryDTO.setCatalogId( entity.getCatalogId() );
        categoryDTO.setCommissionRate( entity.getCommissionRate() );

        return categoryDTO;
    }

    @Override
    public List<Category> toEntity(List<CategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( dtoList.size() );
        for ( CategoryDTO categoryDTO : dtoList ) {
            list.add( toEntity( categoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryDTO> toDto(List<Category> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( entityList.size() );
        for ( Category category : entityList ) {
            list.add( toDto( category ) );
        }

        return list;
    }
}
