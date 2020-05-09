package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.HomeNewProduct;
import co.lq.modules.shop.service.dto.HomeNewProductDTO;
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
public class HomeNewProductMapperImpl implements HomeNewProductMapper {

    @Override
    public HomeNewProduct toEntity(HomeNewProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HomeNewProduct homeNewProduct = new HomeNewProduct();

        homeNewProduct.setId( dto.getId() );
        homeNewProduct.setProductId( dto.getProductId() );
        homeNewProduct.setProductName( dto.getProductName() );
        homeNewProduct.setRecommendStatus( dto.getRecommendStatus() );
        homeNewProduct.setSort( dto.getSort() );
        homeNewProduct.setAddTime( dto.getAddTime() );
        homeNewProduct.setModifyTime( dto.getModifyTime() );
        homeNewProduct.setDeleted( dto.getDeleted() );
        homeNewProduct.setStoreId( dto.getStoreId() );

        return homeNewProduct;
    }

    @Override
    public HomeNewProductDTO toDto(HomeNewProduct entity) {
        if ( entity == null ) {
            return null;
        }

        HomeNewProductDTO homeNewProductDTO = new HomeNewProductDTO();

        homeNewProductDTO.setId( entity.getId() );
        homeNewProductDTO.setProductId( entity.getProductId() );
        homeNewProductDTO.setProductName( entity.getProductName() );
        homeNewProductDTO.setRecommendStatus( entity.getRecommendStatus() );
        homeNewProductDTO.setSort( entity.getSort() );
        homeNewProductDTO.setAddTime( entity.getAddTime() );
        homeNewProductDTO.setModifyTime( entity.getModifyTime() );
        homeNewProductDTO.setDeleted( entity.getDeleted() );
        homeNewProductDTO.setStoreId( entity.getStoreId() );

        return homeNewProductDTO;
    }

    @Override
    public List<HomeNewProduct> toEntity(List<HomeNewProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HomeNewProduct> list = new ArrayList<HomeNewProduct>( dtoList.size() );
        for ( HomeNewProductDTO homeNewProductDTO : dtoList ) {
            list.add( toEntity( homeNewProductDTO ) );
        }

        return list;
    }

    @Override
    public List<HomeNewProductDTO> toDto(List<HomeNewProduct> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HomeNewProductDTO> list = new ArrayList<HomeNewProductDTO>( entityList.size() );
        for ( HomeNewProduct homeNewProduct : entityList ) {
            list.add( toDto( homeNewProduct ) );
        }

        return list;
    }
}
