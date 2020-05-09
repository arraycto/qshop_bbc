package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.dto.UserDTO;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUid( dto.getUid() );
        user.setAccount( dto.getAccount() );
        user.setPwd( dto.getPwd() );
        user.setRealName( dto.getRealName() );
        user.setBirthday( dto.getBirthday() );
        user.setCardId( dto.getCardId() );
        user.setMark( dto.getMark() );
        user.setPartnerId( dto.getPartnerId() );
        user.setNickname( dto.getNickname() );
        user.setAvatar( dto.getAvatar() );
        user.setPhone( dto.getPhone() );
        user.setAddTime( dto.getAddTime() );
        user.setAddIp( dto.getAddIp() );
        user.setLastTime( dto.getLastTime() );
        user.setLastIp( dto.getLastIp() );
        user.setNowMoney( dto.getNowMoney() );
        user.setBrokeragePrice( dto.getBrokeragePrice() );
        user.setIntegral( dto.getIntegral() );
        user.setSignNum( dto.getSignNum() );
        user.setStatus( dto.getStatus() );
        if ( dto.getLevel() != null ) {
            user.setLevel( dto.getLevel().longValue() );
        }
        user.setSpreadUid( dto.getSpreadUid() );
        user.setSpreadTime( dto.getSpreadTime() );
        user.setUserType( dto.getUserType() );
        user.setIsPromoter( dto.getIsPromoter() );
        user.setPayCount( dto.getPayCount() );
        user.setSpreadCount( dto.getSpreadCount() );
        user.setCleanTime( dto.getCleanTime() );
        user.setAddres( dto.getAddres() );
        user.setAdminid( dto.getAdminid() );
        user.setLoginType( dto.getLoginType() );

        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUid( entity.getUid() );
        userDTO.setAccount( entity.getAccount() );
        userDTO.setPwd( entity.getPwd() );
        userDTO.setRealName( entity.getRealName() );
        userDTO.setBirthday( entity.getBirthday() );
        userDTO.setCardId( entity.getCardId() );
        userDTO.setMark( entity.getMark() );
        userDTO.setPartnerId( entity.getPartnerId() );
        userDTO.setNickname( entity.getNickname() );
        userDTO.setAvatar( entity.getAvatar() );
        userDTO.setPhone( entity.getPhone() );
        userDTO.setAddTime( entity.getAddTime() );
        userDTO.setAddIp( entity.getAddIp() );
        userDTO.setLastTime( entity.getLastTime() );
        userDTO.setLastIp( entity.getLastIp() );
        userDTO.setNowMoney( entity.getNowMoney() );
        userDTO.setBrokeragePrice( entity.getBrokeragePrice() );
        userDTO.setIntegral( entity.getIntegral() );
        userDTO.setSignNum( entity.getSignNum() );
        userDTO.setStatus( entity.getStatus() );
        if ( entity.getLevel() != null ) {
            userDTO.setLevel( entity.getLevel().intValue() );
        }
        userDTO.setSpreadUid( entity.getSpreadUid() );
        userDTO.setSpreadTime( entity.getSpreadTime() );
        userDTO.setUserType( entity.getUserType() );
        userDTO.setIsPromoter( entity.getIsPromoter() );
        userDTO.setPayCount( entity.getPayCount() );
        userDTO.setSpreadCount( entity.getSpreadCount() );
        userDTO.setCleanTime( entity.getCleanTime() );
        userDTO.setAddres( entity.getAddres() );
        userDTO.setAdminid( entity.getAdminid() );
        userDTO.setLoginType( entity.getLoginType() );

        return userDTO;
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDTO userDTO : dtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }
}
