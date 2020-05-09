package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.MaterialGroup;
import co.lq.modules.shop.service.dto.MaterialGroupDto;
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
public class MaterialGroupMapperImpl implements MaterialGroupMapper {

    @Override
    public MaterialGroup toEntity(MaterialGroupDto dto) {
        if ( dto == null ) {
            return null;
        }

        MaterialGroup materialGroup = new MaterialGroup();

        materialGroup.setId( dto.getId() );
        materialGroup.setDelFlag( dto.getDelFlag() );
        materialGroup.setCreateTime( dto.getCreateTime() );
        materialGroup.setCreateId( dto.getCreateId() );
        materialGroup.setName( dto.getName() );

        return materialGroup;
    }

    @Override
    public MaterialGroupDto toDto(MaterialGroup entity) {
        if ( entity == null ) {
            return null;
        }

        MaterialGroupDto materialGroupDto = new MaterialGroupDto();

        materialGroupDto.setId( entity.getId() );
        materialGroupDto.setDelFlag( entity.getDelFlag() );
        materialGroupDto.setCreateTime( entity.getCreateTime() );
        materialGroupDto.setCreateId( entity.getCreateId() );
        materialGroupDto.setName( entity.getName() );

        return materialGroupDto;
    }

    @Override
    public List<MaterialGroup> toEntity(List<MaterialGroupDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MaterialGroup> list = new ArrayList<MaterialGroup>( dtoList.size() );
        for ( MaterialGroupDto materialGroupDto : dtoList ) {
            list.add( toEntity( materialGroupDto ) );
        }

        return list;
    }

    @Override
    public List<MaterialGroupDto> toDto(List<MaterialGroup> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MaterialGroupDto> list = new ArrayList<MaterialGroupDto>( entityList.size() );
        for ( MaterialGroup materialGroup : entityList ) {
            list.add( toDto( materialGroup ) );
        }

        return list;
    }
}
