package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.UserRecharge;
import co.lq.modules.shop.service.dto.UserRechargeDto;
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
public class UserRechargeMapperImpl implements UserRechargeMapper {

    @Override
    public UserRecharge toEntity(UserRechargeDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserRecharge userRecharge = new UserRecharge();

        userRecharge.setId( dto.getId() );
        userRecharge.setUid( dto.getUid() );
        userRecharge.setOrderId( dto.getOrderId() );
        userRecharge.setPrice( dto.getPrice() );
        userRecharge.setRechargeType( dto.getRechargeType() );
        userRecharge.setPaid( dto.getPaid() );
        userRecharge.setPayTime( dto.getPayTime() );
        userRecharge.setAddTime( dto.getAddTime() );
        userRecharge.setRefundPrice( dto.getRefundPrice() );
        userRecharge.setNickname( dto.getNickname() );
        userRecharge.setStoreId( dto.getStoreId() );

        return userRecharge;
    }

    @Override
    public UserRechargeDto toDto(UserRecharge entity) {
        if ( entity == null ) {
            return null;
        }

        UserRechargeDto userRechargeDto = new UserRechargeDto();

        userRechargeDto.setId( entity.getId() );
        userRechargeDto.setUid( entity.getUid() );
        userRechargeDto.setOrderId( entity.getOrderId() );
        userRechargeDto.setPrice( entity.getPrice() );
        userRechargeDto.setRechargeType( entity.getRechargeType() );
        userRechargeDto.setPaid( entity.getPaid() );
        userRechargeDto.setPayTime( entity.getPayTime() );
        userRechargeDto.setAddTime( entity.getAddTime() );
        userRechargeDto.setRefundPrice( entity.getRefundPrice() );
        userRechargeDto.setNickname( entity.getNickname() );
        userRechargeDto.setStoreId( entity.getStoreId() );

        return userRechargeDto;
    }

    @Override
    public List<UserRecharge> toEntity(List<UserRechargeDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserRecharge> list = new ArrayList<UserRecharge>( dtoList.size() );
        for ( UserRechargeDto userRechargeDto : dtoList ) {
            list.add( toEntity( userRechargeDto ) );
        }

        return list;
    }

    @Override
    public List<UserRechargeDto> toDto(List<UserRecharge> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserRechargeDto> list = new ArrayList<UserRechargeDto>( entityList.size() );
        for ( UserRecharge userRecharge : entityList ) {
            list.add( toDto( userRecharge ) );
        }

        return list;
    }
}
