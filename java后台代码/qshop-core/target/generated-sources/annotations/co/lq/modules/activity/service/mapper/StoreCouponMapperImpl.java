package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreCoupon;
import co.lq.modules.activity.service.dto.StoreCouponDTO;
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
public class StoreCouponMapperImpl implements StoreCouponMapper {

    @Override
    public StoreCoupon toEntity(StoreCouponDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCoupon storeCoupon = new StoreCoupon();

        storeCoupon.setId( dto.getId() );
        storeCoupon.setTitle( dto.getTitle() );
        storeCoupon.setIntegral( dto.getIntegral() );
        storeCoupon.setCouponPrice( dto.getCouponPrice() );
        storeCoupon.setUseMinPrice( dto.getUseMinPrice() );
        storeCoupon.setCouponTime( dto.getCouponTime() );
        storeCoupon.setSort( dto.getSort() );
        storeCoupon.setStatus( dto.getStatus() );
        storeCoupon.setAddTime( dto.getAddTime() );
        storeCoupon.setIsDel( dto.getIsDel() );
        storeCoupon.setStoreId( dto.getStoreId() );

        return storeCoupon;
    }

    @Override
    public StoreCouponDTO toDto(StoreCoupon entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCouponDTO storeCouponDTO = new StoreCouponDTO();

        storeCouponDTO.setId( entity.getId() );
        storeCouponDTO.setTitle( entity.getTitle() );
        storeCouponDTO.setIntegral( entity.getIntegral() );
        storeCouponDTO.setCouponPrice( entity.getCouponPrice() );
        storeCouponDTO.setUseMinPrice( entity.getUseMinPrice() );
        storeCouponDTO.setCouponTime( entity.getCouponTime() );
        storeCouponDTO.setSort( entity.getSort() );
        storeCouponDTO.setStatus( entity.getStatus() );
        storeCouponDTO.setAddTime( entity.getAddTime() );
        storeCouponDTO.setIsDel( entity.getIsDel() );
        storeCouponDTO.setStoreId( entity.getStoreId() );

        return storeCouponDTO;
    }

    @Override
    public List<StoreCoupon> toEntity(List<StoreCouponDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCoupon> list = new ArrayList<StoreCoupon>( dtoList.size() );
        for ( StoreCouponDTO storeCouponDTO : dtoList ) {
            list.add( toEntity( storeCouponDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCouponDTO> toDto(List<StoreCoupon> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCouponDTO> list = new ArrayList<StoreCouponDTO>( entityList.size() );
        for ( StoreCoupon storeCoupon : entityList ) {
            list.add( toDto( storeCoupon ) );
        }

        return list;
    }
}
