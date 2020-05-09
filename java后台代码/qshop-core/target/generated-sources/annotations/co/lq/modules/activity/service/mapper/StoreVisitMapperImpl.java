package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreVisit;
import co.lq.modules.activity.service.dto.StoreVisitDTO;
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
public class StoreVisitMapperImpl implements StoreVisitMapper {

    @Override
    public StoreVisit toEntity(StoreVisitDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreVisit storeVisit = new StoreVisit();

        storeVisit.setId( dto.getId() );
        storeVisit.setProductId( dto.getProductId() );
        storeVisit.setProductType( dto.getProductType() );
        storeVisit.setCateId( dto.getCateId() );
        storeVisit.setType( dto.getType() );
        storeVisit.setUid( dto.getUid() );
        storeVisit.setCount( dto.getCount() );
        storeVisit.setContent( dto.getContent() );
        storeVisit.setAddTime( dto.getAddTime() );
        storeVisit.setStoreId( dto.getStoreId() );

        return storeVisit;
    }

    @Override
    public StoreVisitDTO toDto(StoreVisit entity) {
        if ( entity == null ) {
            return null;
        }

        StoreVisitDTO storeVisitDTO = new StoreVisitDTO();

        storeVisitDTO.setId( entity.getId() );
        storeVisitDTO.setProductId( entity.getProductId() );
        storeVisitDTO.setProductType( entity.getProductType() );
        storeVisitDTO.setCateId( entity.getCateId() );
        storeVisitDTO.setType( entity.getType() );
        storeVisitDTO.setUid( entity.getUid() );
        storeVisitDTO.setCount( entity.getCount() );
        storeVisitDTO.setContent( entity.getContent() );
        storeVisitDTO.setAddTime( entity.getAddTime() );
        storeVisitDTO.setStoreId( entity.getStoreId() );

        return storeVisitDTO;
    }

    @Override
    public List<StoreVisit> toEntity(List<StoreVisitDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreVisit> list = new ArrayList<StoreVisit>( dtoList.size() );
        for ( StoreVisitDTO storeVisitDTO : dtoList ) {
            list.add( toEntity( storeVisitDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreVisitDTO> toDto(List<StoreVisit> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreVisitDTO> list = new ArrayList<StoreVisitDTO>( entityList.size() );
        for ( StoreVisit storeVisit : entityList ) {
            list.add( toDto( storeVisit ) );
        }

        return list;
    }
}
