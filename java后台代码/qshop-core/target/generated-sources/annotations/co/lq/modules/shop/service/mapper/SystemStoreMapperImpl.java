package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.SystemStore;
import co.lq.modules.shop.service.dto.SystemStoreDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-09T22:21:11+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_40 (Oracle Corporation)"
)
@Component
public class SystemStoreMapperImpl implements SystemStoreMapper {

    @Override
    public SystemStore toEntity(SystemStoreDto dto) {
        if ( dto == null ) {
            return null;
        }

        SystemStore systemStore = new SystemStore();

        systemStore.setId( dto.getId() );
        systemStore.setName( dto.getName() );
        systemStore.setIntroduction( dto.getIntroduction() );
        systemStore.setPhone( dto.getPhone() );
        systemStore.setAddress( dto.getAddress() );
        systemStore.setDetailedAddress( dto.getDetailedAddress() );
        systemStore.setImage( dto.getImage() );
        systemStore.setLatitude( dto.getLatitude() );
        systemStore.setLongitude( dto.getLongitude() );
        systemStore.setValidTime( dto.getValidTime() );
        systemStore.setValidTimeStart( dto.getValidTimeStart() );
        systemStore.setValidTimeEnd( dto.getValidTimeEnd() );
        systemStore.setDayTime( dto.getDayTime() );
        systemStore.setDayTimeStart( dto.getDayTimeStart() );
        systemStore.setDayTimeEnd( dto.getDayTimeEnd() );
        systemStore.setAddTime( dto.getAddTime() );
        systemStore.setIsShow( dto.getIsShow() );
        systemStore.setIsDel( dto.getIsDel() );
        systemStore.setStoreId( dto.getStoreId() );

        return systemStore;
    }

    @Override
    public SystemStoreDto toDto(SystemStore entity) {
        if ( entity == null ) {
            return null;
        }

        SystemStoreDto systemStoreDto = new SystemStoreDto();

        systemStoreDto.setId( entity.getId() );
        systemStoreDto.setName( entity.getName() );
        systemStoreDto.setIntroduction( entity.getIntroduction() );
        systemStoreDto.setPhone( entity.getPhone() );
        systemStoreDto.setAddress( entity.getAddress() );
        systemStoreDto.setDetailedAddress( entity.getDetailedAddress() );
        systemStoreDto.setImage( entity.getImage() );
        systemStoreDto.setLatitude( entity.getLatitude() );
        systemStoreDto.setLongitude( entity.getLongitude() );
        systemStoreDto.setValidTime( entity.getValidTime() );
        systemStoreDto.setDayTime( entity.getDayTime() );
        systemStoreDto.setAddTime( entity.getAddTime() );
        systemStoreDto.setIsShow( entity.getIsShow() );
        systemStoreDto.setIsDel( entity.getIsDel() );
        systemStoreDto.setDayTimeStart( entity.getDayTimeStart() );
        systemStoreDto.setDayTimeEnd( entity.getDayTimeEnd() );
        systemStoreDto.setValidTimeStart( entity.getValidTimeStart() );
        systemStoreDto.setValidTimeEnd( entity.getValidTimeEnd() );
        systemStoreDto.setStoreId( entity.getStoreId() );

        return systemStoreDto;
    }

    @Override
    public List<SystemStore> toEntity(List<SystemStoreDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SystemStore> list = new ArrayList<SystemStore>( dtoList.size() );
        for ( SystemStoreDto systemStoreDto : dtoList ) {
            list.add( toEntity( systemStoreDto ) );
        }

        return list;
    }

    @Override
    public List<SystemStoreDto> toDto(List<SystemStore> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SystemStoreDto> list = new ArrayList<SystemStoreDto>( entityList.size() );
        for ( SystemStore systemStore : entityList ) {
            list.add( toDto( systemStore ) );
        }

        return list;
    }
}
