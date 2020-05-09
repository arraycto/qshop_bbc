package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreOrderReturnReason;
import co.lq.modules.shop.service.dto.StoreOrderReturnReasonDTO;
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
public class StoreOrderReturnReasonMapperImpl implements StoreOrderReturnReasonMapper {

    @Override
    public StoreOrderReturnReason toEntity(StoreOrderReturnReasonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreOrderReturnReason storeOrderReturnReason = new StoreOrderReturnReason();

        storeOrderReturnReason.setId( dto.getId() );
        storeOrderReturnReason.setName( dto.getName() );
        storeOrderReturnReason.setSort( dto.getSort() );
        storeOrderReturnReason.setStatus( dto.getStatus() );
        storeOrderReturnReason.setStoreId( dto.getStoreId() );
        storeOrderReturnReason.setAddTime( dto.getAddTime() );
        storeOrderReturnReason.setModifyTime( dto.getModifyTime() );
        storeOrderReturnReason.setDeleted( dto.getDeleted() );

        return storeOrderReturnReason;
    }

    @Override
    public StoreOrderReturnReasonDTO toDto(StoreOrderReturnReason entity) {
        if ( entity == null ) {
            return null;
        }

        StoreOrderReturnReasonDTO storeOrderReturnReasonDTO = new StoreOrderReturnReasonDTO();

        storeOrderReturnReasonDTO.setId( entity.getId() );
        storeOrderReturnReasonDTO.setName( entity.getName() );
        storeOrderReturnReasonDTO.setSort( entity.getSort() );
        storeOrderReturnReasonDTO.setStatus( entity.getStatus() );
        storeOrderReturnReasonDTO.setStoreId( entity.getStoreId() );
        storeOrderReturnReasonDTO.setAddTime( entity.getAddTime() );
        storeOrderReturnReasonDTO.setModifyTime( entity.getModifyTime() );
        storeOrderReturnReasonDTO.setDeleted( entity.getDeleted() );

        return storeOrderReturnReasonDTO;
    }

    @Override
    public List<StoreOrderReturnReason> toEntity(List<StoreOrderReturnReasonDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreOrderReturnReason> list = new ArrayList<StoreOrderReturnReason>( dtoList.size() );
        for ( StoreOrderReturnReasonDTO storeOrderReturnReasonDTO : dtoList ) {
            list.add( toEntity( storeOrderReturnReasonDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreOrderReturnReasonDTO> toDto(List<StoreOrderReturnReason> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreOrderReturnReasonDTO> list = new ArrayList<StoreOrderReturnReasonDTO>( entityList.size() );
        for ( StoreOrderReturnReason storeOrderReturnReason : entityList ) {
            list.add( toDto( storeOrderReturnReason ) );
        }

        return list;
    }
}
