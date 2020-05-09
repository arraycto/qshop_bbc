package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreGiftsCategory;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryDTO;
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
public class StoreGiftsCategoryMapperImpl implements StoreGiftsCategoryMapper {

    @Override
    public StoreGiftsCategory toEntity(StoreGiftsCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreGiftsCategory storeGiftsCategory = new StoreGiftsCategory();

        storeGiftsCategory.setId( dto.getId() );
        storeGiftsCategory.setName( dto.getName() );
        storeGiftsCategory.setIcon( dto.getIcon() );
        storeGiftsCategory.setShowStatus( dto.getShowStatus() );
        storeGiftsCategory.setSort( dto.getSort() );
        storeGiftsCategory.setStoreId( dto.getStoreId() );
        storeGiftsCategory.setAddTime( dto.getAddTime() );
        storeGiftsCategory.setModifyTime( dto.getModifyTime() );
        storeGiftsCategory.setDeleted( dto.getDeleted() );

        return storeGiftsCategory;
    }

    @Override
    public StoreGiftsCategoryDTO toDto(StoreGiftsCategory entity) {
        if ( entity == null ) {
            return null;
        }

        StoreGiftsCategoryDTO storeGiftsCategoryDTO = new StoreGiftsCategoryDTO();

        storeGiftsCategoryDTO.setId( entity.getId() );
        storeGiftsCategoryDTO.setName( entity.getName() );
        storeGiftsCategoryDTO.setIcon( entity.getIcon() );
        storeGiftsCategoryDTO.setShowStatus( entity.getShowStatus() );
        storeGiftsCategoryDTO.setSort( entity.getSort() );
        storeGiftsCategoryDTO.setStoreId( entity.getStoreId() );
        storeGiftsCategoryDTO.setAddTime( entity.getAddTime() );
        storeGiftsCategoryDTO.setModifyTime( entity.getModifyTime() );
        storeGiftsCategoryDTO.setDeleted( entity.getDeleted() );

        return storeGiftsCategoryDTO;
    }

    @Override
    public List<StoreGiftsCategory> toEntity(List<StoreGiftsCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreGiftsCategory> list = new ArrayList<StoreGiftsCategory>( dtoList.size() );
        for ( StoreGiftsCategoryDTO storeGiftsCategoryDTO : dtoList ) {
            list.add( toEntity( storeGiftsCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreGiftsCategoryDTO> toDto(List<StoreGiftsCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreGiftsCategoryDTO> list = new ArrayList<StoreGiftsCategoryDTO>( entityList.size() );
        for ( StoreGiftsCategory storeGiftsCategory : entityList ) {
            list.add( toDto( storeGiftsCategory ) );
        }

        return list;
    }
}
