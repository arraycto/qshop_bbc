package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.HomeBrand;
import co.lq.modules.shop.service.dto.HomeBrandDTO;
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
public class HomeBrandMapperImpl implements HomeBrandMapper {

    @Override
    public HomeBrand toEntity(HomeBrandDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HomeBrand homeBrand = new HomeBrand();

        homeBrand.setId( dto.getId() );
        homeBrand.setBrandId( dto.getBrandId() );
        homeBrand.setBrandName( dto.getBrandName() );
        homeBrand.setRecommendStatus( dto.getRecommendStatus() );
        homeBrand.setSort( dto.getSort() );
        homeBrand.setStoreId( dto.getStoreId() );
        homeBrand.setAddTime( dto.getAddTime() );
        homeBrand.setModifyTime( dto.getModifyTime() );
        homeBrand.setDeleted( dto.getDeleted() );

        return homeBrand;
    }

    @Override
    public HomeBrandDTO toDto(HomeBrand entity) {
        if ( entity == null ) {
            return null;
        }

        HomeBrandDTO homeBrandDTO = new HomeBrandDTO();

        homeBrandDTO.setId( entity.getId() );
        homeBrandDTO.setBrandId( entity.getBrandId() );
        homeBrandDTO.setBrandName( entity.getBrandName() );
        homeBrandDTO.setRecommendStatus( entity.getRecommendStatus() );
        homeBrandDTO.setSort( entity.getSort() );
        homeBrandDTO.setStoreId( entity.getStoreId() );
        homeBrandDTO.setAddTime( entity.getAddTime() );
        homeBrandDTO.setModifyTime( entity.getModifyTime() );
        homeBrandDTO.setDeleted( entity.getDeleted() );

        return homeBrandDTO;
    }

    @Override
    public List<HomeBrand> toEntity(List<HomeBrandDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HomeBrand> list = new ArrayList<HomeBrand>( dtoList.size() );
        for ( HomeBrandDTO homeBrandDTO : dtoList ) {
            list.add( toEntity( homeBrandDTO ) );
        }

        return list;
    }

    @Override
    public List<HomeBrandDTO> toDto(List<HomeBrand> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HomeBrandDTO> list = new ArrayList<HomeBrandDTO>( entityList.size() );
        for ( HomeBrand homeBrand : entityList ) {
            list.add( toDto( homeBrand ) );
        }

        return list;
    }
}
