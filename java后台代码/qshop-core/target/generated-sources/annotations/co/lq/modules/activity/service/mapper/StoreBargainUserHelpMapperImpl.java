package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreBargainUserHelp;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpDTO;
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
public class StoreBargainUserHelpMapperImpl implements StoreBargainUserHelpMapper {

    @Override
    public StoreBargainUserHelp toEntity(StoreBargainUserHelpDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreBargainUserHelp storeBargainUserHelp = new StoreBargainUserHelp();

        storeBargainUserHelp.setId( dto.getId() );
        storeBargainUserHelp.setUid( dto.getUid() );
        storeBargainUserHelp.setBargainId( dto.getBargainId() );
        storeBargainUserHelp.setBargainUserId( dto.getBargainUserId() );
        storeBargainUserHelp.setPrice( dto.getPrice() );
        storeBargainUserHelp.setAddTime( dto.getAddTime() );
        storeBargainUserHelp.setStoreId( dto.getStoreId() );

        return storeBargainUserHelp;
    }

    @Override
    public StoreBargainUserHelpDTO toDto(StoreBargainUserHelp entity) {
        if ( entity == null ) {
            return null;
        }

        StoreBargainUserHelpDTO storeBargainUserHelpDTO = new StoreBargainUserHelpDTO();

        storeBargainUserHelpDTO.setId( entity.getId() );
        storeBargainUserHelpDTO.setUid( entity.getUid() );
        storeBargainUserHelpDTO.setBargainId( entity.getBargainId() );
        storeBargainUserHelpDTO.setBargainUserId( entity.getBargainUserId() );
        storeBargainUserHelpDTO.setPrice( entity.getPrice() );
        storeBargainUserHelpDTO.setAddTime( entity.getAddTime() );
        storeBargainUserHelpDTO.setStoreId( entity.getStoreId() );

        return storeBargainUserHelpDTO;
    }

    @Override
    public List<StoreBargainUserHelp> toEntity(List<StoreBargainUserHelpDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreBargainUserHelp> list = new ArrayList<StoreBargainUserHelp>( dtoList.size() );
        for ( StoreBargainUserHelpDTO storeBargainUserHelpDTO : dtoList ) {
            list.add( toEntity( storeBargainUserHelpDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreBargainUserHelpDTO> toDto(List<StoreBargainUserHelp> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreBargainUserHelpDTO> list = new ArrayList<StoreBargainUserHelpDTO>( entityList.size() );
        for ( StoreBargainUserHelp storeBargainUserHelp : entityList ) {
            list.add( toDto( storeBargainUserHelp ) );
        }

        return list;
    }
}
