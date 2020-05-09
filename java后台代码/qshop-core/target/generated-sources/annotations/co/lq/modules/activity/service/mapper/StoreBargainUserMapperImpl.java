package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreBargainUser;
import co.lq.modules.activity.service.dto.StoreBargainUserDTO;
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
public class StoreBargainUserMapperImpl implements StoreBargainUserMapper {

    @Override
    public StoreBargainUser toEntity(StoreBargainUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreBargainUser storeBargainUser = new StoreBargainUser();

        storeBargainUser.setId( dto.getId() );
        storeBargainUser.setUid( dto.getUid() );
        storeBargainUser.setBargainId( dto.getBargainId() );
        storeBargainUser.setBargainPriceMin( dto.getBargainPriceMin() );
        storeBargainUser.setBargainPrice( dto.getBargainPrice() );
        storeBargainUser.setPrice( dto.getPrice() );
        storeBargainUser.setStatus( dto.getStatus() );
        storeBargainUser.setAddTime( dto.getAddTime() );
        if ( dto.getIsDel() != null ) {
            storeBargainUser.setIsDel( dto.getIsDel().intValue() );
        }
        storeBargainUser.setStoreId( dto.getStoreId() );

        return storeBargainUser;
    }

    @Override
    public StoreBargainUserDTO toDto(StoreBargainUser entity) {
        if ( entity == null ) {
            return null;
        }

        StoreBargainUserDTO storeBargainUserDTO = new StoreBargainUserDTO();

        storeBargainUserDTO.setId( entity.getId() );
        storeBargainUserDTO.setUid( entity.getUid() );
        storeBargainUserDTO.setBargainId( entity.getBargainId() );
        storeBargainUserDTO.setBargainPriceMin( entity.getBargainPriceMin() );
        storeBargainUserDTO.setBargainPrice( entity.getBargainPrice() );
        storeBargainUserDTO.setPrice( entity.getPrice() );
        storeBargainUserDTO.setStatus( entity.getStatus() );
        storeBargainUserDTO.setAddTime( entity.getAddTime() );
        if ( entity.getIsDel() != null ) {
            storeBargainUserDTO.setIsDel( entity.getIsDel().byteValue() );
        }
        storeBargainUserDTO.setStoreId( entity.getStoreId() );

        return storeBargainUserDTO;
    }

    @Override
    public List<StoreBargainUser> toEntity(List<StoreBargainUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreBargainUser> list = new ArrayList<StoreBargainUser>( dtoList.size() );
        for ( StoreBargainUserDTO storeBargainUserDTO : dtoList ) {
            list.add( toEntity( storeBargainUserDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreBargainUserDTO> toDto(List<StoreBargainUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreBargainUserDTO> list = new ArrayList<StoreBargainUserDTO>( entityList.size() );
        for ( StoreBargainUser storeBargainUser : entityList ) {
            list.add( toDto( storeBargainUser ) );
        }

        return list;
    }
}
