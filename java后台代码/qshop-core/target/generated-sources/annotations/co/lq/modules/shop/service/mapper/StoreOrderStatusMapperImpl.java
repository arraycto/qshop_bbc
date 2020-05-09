package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreOrderStatus;
import co.lq.modules.shop.service.dto.StoreOrderStatusDTO;
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
public class StoreOrderStatusMapperImpl implements StoreOrderStatusMapper {

    @Override
    public StoreOrderStatus toEntity(StoreOrderStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreOrderStatus storeOrderStatus = new StoreOrderStatus();

        storeOrderStatus.setId( dto.getId() );
        storeOrderStatus.setOid( dto.getOid() );
        storeOrderStatus.setChangeType( dto.getChangeType() );
        storeOrderStatus.setChangeMessage( dto.getChangeMessage() );
        storeOrderStatus.setChangeTime( dto.getChangeTime() );

        return storeOrderStatus;
    }

    @Override
    public StoreOrderStatusDTO toDto(StoreOrderStatus entity) {
        if ( entity == null ) {
            return null;
        }

        StoreOrderStatusDTO storeOrderStatusDTO = new StoreOrderStatusDTO();

        storeOrderStatusDTO.setId( entity.getId() );
        storeOrderStatusDTO.setOid( entity.getOid() );
        storeOrderStatusDTO.setChangeType( entity.getChangeType() );
        storeOrderStatusDTO.setChangeMessage( entity.getChangeMessage() );
        storeOrderStatusDTO.setChangeTime( entity.getChangeTime() );

        return storeOrderStatusDTO;
    }

    @Override
    public List<StoreOrderStatus> toEntity(List<StoreOrderStatusDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreOrderStatus> list = new ArrayList<StoreOrderStatus>( dtoList.size() );
        for ( StoreOrderStatusDTO storeOrderStatusDTO : dtoList ) {
            list.add( toEntity( storeOrderStatusDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreOrderStatusDTO> toDto(List<StoreOrderStatus> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreOrderStatusDTO> list = new ArrayList<StoreOrderStatusDTO>( entityList.size() );
        for ( StoreOrderStatus storeOrderStatus : entityList ) {
            list.add( toDto( storeOrderStatus ) );
        }

        return list;
    }
}
