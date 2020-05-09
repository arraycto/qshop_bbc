package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.HomeAdvertise;
import co.lq.modules.shop.service.dto.HomeAdvertiseDTO;
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
public class HomeAdvertiseMapperImpl implements HomeAdvertiseMapper {

    @Override
    public HomeAdvertise toEntity(HomeAdvertiseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HomeAdvertise homeAdvertise = new HomeAdvertise();

        homeAdvertise.setId( dto.getId() );
        homeAdvertise.setName( dto.getName() );
        homeAdvertise.setType( dto.getType() );
        homeAdvertise.setPic( dto.getPic() );
        homeAdvertise.setStartTime( dto.getStartTime() );
        homeAdvertise.setEndTime( dto.getEndTime() );
        homeAdvertise.setStatus( dto.getStatus() );
        homeAdvertise.setClickCount( dto.getClickCount() );
        homeAdvertise.setOrderCount( dto.getOrderCount() );
        homeAdvertise.setUrl( dto.getUrl() );
        homeAdvertise.setNote( dto.getNote() );
        homeAdvertise.setSort( dto.getSort() );
        homeAdvertise.setStoreId( dto.getStoreId() );

        return homeAdvertise;
    }

    @Override
    public HomeAdvertiseDTO toDto(HomeAdvertise entity) {
        if ( entity == null ) {
            return null;
        }

        HomeAdvertiseDTO homeAdvertiseDTO = new HomeAdvertiseDTO();

        homeAdvertiseDTO.setId( entity.getId() );
        homeAdvertiseDTO.setName( entity.getName() );
        homeAdvertiseDTO.setType( entity.getType() );
        homeAdvertiseDTO.setPic( entity.getPic() );
        homeAdvertiseDTO.setStartTime( entity.getStartTime() );
        homeAdvertiseDTO.setEndTime( entity.getEndTime() );
        homeAdvertiseDTO.setStatus( entity.getStatus() );
        homeAdvertiseDTO.setClickCount( entity.getClickCount() );
        homeAdvertiseDTO.setOrderCount( entity.getOrderCount() );
        homeAdvertiseDTO.setUrl( entity.getUrl() );
        homeAdvertiseDTO.setNote( entity.getNote() );
        homeAdvertiseDTO.setSort( entity.getSort() );
        homeAdvertiseDTO.setStoreId( entity.getStoreId() );

        return homeAdvertiseDTO;
    }

    @Override
    public List<HomeAdvertise> toEntity(List<HomeAdvertiseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HomeAdvertise> list = new ArrayList<HomeAdvertise>( dtoList.size() );
        for ( HomeAdvertiseDTO homeAdvertiseDTO : dtoList ) {
            list.add( toEntity( homeAdvertiseDTO ) );
        }

        return list;
    }

    @Override
    public List<HomeAdvertiseDTO> toDto(List<HomeAdvertise> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HomeAdvertiseDTO> list = new ArrayList<HomeAdvertiseDTO>( entityList.size() );
        for ( HomeAdvertise homeAdvertise : entityList ) {
            list.add( toDto( homeAdvertise ) );
        }

        return list;
    }
}
