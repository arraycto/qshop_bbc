package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.UserLevel;
import co.lq.modules.shop.service.dto.UserLevelDTO;
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
public class UserLevelMapperImpl implements UserLevelMapper {

    @Override
    public UserLevel toEntity(UserLevelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserLevel userLevel = new UserLevel();

        userLevel.setId( dto.getId() );
        userLevel.setMerId( dto.getMerId() );
        userLevel.setName( dto.getName() );
        userLevel.setMoney( dto.getMoney() );
        userLevel.setValidDate( dto.getValidDate() );
        userLevel.setIsForever( dto.getIsForever() );
        userLevel.setIsPay( dto.getIsPay() );
        userLevel.setIsShow( dto.getIsShow() );
        userLevel.setGrade( dto.getGrade() );
        userLevel.setDiscount( dto.getDiscount() );
        userLevel.setImage( dto.getImage() );
        userLevel.setIcon( dto.getIcon() );
        userLevel.setExplain( dto.getExplain() );
        userLevel.setAddTime( dto.getAddTime() );
        userLevel.setIsDel( dto.getIsDel() );
        userLevel.setStoreId( dto.getStoreId() );

        return userLevel;
    }

    @Override
    public UserLevelDTO toDto(UserLevel entity) {
        if ( entity == null ) {
            return null;
        }

        UserLevelDTO userLevelDTO = new UserLevelDTO();

        userLevelDTO.setId( entity.getId() );
        userLevelDTO.setMerId( entity.getMerId() );
        userLevelDTO.setName( entity.getName() );
        userLevelDTO.setMoney( entity.getMoney() );
        userLevelDTO.setValidDate( entity.getValidDate() );
        userLevelDTO.setIsForever( entity.getIsForever() );
        userLevelDTO.setIsPay( entity.getIsPay() );
        userLevelDTO.setIsShow( entity.getIsShow() );
        userLevelDTO.setGrade( entity.getGrade() );
        userLevelDTO.setDiscount( entity.getDiscount() );
        userLevelDTO.setImage( entity.getImage() );
        userLevelDTO.setIcon( entity.getIcon() );
        userLevelDTO.setExplain( entity.getExplain() );
        userLevelDTO.setAddTime( entity.getAddTime() );
        userLevelDTO.setIsDel( entity.getIsDel() );
        userLevelDTO.setStoreId( entity.getStoreId() );

        return userLevelDTO;
    }

    @Override
    public List<UserLevel> toEntity(List<UserLevelDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserLevel> list = new ArrayList<UserLevel>( dtoList.size() );
        for ( UserLevelDTO userLevelDTO : dtoList ) {
            list.add( toEntity( userLevelDTO ) );
        }

        return list;
    }

    @Override
    public List<UserLevelDTO> toDto(List<UserLevel> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserLevelDTO> list = new ArrayList<UserLevelDTO>( entityList.size() );
        for ( UserLevel userLevel : entityList ) {
            list.add( toDto( userLevel ) );
        }

        return list;
    }
}
