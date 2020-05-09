package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.SystemConfig;
import co.lq.modules.shop.service.dto.SystemConfigDTO;
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
public class SystemConfigMapperImpl implements SystemConfigMapper {

    @Override
    public SystemConfig toEntity(SystemConfigDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SystemConfig systemConfig = new SystemConfig();

        systemConfig.setId( dto.getId() );
        systemConfig.setMenuName( dto.getMenuName() );
        systemConfig.setValue( dto.getValue() );
        systemConfig.setSort( dto.getSort() );
        systemConfig.setStatus( dto.getStatus() );

        return systemConfig;
    }

    @Override
    public SystemConfigDTO toDto(SystemConfig entity) {
        if ( entity == null ) {
            return null;
        }

        SystemConfigDTO systemConfigDTO = new SystemConfigDTO();

        systemConfigDTO.setId( entity.getId() );
        systemConfigDTO.setMenuName( entity.getMenuName() );
        systemConfigDTO.setValue( entity.getValue() );
        systemConfigDTO.setSort( entity.getSort() );
        systemConfigDTO.setStatus( entity.getStatus() );

        return systemConfigDTO;
    }

    @Override
    public List<SystemConfig> toEntity(List<SystemConfigDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SystemConfig> list = new ArrayList<SystemConfig>( dtoList.size() );
        for ( SystemConfigDTO systemConfigDTO : dtoList ) {
            list.add( toEntity( systemConfigDTO ) );
        }

        return list;
    }

    @Override
    public List<SystemConfigDTO> toDto(List<SystemConfig> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SystemConfigDTO> list = new ArrayList<SystemConfigDTO>( entityList.size() );
        for ( SystemConfig systemConfig : entityList ) {
            list.add( toDto( systemConfig ) );
        }

        return list;
    }
}
