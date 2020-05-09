package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreVertifyRecord;
import co.lq.modules.shop.service.dto.StoreVertifyRecordDTO;
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
public class StoreVertifyRecordMapperImpl implements StoreVertifyRecordMapper {

    @Override
    public StoreVertifyRecord toEntity(StoreVertifyRecordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreVertifyRecord storeVertifyRecord = new StoreVertifyRecord();

        storeVertifyRecord.setId( dto.getId() );
        storeVertifyRecord.setVertifier( dto.getVertifier() );
        storeVertifyRecord.setStatus( dto.getStatus() );
        storeVertifyRecord.setDetail( dto.getDetail() );
        storeVertifyRecord.setStoreId( dto.getStoreId() );
        storeVertifyRecord.setAddTime( dto.getAddTime() );
        storeVertifyRecord.setModifyTime( dto.getModifyTime() );
        storeVertifyRecord.setDeleted( dto.getDeleted() );

        return storeVertifyRecord;
    }

    @Override
    public StoreVertifyRecordDTO toDto(StoreVertifyRecord entity) {
        if ( entity == null ) {
            return null;
        }

        StoreVertifyRecordDTO storeVertifyRecordDTO = new StoreVertifyRecordDTO();

        storeVertifyRecordDTO.setId( entity.getId() );
        storeVertifyRecordDTO.setVertifier( entity.getVertifier() );
        storeVertifyRecordDTO.setDetail( entity.getDetail() );
        storeVertifyRecordDTO.setStoreId( entity.getStoreId() );
        storeVertifyRecordDTO.setAddTime( entity.getAddTime() );
        storeVertifyRecordDTO.setModifyTime( entity.getModifyTime() );
        storeVertifyRecordDTO.setDeleted( entity.getDeleted() );
        storeVertifyRecordDTO.setStatus( entity.getStatus() );

        return storeVertifyRecordDTO;
    }

    @Override
    public List<StoreVertifyRecord> toEntity(List<StoreVertifyRecordDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreVertifyRecord> list = new ArrayList<StoreVertifyRecord>( dtoList.size() );
        for ( StoreVertifyRecordDTO storeVertifyRecordDTO : dtoList ) {
            list.add( toEntity( storeVertifyRecordDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreVertifyRecordDTO> toDto(List<StoreVertifyRecord> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreVertifyRecordDTO> list = new ArrayList<StoreVertifyRecordDTO>( entityList.size() );
        for ( StoreVertifyRecord storeVertifyRecord : entityList ) {
            list.add( toDto( storeVertifyRecord ) );
        }

        return list;
    }
}
