package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreGifts;
import co.lq.modules.shop.service.dto.StoreGiftsDTO;
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
public class StoreGiftsMapperImpl implements StoreGiftsMapper {

    @Override
    public StoreGifts toEntity(StoreGiftsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreGifts storeGifts = new StoreGifts();

        storeGifts.setId( dto.getId() );
        storeGifts.setCategoryId( dto.getCategoryId() );
        storeGifts.setIcon( dto.getIcon() );
        storeGifts.setTitle( dto.getTitle() );
        storeGifts.setShowStatus( dto.getShowStatus() );
        storeGifts.setContent( dto.getContent() );
        storeGifts.setStoreId( dto.getStoreId() );
        storeGifts.setType( dto.getType() );
        storeGifts.setPrice( dto.getPrice() );
        storeGifts.setStock( dto.getStock() );
        storeGifts.setAddTime( dto.getAddTime() );
        storeGifts.setModifyTime( dto.getModifyTime() );
        storeGifts.setDeleted( dto.getDeleted() );

        return storeGifts;
    }

    @Override
    public StoreGiftsDTO toDto(StoreGifts entity) {
        if ( entity == null ) {
            return null;
        }

        StoreGiftsDTO storeGiftsDTO = new StoreGiftsDTO();

        storeGiftsDTO.setId( entity.getId() );
        storeGiftsDTO.setCategoryId( entity.getCategoryId() );
        storeGiftsDTO.setIcon( entity.getIcon() );
        storeGiftsDTO.setTitle( entity.getTitle() );
        storeGiftsDTO.setShowStatus( entity.getShowStatus() );
        storeGiftsDTO.setContent( entity.getContent() );
        storeGiftsDTO.setStoreId( entity.getStoreId() );
        storeGiftsDTO.setType( entity.getType() );
        storeGiftsDTO.setPrice( entity.getPrice() );
        storeGiftsDTO.setStock( entity.getStock() );
        storeGiftsDTO.setAddTime( entity.getAddTime() );
        storeGiftsDTO.setModifyTime( entity.getModifyTime() );
        storeGiftsDTO.setDeleted( entity.getDeleted() );

        return storeGiftsDTO;
    }

    @Override
    public List<StoreGifts> toEntity(List<StoreGiftsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreGifts> list = new ArrayList<StoreGifts>( dtoList.size() );
        for ( StoreGiftsDTO storeGiftsDTO : dtoList ) {
            list.add( toEntity( storeGiftsDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreGiftsDTO> toDto(List<StoreGifts> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreGiftsDTO> list = new ArrayList<StoreGiftsDTO>( entityList.size() );
        for ( StoreGifts storeGifts : entityList ) {
            list.add( toDto( storeGifts ) );
        }

        return list;
    }
}
