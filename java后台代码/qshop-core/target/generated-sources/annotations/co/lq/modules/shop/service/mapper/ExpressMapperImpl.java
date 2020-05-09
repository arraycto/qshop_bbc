package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Express;
import co.lq.modules.shop.service.dto.ExpressDTO;
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
public class ExpressMapperImpl implements ExpressMapper {

    @Override
    public Express toEntity(ExpressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Express express = new Express();

        express.setId( dto.getId() );
        express.setCode( dto.getCode() );
        express.setName( dto.getName() );
        express.setSort( dto.getSort() );
        express.setIsShow( dto.getIsShow() );

        return express;
    }

    @Override
    public ExpressDTO toDto(Express entity) {
        if ( entity == null ) {
            return null;
        }

        ExpressDTO expressDTO = new ExpressDTO();

        expressDTO.setId( entity.getId() );
        expressDTO.setCode( entity.getCode() );
        expressDTO.setName( entity.getName() );
        expressDTO.setSort( entity.getSort() );
        expressDTO.setIsShow( entity.getIsShow() );

        return expressDTO;
    }

    @Override
    public List<Express> toEntity(List<ExpressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Express> list = new ArrayList<Express>( dtoList.size() );
        for ( ExpressDTO expressDTO : dtoList ) {
            list.add( toEntity( expressDTO ) );
        }

        return list;
    }

    @Override
    public List<ExpressDTO> toDto(List<Express> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ExpressDTO> list = new ArrayList<ExpressDTO>( entityList.size() );
        for ( Express express : entityList ) {
            list.add( toDto( express ) );
        }

        return list;
    }
}
