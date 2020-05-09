package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreCategory;
import co.lq.modules.shop.service.dto.StoreCategoryDTO;
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
public class StoreCategoryMapperImpl implements StoreCategoryMapper {

    @Override
    public StoreCategory toEntity(StoreCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCategory storeCategory = new StoreCategory();

        storeCategory.setId( dto.getId() );
        storeCategory.setPid( dto.getPid() );
        storeCategory.setStoreId( dto.getStoreId() );
        storeCategory.setCateName( dto.getCateName() );
        storeCategory.setSort( dto.getSort() );
        storeCategory.setPic( dto.getPic() );
        storeCategory.setIsShow( dto.getIsShow() );
        storeCategory.setAddTime( dto.getAddTime() );
        storeCategory.setModifyTime( dto.getModifyTime() );
        storeCategory.setDeleted( dto.getDeleted() );

        return storeCategory;
    }

    @Override
    public StoreCategoryDTO toDto(StoreCategory entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCategoryDTO storeCategoryDTO = new StoreCategoryDTO();

        storeCategoryDTO.setId( entity.getId() );
        storeCategoryDTO.setPid( entity.getPid() );
        storeCategoryDTO.setStoreId( entity.getStoreId() );
        storeCategoryDTO.setCateName( entity.getCateName() );
        storeCategoryDTO.setSort( entity.getSort() );
        storeCategoryDTO.setPic( entity.getPic() );
        storeCategoryDTO.setIsShow( entity.getIsShow() );
        storeCategoryDTO.setAddTime( entity.getAddTime() );
        storeCategoryDTO.setModifyTime( entity.getModifyTime() );
        storeCategoryDTO.setDeleted( entity.getDeleted() );

        return storeCategoryDTO;
    }

    @Override
    public List<StoreCategory> toEntity(List<StoreCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCategory> list = new ArrayList<StoreCategory>( dtoList.size() );
        for ( StoreCategoryDTO storeCategoryDTO : dtoList ) {
            list.add( toEntity( storeCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCategoryDTO> toDto(List<StoreCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCategoryDTO> list = new ArrayList<StoreCategoryDTO>( entityList.size() );
        for ( StoreCategory storeCategory : entityList ) {
            list.add( toDto( storeCategory ) );
        }

        return list;
    }
}
