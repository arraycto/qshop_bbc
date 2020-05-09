package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Brand;
import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.service.dto.BrandDTO;
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
public class BrandMapperImpl implements BrandMapper {

    @Override
    public Brand toEntity(BrandDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Brand brand = new Brand();

        brand.setId( dto.getId() );
        brand.setName( dto.getName() );
        if ( dto.getCatalogId() != null ) {
            brand.setCatalogId( String.valueOf( dto.getCatalogId() ) );
        }
        brand.setFirstLetter( dto.getFirstLetter() );
        brand.setSort( dto.getSort() );
        brand.setFactory( dto.getFactory() );
        brand.setShowStatus( dto.getShowStatus() );
        brand.setProductCount( dto.getProductCount() );
        brand.setLogo( dto.getLogo() );
        brand.setBigPic( dto.getBigPic() );
        brand.setBrandStory( dto.getBrandStory() );
        brand.setAddTime( dto.getAddTime() );
        brand.setModifyTime( dto.getModifyTime() );
        brand.setDeleted( dto.getDeleted() );

        return brand;
    }

    @Override
    public List<Brand> toEntity(List<BrandDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Brand> list = new ArrayList<Brand>( dtoList.size() );
        for ( BrandDTO brandDTO : dtoList ) {
            list.add( toEntity( brandDTO ) );
        }

        return list;
    }

    @Override
    public List<BrandDTO> toDto(List<Brand> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BrandDTO> list = new ArrayList<BrandDTO>( entityList.size() );
        for ( Brand brand : entityList ) {
            list.add( toDto( brand ) );
        }

        return list;
    }

    @Override
    public BrandDTO toDto(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandDTO brandDTO = new BrandDTO();

        String name = brandCatalogName( brand );
        if ( name != null ) {
            brandDTO.setCatalogName( name );
        }
        brandDTO.setId( brand.getId() );
        brandDTO.setName( brand.getName() );
        if ( brand.getCatalogId() != null ) {
            brandDTO.setCatalogId( Long.parseLong( brand.getCatalogId() ) );
        }
        brandDTO.setFirstLetter( brand.getFirstLetter() );
        brandDTO.setSort( brand.getSort() );
        brandDTO.setFactory( brand.getFactory() );
        brandDTO.setShowStatus( brand.getShowStatus() );
        brandDTO.setProductCount( brand.getProductCount() );
        brandDTO.setLogo( brand.getLogo() );
        brandDTO.setBigPic( brand.getBigPic() );
        brandDTO.setBrandStory( brand.getBrandStory() );
        brandDTO.setAddTime( brand.getAddTime() );
        brandDTO.setModifyTime( brand.getModifyTime() );
        brandDTO.setDeleted( brand.getDeleted() );

        return brandDTO;
    }

    private String brandCatalogName(Brand brand) {
        if ( brand == null ) {
            return null;
        }
        Catalog catalog = brand.getCatalog();
        if ( catalog == null ) {
            return null;
        }
        String name = catalog.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
