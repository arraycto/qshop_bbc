package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.WechatUser;
import co.lq.modules.shop.service.dto.WechatUserDTO;
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
public class WechatUserMapperImpl implements WechatUserMapper {

    @Override
    public WechatUser toEntity(WechatUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        WechatUser wechatUser = new WechatUser();

        wechatUser.setUid( dto.getUid() );
        wechatUser.setUnionid( dto.getUnionid() );
        wechatUser.setOpenid( dto.getOpenid() );
        wechatUser.setRoutineOpenid( dto.getRoutineOpenid() );
        wechatUser.setNickname( dto.getNickname() );
        wechatUser.setHeadimgurl( dto.getHeadimgurl() );
        wechatUser.setSex( dto.getSex() );
        wechatUser.setCity( dto.getCity() );
        wechatUser.setLanguage( dto.getLanguage() );
        wechatUser.setProvince( dto.getProvince() );
        wechatUser.setCountry( dto.getCountry() );
        wechatUser.setRemark( dto.getRemark() );
        wechatUser.setGroupid( dto.getGroupid() );
        wechatUser.setTagidList( dto.getTagidList() );
        wechatUser.setSubscribe( dto.getSubscribe() );
        wechatUser.setSubscribeTime( dto.getSubscribeTime() );
        wechatUser.setAddTime( dto.getAddTime() );
        wechatUser.setStair( dto.getStair() );
        wechatUser.setSecond( dto.getSecond() );
        wechatUser.setOrderStair( dto.getOrderStair() );
        wechatUser.setOrderSecond( dto.getOrderSecond() );
        wechatUser.setNowMoney( dto.getNowMoney() );
        wechatUser.setSessionKey( dto.getSessionKey() );
        wechatUser.setUserType( dto.getUserType() );

        return wechatUser;
    }

    @Override
    public WechatUserDTO toDto(WechatUser entity) {
        if ( entity == null ) {
            return null;
        }

        WechatUserDTO wechatUserDTO = new WechatUserDTO();

        wechatUserDTO.setUid( entity.getUid() );
        wechatUserDTO.setUnionid( entity.getUnionid() );
        wechatUserDTO.setOpenid( entity.getOpenid() );
        wechatUserDTO.setRoutineOpenid( entity.getRoutineOpenid() );
        wechatUserDTO.setNickname( entity.getNickname() );
        wechatUserDTO.setHeadimgurl( entity.getHeadimgurl() );
        wechatUserDTO.setSex( entity.getSex() );
        wechatUserDTO.setCity( entity.getCity() );
        wechatUserDTO.setLanguage( entity.getLanguage() );
        wechatUserDTO.setProvince( entity.getProvince() );
        wechatUserDTO.setCountry( entity.getCountry() );
        wechatUserDTO.setRemark( entity.getRemark() );
        wechatUserDTO.setGroupid( entity.getGroupid() );
        wechatUserDTO.setTagidList( entity.getTagidList() );
        wechatUserDTO.setSubscribe( entity.getSubscribe() );
        wechatUserDTO.setSubscribeTime( entity.getSubscribeTime() );
        wechatUserDTO.setAddTime( entity.getAddTime() );
        wechatUserDTO.setStair( entity.getStair() );
        wechatUserDTO.setSecond( entity.getSecond() );
        wechatUserDTO.setOrderStair( entity.getOrderStair() );
        wechatUserDTO.setOrderSecond( entity.getOrderSecond() );
        wechatUserDTO.setNowMoney( entity.getNowMoney() );
        wechatUserDTO.setSessionKey( entity.getSessionKey() );
        wechatUserDTO.setUserType( entity.getUserType() );

        return wechatUserDTO;
    }

    @Override
    public List<WechatUser> toEntity(List<WechatUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<WechatUser> list = new ArrayList<WechatUser>( dtoList.size() );
        for ( WechatUserDTO wechatUserDTO : dtoList ) {
            list.add( toEntity( wechatUserDTO ) );
        }

        return list;
    }

    @Override
    public List<WechatUserDTO> toDto(List<WechatUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<WechatUserDTO> list = new ArrayList<WechatUserDTO>( entityList.size() );
        for ( WechatUser wechatUser : entityList ) {
            list.add( toDto( wechatUser ) );
        }

        return list;
    }
}
