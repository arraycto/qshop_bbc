package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreWithdrawRecord;
import co.lq.modules.shop.service.dto.StoreWithdrawRecordDTO;
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
public class StoreWithdrawRecordMapperImpl implements StoreWithdrawRecordMapper {

    @Override
    public StoreWithdrawRecord toEntity(StoreWithdrawRecordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreWithdrawRecord storeWithdrawRecord = new StoreWithdrawRecord();

        storeWithdrawRecord.setId( dto.getId() );
        storeWithdrawRecord.setStoreWithdrawId( dto.getStoreWithdrawId() );
        storeWithdrawRecord.setVertifier( dto.getVertifier() );
        storeWithdrawRecord.setStatus( dto.getStatus() );
        storeWithdrawRecord.setDetail( dto.getDetail() );
        storeWithdrawRecord.setStoreId( dto.getStoreId() );
        storeWithdrawRecord.setAddTime( dto.getAddTime() );
        storeWithdrawRecord.setModifyTime( dto.getModifyTime() );
        storeWithdrawRecord.setDeleted( dto.getDeleted() );

        return storeWithdrawRecord;
    }

    @Override
    public StoreWithdrawRecordDTO toDto(StoreWithdrawRecord entity) {
        if ( entity == null ) {
            return null;
        }

        StoreWithdrawRecordDTO storeWithdrawRecordDTO = new StoreWithdrawRecordDTO();

        storeWithdrawRecordDTO.setId( entity.getId() );
        storeWithdrawRecordDTO.setStoreWithdrawId( entity.getStoreWithdrawId() );
        storeWithdrawRecordDTO.setVertifier( entity.getVertifier() );
        storeWithdrawRecordDTO.setStatus( entity.getStatus() );
        storeWithdrawRecordDTO.setDetail( entity.getDetail() );
        storeWithdrawRecordDTO.setStoreId( entity.getStoreId() );
        storeWithdrawRecordDTO.setAddTime( entity.getAddTime() );
        storeWithdrawRecordDTO.setModifyTime( entity.getModifyTime() );
        storeWithdrawRecordDTO.setDeleted( entity.getDeleted() );

        return storeWithdrawRecordDTO;
    }

    @Override
    public List<StoreWithdrawRecord> toEntity(List<StoreWithdrawRecordDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreWithdrawRecord> list = new ArrayList<StoreWithdrawRecord>( dtoList.size() );
        for ( StoreWithdrawRecordDTO storeWithdrawRecordDTO : dtoList ) {
            list.add( toEntity( storeWithdrawRecordDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreWithdrawRecordDTO> toDto(List<StoreWithdrawRecord> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreWithdrawRecordDTO> list = new ArrayList<StoreWithdrawRecordDTO>( entityList.size() );
        for ( StoreWithdrawRecord storeWithdrawRecord : entityList ) {
            list.add( toDto( storeWithdrawRecord ) );
        }

        return list;
    }
}
