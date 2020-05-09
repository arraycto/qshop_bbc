package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.SystemGroupData;
import co.lq.modules.shop.service.dto.SystemGroupDataDTO;
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
public class SystemGroupDataMapperImpl implements SystemGroupDataMapper {

    @Override
    public SystemGroupData toEntity(SystemGroupDataDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SystemGroupData systemGroupData = new SystemGroupData();

        systemGroupData.setId( dto.getId() );
        systemGroupData.setGroupName( dto.getGroupName() );
        systemGroupData.setValue( dto.getValue() );
        systemGroupData.setAddTime( dto.getAddTime() );
        systemGroupData.setSort( dto.getSort() );
        systemGroupData.setStatus( dto.getStatus() );

        return systemGroupData;
    }

    @Override
    public SystemGroupDataDTO toDto(SystemGroupData entity) {
        if ( entity == null ) {
            return null;
        }

        SystemGroupDataDTO systemGroupDataDTO = new SystemGroupDataDTO();

        systemGroupDataDTO.setId( entity.getId() );
        systemGroupDataDTO.setGroupName( entity.getGroupName() );
        systemGroupDataDTO.setValue( entity.getValue() );
        systemGroupDataDTO.setAddTime( entity.getAddTime() );
        systemGroupDataDTO.setSort( entity.getSort() );
        systemGroupDataDTO.setStatus( entity.getStatus() );

        return systemGroupDataDTO;
    }

    @Override
    public List<SystemGroupData> toEntity(List<SystemGroupDataDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SystemGroupData> list = new ArrayList<SystemGroupData>( dtoList.size() );
        for ( SystemGroupDataDTO systemGroupDataDTO : dtoList ) {
            list.add( toEntity( systemGroupDataDTO ) );
        }

        return list;
    }

    @Override
    public List<SystemGroupDataDTO> toDto(List<SystemGroupData> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SystemGroupDataDTO> list = new ArrayList<SystemGroupDataDTO>( entityList.size() );
        for ( SystemGroupData systemGroupData : entityList ) {
            list.add( toDto( systemGroupData ) );
        }

        return list;
    }
}
