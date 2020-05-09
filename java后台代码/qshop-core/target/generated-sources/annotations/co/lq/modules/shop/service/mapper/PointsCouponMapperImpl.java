package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.PointsCoupon;
import co.lq.modules.shop.service.dto.PointsCouponDTO;
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
public class PointsCouponMapperImpl implements PointsCouponMapper {

    @Override
    public PointsCoupon toEntity(PointsCouponDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PointsCoupon pointsCoupon = new PointsCoupon();

        pointsCoupon.setId( dto.getId() );
        pointsCoupon.setValidDay( dto.getValidDay() );
        pointsCoupon.setMinIntegration( dto.getMinIntegration() );
        pointsCoupon.setMaxIntegration( dto.getMaxIntegration() );
        pointsCoupon.setRemark( dto.getRemark() );
        pointsCoupon.setCreateTime( dto.getCreateTime() );
        pointsCoupon.setStoreId( dto.getStoreId() );
        pointsCoupon.setAddTime( dto.getAddTime() );
        pointsCoupon.setModifyTime( dto.getModifyTime() );
        pointsCoupon.setDeleted( dto.getDeleted() );
        pointsCoupon.setCount( dto.getCount() );

        return pointsCoupon;
    }

    @Override
    public PointsCouponDTO toDto(PointsCoupon entity) {
        if ( entity == null ) {
            return null;
        }

        PointsCouponDTO pointsCouponDTO = new PointsCouponDTO();

        pointsCouponDTO.setId( entity.getId() );
        pointsCouponDTO.setValidDay( entity.getValidDay() );
        pointsCouponDTO.setMinIntegration( entity.getMinIntegration() );
        pointsCouponDTO.setMaxIntegration( entity.getMaxIntegration() );
        pointsCouponDTO.setRemark( entity.getRemark() );
        pointsCouponDTO.setCreateTime( entity.getCreateTime() );
        pointsCouponDTO.setStoreId( entity.getStoreId() );
        pointsCouponDTO.setAddTime( entity.getAddTime() );
        pointsCouponDTO.setModifyTime( entity.getModifyTime() );
        pointsCouponDTO.setDeleted( entity.getDeleted() );
        pointsCouponDTO.setCount( entity.getCount() );

        return pointsCouponDTO;
    }

    @Override
    public List<PointsCoupon> toEntity(List<PointsCouponDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PointsCoupon> list = new ArrayList<PointsCoupon>( dtoList.size() );
        for ( PointsCouponDTO pointsCouponDTO : dtoList ) {
            list.add( toEntity( pointsCouponDTO ) );
        }

        return list;
    }

    @Override
    public List<PointsCouponDTO> toDto(List<PointsCoupon> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PointsCouponDTO> list = new ArrayList<PointsCouponDTO>( entityList.size() );
        for ( PointsCoupon pointsCoupon : entityList ) {
            list.add( toDto( pointsCoupon ) );
        }

        return list;
    }
}
