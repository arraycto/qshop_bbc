package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Material;
import co.lq.modules.shop.service.dto.MaterialDto;
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
public class MaterialMapperImpl implements MaterialMapper {

    @Override
    public Material toEntity(MaterialDto dto) {
        if ( dto == null ) {
            return null;
        }

        Material material = new Material();

        material.setId( dto.getId() );
        material.setDelFlag( dto.getDelFlag() );
        material.setCreateTime( dto.getCreateTime() );
        material.setCreateId( dto.getCreateId() );
        material.setType( dto.getType() );
        material.setGroupId( dto.getGroupId() );
        material.setName( dto.getName() );
        material.setUrl( dto.getUrl() );

        return material;
    }

    @Override
    public MaterialDto toDto(Material entity) {
        if ( entity == null ) {
            return null;
        }

        MaterialDto materialDto = new MaterialDto();

        materialDto.setId( entity.getId() );
        materialDto.setDelFlag( entity.getDelFlag() );
        materialDto.setCreateTime( entity.getCreateTime() );
        materialDto.setCreateId( entity.getCreateId() );
        materialDto.setType( entity.getType() );
        materialDto.setGroupId( entity.getGroupId() );
        materialDto.setName( entity.getName() );
        materialDto.setUrl( entity.getUrl() );

        return materialDto;
    }

    @Override
    public List<Material> toEntity(List<MaterialDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Material> list = new ArrayList<Material>( dtoList.size() );
        for ( MaterialDto materialDto : dtoList ) {
            list.add( toEntity( materialDto ) );
        }

        return list;
    }

    @Override
    public List<MaterialDto> toDto(List<Material> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MaterialDto> list = new ArrayList<MaterialDto>( entityList.size() );
        for ( Material material : entityList ) {
            list.add( toDto( material ) );
        }

        return list;
    }
}
