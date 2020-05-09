package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.PointsConsumeSetting;
import co.lq.modules.shop.service.dto.PointsConsumeSettingDTO;
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
public class PointsConsumeSettingMapperImpl implements PointsConsumeSettingMapper {

    @Override
    public PointsConsumeSetting toEntity(PointsConsumeSettingDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PointsConsumeSetting pointsConsumeSetting = new PointsConsumeSetting();

        pointsConsumeSetting.setId( dto.getId() );
        pointsConsumeSetting.setDeductionPerAmount( dto.getDeductionPerAmount() );
        pointsConsumeSetting.setMaxPercentPerOrder( dto.getMaxPercentPerOrder() );
        pointsConsumeSetting.setUseUnit( dto.getUseUnit() );
        pointsConsumeSetting.setCouponStatus( dto.getCouponStatus() );
        pointsConsumeSetting.setLogin( dto.getLogin() );
        pointsConsumeSetting.setRegister( dto.getRegister() );
        pointsConsumeSetting.setSign( dto.getSign() );
        pointsConsumeSetting.setOrders( dto.getOrders() );
        pointsConsumeSetting.setStoreId( dto.getStoreId() );
        pointsConsumeSetting.setAddTime( dto.getAddTime() );
        pointsConsumeSetting.setModifyTime( dto.getModifyTime() );
        pointsConsumeSetting.setDeleted( dto.getDeleted() );

        return pointsConsumeSetting;
    }

    @Override
    public PointsConsumeSettingDTO toDto(PointsConsumeSetting entity) {
        if ( entity == null ) {
            return null;
        }

        PointsConsumeSettingDTO pointsConsumeSettingDTO = new PointsConsumeSettingDTO();

        pointsConsumeSettingDTO.setId( entity.getId() );
        pointsConsumeSettingDTO.setDeductionPerAmount( entity.getDeductionPerAmount() );
        pointsConsumeSettingDTO.setMaxPercentPerOrder( entity.getMaxPercentPerOrder() );
        pointsConsumeSettingDTO.setUseUnit( entity.getUseUnit() );
        pointsConsumeSettingDTO.setCouponStatus( entity.getCouponStatus() );
        pointsConsumeSettingDTO.setLogin( entity.getLogin() );
        pointsConsumeSettingDTO.setRegister( entity.getRegister() );
        pointsConsumeSettingDTO.setSign( entity.getSign() );
        pointsConsumeSettingDTO.setOrders( entity.getOrders() );
        pointsConsumeSettingDTO.setStoreId( entity.getStoreId() );
        pointsConsumeSettingDTO.setAddTime( entity.getAddTime() );
        pointsConsumeSettingDTO.setModifyTime( entity.getModifyTime() );
        pointsConsumeSettingDTO.setDeleted( entity.getDeleted() );

        return pointsConsumeSettingDTO;
    }

    @Override
    public List<PointsConsumeSetting> toEntity(List<PointsConsumeSettingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PointsConsumeSetting> list = new ArrayList<PointsConsumeSetting>( dtoList.size() );
        for ( PointsConsumeSettingDTO pointsConsumeSettingDTO : dtoList ) {
            list.add( toEntity( pointsConsumeSettingDTO ) );
        }

        return list;
    }

    @Override
    public List<PointsConsumeSettingDTO> toDto(List<PointsConsumeSetting> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PointsConsumeSettingDTO> list = new ArrayList<PointsConsumeSettingDTO>( entityList.size() );
        for ( PointsConsumeSetting pointsConsumeSetting : entityList ) {
            list.add( toDto( pointsConsumeSetting ) );
        }

        return list;
    }
}
