package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreCollect;
import co.lq.modules.shop.service.dto.StoreCollectDTO;
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
public class StoreCollectMapperImpl implements StoreCollectMapper {

    @Override
    public StoreCollect toEntity(StoreCollectDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCollect storeCollect = new StoreCollect();

        storeCollect.setId( dto.getId() );
        storeCollect.setUid( dto.getUid() );
        storeCollect.setStoreId( dto.getStoreId() );
        storeCollect.setType( dto.getType() );
        storeCollect.setAddTime( dto.getAddTime() );
        storeCollect.setModifyTime( dto.getModifyTime() );
        storeCollect.setDeleted( dto.getDeleted() );

        return storeCollect;
    }

    @Override
    public StoreCollectDTO toDto(StoreCollect entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCollectDTO storeCollectDTO = new StoreCollectDTO();

        storeCollectDTO.setId( entity.getId() );
        storeCollectDTO.setUid( entity.getUid() );
        storeCollectDTO.setStoreId( entity.getStoreId() );
        storeCollectDTO.setType( entity.getType() );
        storeCollectDTO.setAddTime( entity.getAddTime() );
        storeCollectDTO.setModifyTime( entity.getModifyTime() );
        storeCollectDTO.setDeleted( entity.getDeleted() );

        return storeCollectDTO;
    }

    @Override
    public List<StoreCollect> toEntity(List<StoreCollectDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCollect> list = new ArrayList<StoreCollect>( dtoList.size() );
        for ( StoreCollectDTO storeCollectDTO : dtoList ) {
            list.add( toEntity( storeCollectDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCollectDTO> toDto(List<StoreCollect> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCollectDTO> list = new ArrayList<StoreCollectDTO>( entityList.size() );
        for ( StoreCollect storeCollect : entityList ) {
            list.add( toDto( storeCollect ) );
        }

        return list;
    }
}
