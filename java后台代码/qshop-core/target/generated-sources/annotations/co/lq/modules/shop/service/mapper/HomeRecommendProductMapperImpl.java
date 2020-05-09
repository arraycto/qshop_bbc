package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.HomeRecommendProduct;
import co.lq.modules.shop.service.dto.HomeRecommendProductDTO;
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
public class HomeRecommendProductMapperImpl implements HomeRecommendProductMapper {

    @Override
    public HomeRecommendProduct toEntity(HomeRecommendProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HomeRecommendProduct homeRecommendProduct = new HomeRecommendProduct();

        homeRecommendProduct.setId( dto.getId() );
        homeRecommendProduct.setProductId( dto.getProductId() );
        homeRecommendProduct.setProductName( dto.getProductName() );
        homeRecommendProduct.setRecommendStatus( dto.getRecommendStatus() );
        homeRecommendProduct.setSort( dto.getSort() );
        homeRecommendProduct.setStoreId( dto.getStoreId() );
        homeRecommendProduct.setAddTime( dto.getAddTime() );
        homeRecommendProduct.setModifyTime( dto.getModifyTime() );
        homeRecommendProduct.setDeleted( dto.getDeleted() );

        return homeRecommendProduct;
    }

    @Override
    public HomeRecommendProductDTO toDto(HomeRecommendProduct entity) {
        if ( entity == null ) {
            return null;
        }

        HomeRecommendProductDTO homeRecommendProductDTO = new HomeRecommendProductDTO();

        homeRecommendProductDTO.setId( entity.getId() );
        homeRecommendProductDTO.setProductId( entity.getProductId() );
        homeRecommendProductDTO.setProductName( entity.getProductName() );
        homeRecommendProductDTO.setRecommendStatus( entity.getRecommendStatus() );
        homeRecommendProductDTO.setSort( entity.getSort() );
        homeRecommendProductDTO.setStoreId( entity.getStoreId() );
        homeRecommendProductDTO.setAddTime( entity.getAddTime() );
        homeRecommendProductDTO.setModifyTime( entity.getModifyTime() );
        homeRecommendProductDTO.setDeleted( entity.getDeleted() );

        return homeRecommendProductDTO;
    }

    @Override
    public List<HomeRecommendProduct> toEntity(List<HomeRecommendProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HomeRecommendProduct> list = new ArrayList<HomeRecommendProduct>( dtoList.size() );
        for ( HomeRecommendProductDTO homeRecommendProductDTO : dtoList ) {
            list.add( toEntity( homeRecommendProductDTO ) );
        }

        return list;
    }

    @Override
    public List<HomeRecommendProductDTO> toDto(List<HomeRecommendProduct> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HomeRecommendProductDTO> list = new ArrayList<HomeRecommendProductDTO>( entityList.size() );
        for ( HomeRecommendProduct homeRecommendProduct : entityList ) {
            list.add( toDto( homeRecommendProduct ) );
        }

        return list;
    }
}
