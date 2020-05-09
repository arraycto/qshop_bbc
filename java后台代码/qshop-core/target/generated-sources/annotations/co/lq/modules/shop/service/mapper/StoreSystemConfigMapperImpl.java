package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreSystemConfig;
import co.lq.modules.shop.service.dto.StoreSystemConfigDTO;
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
public class StoreSystemConfigMapperImpl implements StoreSystemConfigMapper {

    @Override
    public StoreSystemConfig toEntity(StoreSystemConfigDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreSystemConfig storeSystemConfig = new StoreSystemConfig();

        storeSystemConfig.setId( dto.getId() );
        storeSystemConfig.setMenuName( dto.getMenuName() );
        storeSystemConfig.setValue( dto.getValue() );
        storeSystemConfig.setSort( dto.getSort() );
        storeSystemConfig.setStatus( dto.getStatus() );
        storeSystemConfig.setStoreId( dto.getStoreId() );

        return storeSystemConfig;
    }

    @Override
    public StoreSystemConfigDTO toDto(StoreSystemConfig entity) {
        if ( entity == null ) {
            return null;
        }

        StoreSystemConfigDTO storeSystemConfigDTO = new StoreSystemConfigDTO();

        storeSystemConfigDTO.setId( entity.getId() );
        storeSystemConfigDTO.setMenuName( entity.getMenuName() );
        storeSystemConfigDTO.setValue( entity.getValue() );
        storeSystemConfigDTO.setSort( entity.getSort() );
        storeSystemConfigDTO.setStatus( entity.getStatus() );
        storeSystemConfigDTO.setStoreId( entity.getStoreId() );

        return storeSystemConfigDTO;
    }

    @Override
    public List<StoreSystemConfig> toEntity(List<StoreSystemConfigDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreSystemConfig> list = new ArrayList<StoreSystemConfig>( dtoList.size() );
        for ( StoreSystemConfigDTO storeSystemConfigDTO : dtoList ) {
            list.add( toEntity( storeSystemConfigDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreSystemConfigDTO> toDto(List<StoreSystemConfig> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreSystemConfigDTO> list = new ArrayList<StoreSystemConfigDTO>( entityList.size() );
        for ( StoreSystemConfig storeSystemConfig : entityList ) {
            list.add( toDto( storeSystemConfig ) );
        }

        return list;
    }
}
