package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.OrderReturnReason;
import co.lq.modules.shop.service.dto.OrderReturnReasonDTO;
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
public class OrderReturnReasonMapperImpl implements OrderReturnReasonMapper {

    @Override
    public OrderReturnReason toEntity(OrderReturnReasonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderReturnReason orderReturnReason = new OrderReturnReason();

        orderReturnReason.setId( dto.getId() );
        orderReturnReason.setName( dto.getName() );
        orderReturnReason.setSort( dto.getSort() );
        orderReturnReason.setStatus( dto.getStatus() );
        orderReturnReason.setAddTime( dto.getAddTime() );
        orderReturnReason.setModifyTime( dto.getModifyTime() );
        orderReturnReason.setDeleted( dto.getDeleted() );

        return orderReturnReason;
    }

    @Override
    public OrderReturnReasonDTO toDto(OrderReturnReason entity) {
        if ( entity == null ) {
            return null;
        }

        OrderReturnReasonDTO orderReturnReasonDTO = new OrderReturnReasonDTO();

        orderReturnReasonDTO.setId( entity.getId() );
        orderReturnReasonDTO.setName( entity.getName() );
        orderReturnReasonDTO.setSort( entity.getSort() );
        orderReturnReasonDTO.setStatus( entity.getStatus() );
        orderReturnReasonDTO.setAddTime( entity.getAddTime() );
        orderReturnReasonDTO.setModifyTime( entity.getModifyTime() );
        orderReturnReasonDTO.setDeleted( entity.getDeleted() );

        return orderReturnReasonDTO;
    }

    @Override
    public List<OrderReturnReason> toEntity(List<OrderReturnReasonDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderReturnReason> list = new ArrayList<OrderReturnReason>( dtoList.size() );
        for ( OrderReturnReasonDTO orderReturnReasonDTO : dtoList ) {
            list.add( toEntity( orderReturnReasonDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderReturnReasonDTO> toDto(List<OrderReturnReason> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderReturnReasonDTO> list = new ArrayList<OrderReturnReasonDTO>( entityList.size() );
        for ( OrderReturnReason orderReturnReason : entityList ) {
            list.add( toDto( orderReturnReason ) );
        }

        return list;
    }
}
