package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreCouponUser;
import co.lq.modules.activity.service.dto.StoreCouponUserDTO;
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
public class StoreCouponUserMapperImpl implements StoreCouponUserMapper {

    @Override
    public StoreCouponUser toEntity(StoreCouponUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCouponUser storeCouponUser = new StoreCouponUser();

        storeCouponUser.setId( dto.getId() );
        storeCouponUser.setCid( dto.getCid() );
        storeCouponUser.setUid( dto.getUid() );
        storeCouponUser.setCouponTitle( dto.getCouponTitle() );
        storeCouponUser.setCouponPrice( dto.getCouponPrice() );
        storeCouponUser.setUseMinPrice( dto.getUseMinPrice() );
        storeCouponUser.setAddTime( dto.getAddTime() );
        storeCouponUser.setEndTime( dto.getEndTime() );
        storeCouponUser.setUseTime( dto.getUseTime() );
        storeCouponUser.setType( dto.getType() );
        storeCouponUser.setStatus( dto.getStatus() );
        storeCouponUser.setIsFail( dto.getIsFail() );
        storeCouponUser.setStoreId( dto.getStoreId() );

        return storeCouponUser;
    }

    @Override
    public StoreCouponUserDTO toDto(StoreCouponUser entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCouponUserDTO storeCouponUserDTO = new StoreCouponUserDTO();

        storeCouponUserDTO.setId( entity.getId() );
        storeCouponUserDTO.setCid( entity.getCid() );
        storeCouponUserDTO.setUid( entity.getUid() );
        storeCouponUserDTO.setCouponTitle( entity.getCouponTitle() );
        storeCouponUserDTO.setCouponPrice( entity.getCouponPrice() );
        storeCouponUserDTO.setUseMinPrice( entity.getUseMinPrice() );
        storeCouponUserDTO.setAddTime( entity.getAddTime() );
        storeCouponUserDTO.setEndTime( entity.getEndTime() );
        storeCouponUserDTO.setUseTime( entity.getUseTime() );
        storeCouponUserDTO.setType( entity.getType() );
        storeCouponUserDTO.setStatus( entity.getStatus() );
        storeCouponUserDTO.setIsFail( entity.getIsFail() );
        storeCouponUserDTO.setStoreId( entity.getStoreId() );

        return storeCouponUserDTO;
    }

    @Override
    public List<StoreCouponUser> toEntity(List<StoreCouponUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCouponUser> list = new ArrayList<StoreCouponUser>( dtoList.size() );
        for ( StoreCouponUserDTO storeCouponUserDTO : dtoList ) {
            list.add( toEntity( storeCouponUserDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCouponUserDTO> toDto(List<StoreCouponUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCouponUserDTO> list = new ArrayList<StoreCouponUserDTO>( entityList.size() );
        for ( StoreCouponUser storeCouponUser : entityList ) {
            list.add( toDto( storeCouponUser ) );
        }

        return list;
    }
}
