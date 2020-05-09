package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StorePink;
import co.lq.modules.activity.service.dto.StorePinkDTO;
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
public class StorePinkMapperImpl implements StorePinkMapper {

    @Override
    public StorePink toEntity(StorePinkDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StorePink storePink = new StorePink();

        if ( dto.getId() != null ) {
            storePink.setId( dto.getId().longValue() );
        }
        storePink.setUid( dto.getUid() );
        storePink.setOrderId( dto.getOrderId() );
        if ( dto.getOrderIdKey() != null ) {
            storePink.setOrderIdKey( dto.getOrderIdKey().longValue() );
        }
        storePink.setTotalNum( dto.getTotalNum() );
        storePink.setTotalPrice( dto.getTotalPrice() );
        storePink.setCid( dto.getCid() );
        storePink.setPid( dto.getPid() );
        storePink.setPeople( dto.getPeople() );
        storePink.setPrice( dto.getPrice() );
        storePink.setAddTime( dto.getAddTime() );
        storePink.setStopTime( dto.getStopTime() );
        if ( dto.getKId() != null ) {
            storePink.setKId( dto.getKId().longValue() );
        }
        storePink.setIsTpl( dto.getIsTpl() );
        storePink.setIsRefund( dto.getIsRefund() );
        storePink.setStatus( dto.getStatus() );
        storePink.setStoreId( dto.getStoreId() );

        return storePink;
    }

    @Override
    public StorePinkDTO toDto(StorePink entity) {
        if ( entity == null ) {
            return null;
        }

        StorePinkDTO storePinkDTO = new StorePinkDTO();

        if ( entity.getId() != null ) {
            storePinkDTO.setId( entity.getId().intValue() );
        }
        storePinkDTO.setUid( entity.getUid() );
        storePinkDTO.setOrderId( entity.getOrderId() );
        if ( entity.getOrderIdKey() != null ) {
            storePinkDTO.setOrderIdKey( entity.getOrderIdKey().intValue() );
        }
        storePinkDTO.setTotalNum( entity.getTotalNum() );
        storePinkDTO.setTotalPrice( entity.getTotalPrice() );
        if ( entity.getCid() != null ) {
            storePinkDTO.setCid( entity.getCid() );
        }
        if ( entity.getPid() != null ) {
            storePinkDTO.setPid( entity.getPid() );
        }
        storePinkDTO.setPeople( entity.getPeople() );
        storePinkDTO.setPrice( entity.getPrice() );
        storePinkDTO.setAddTime( entity.getAddTime() );
        storePinkDTO.setStopTime( entity.getStopTime() );
        if ( entity.getKId() != null ) {
            storePinkDTO.setKId( entity.getKId().intValue() );
        }
        storePinkDTO.setIsTpl( entity.getIsTpl() );
        storePinkDTO.setIsRefund( entity.getIsRefund() );
        storePinkDTO.setStatus( entity.getStatus() );
        storePinkDTO.setStoreId( entity.getStoreId() );

        return storePinkDTO;
    }

    @Override
    public List<StorePink> toEntity(List<StorePinkDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StorePink> list = new ArrayList<StorePink>( dtoList.size() );
        for ( StorePinkDTO storePinkDTO : dtoList ) {
            list.add( toEntity( storePinkDTO ) );
        }

        return list;
    }

    @Override
    public List<StorePinkDTO> toDto(List<StorePink> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StorePinkDTO> list = new ArrayList<StorePinkDTO>( entityList.size() );
        for ( StorePink storePink : entityList ) {
            list.add( toDto( storePink ) );
        }

        return list;
    }
}
