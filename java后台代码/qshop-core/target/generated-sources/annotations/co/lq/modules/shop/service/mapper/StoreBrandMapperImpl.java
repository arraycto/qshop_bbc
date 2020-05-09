package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.domain.StoreBrand;
import co.lq.modules.shop.service.dto.StoreBrandDTO;
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
public class StoreBrandMapperImpl implements StoreBrandMapper {

    @Override
    public StoreBrand toEntity(StoreBrandDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreBrand storeBrand = new StoreBrand();

        storeBrand.setId( dto.getId() );
        storeBrand.setName( dto.getName() );
        storeBrand.setFirstLetter( dto.getFirstLetter() );
        storeBrand.setSort( dto.getSort() );
        storeBrand.setFactory( dto.getFactory() );
        storeBrand.setShowStatus( dto.getShowStatus() );
        storeBrand.setProductCount( dto.getProductCount() );
        storeBrand.setLogo( dto.getLogo() );
        storeBrand.setBigPic( dto.getBigPic() );
        storeBrand.setBrandStory( dto.getBrandStory() );
        storeBrand.setAddTime( dto.getAddTime() );
        storeBrand.setModifyTime( dto.getModifyTime() );
        storeBrand.setDeleted( dto.getDeleted() );

        return storeBrand;
    }

    @Override
    public List<StoreBrand> toEntity(List<StoreBrandDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreBrand> list = new ArrayList<StoreBrand>( dtoList.size() );
        for ( StoreBrandDTO storeBrandDTO : dtoList ) {
            list.add( toEntity( storeBrandDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreBrandDTO> toDto(List<StoreBrand> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreBrandDTO> list = new ArrayList<StoreBrandDTO>( entityList.size() );
        for ( StoreBrand storeBrand : entityList ) {
            list.add( toDto( storeBrand ) );
        }

        return list;
    }

    @Override
    public StoreBrandDTO toDto(StoreBrand storeBrand) {
        if ( storeBrand == null ) {
            return null;
        }

        StoreBrandDTO storeBrandDTO = new StoreBrandDTO();

        String name = storeBrandShopName( storeBrand );
        if ( name != null ) {
            storeBrandDTO.setShopName( name );
        }
        storeBrandDTO.setId( storeBrand.getId() );
        storeBrandDTO.setName( storeBrand.getName() );
        storeBrandDTO.setFirstLetter( storeBrand.getFirstLetter() );
        storeBrandDTO.setSort( storeBrand.getSort() );
        storeBrandDTO.setFactory( storeBrand.getFactory() );
        storeBrandDTO.setShowStatus( storeBrand.getShowStatus() );
        storeBrandDTO.setProductCount( storeBrand.getProductCount() );
        storeBrandDTO.setLogo( storeBrand.getLogo() );
        storeBrandDTO.setBigPic( storeBrand.getBigPic() );
        storeBrandDTO.setBrandStory( storeBrand.getBrandStory() );
        storeBrandDTO.setAddTime( storeBrand.getAddTime() );
        storeBrandDTO.setModifyTime( storeBrand.getModifyTime() );
        storeBrandDTO.setDeleted( storeBrand.getDeleted() );

        return storeBrandDTO;
    }

    private String storeBrandShopName(StoreBrand storeBrand) {
        if ( storeBrand == null ) {
            return null;
        }
        Shop shop = storeBrand.getShop();
        if ( shop == null ) {
            return null;
        }
        String name = shop.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
