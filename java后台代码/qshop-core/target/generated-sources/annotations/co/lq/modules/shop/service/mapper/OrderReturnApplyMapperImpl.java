package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.OrderReturnApply;
import co.lq.modules.shop.service.dto.OrderReturnApplyDTO;
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
public class OrderReturnApplyMapperImpl implements OrderReturnApplyMapper {

    @Override
    public OrderReturnApply toEntity(OrderReturnApplyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderReturnApply orderReturnApply = new OrderReturnApply();

        orderReturnApply.setId( dto.getId() );
        orderReturnApply.setOid( dto.getOid() );
        orderReturnApply.setAddressId( dto.getAddressId() );
        orderReturnApply.setOrderId( dto.getOrderId() );
        orderReturnApply.setMemberUsername( dto.getMemberUsername() );
        orderReturnApply.setReturnAmount( dto.getReturnAmount() );
        orderReturnApply.setReturnName( dto.getReturnName() );
        orderReturnApply.setReturnPhone( dto.getReturnPhone() );
        orderReturnApply.setStatus( dto.getStatus() );
        orderReturnApply.setReason( dto.getReason() );
        orderReturnApply.setDescription( dto.getDescription() );
        orderReturnApply.setProofPics( dto.getProofPics() );
        orderReturnApply.setHandleNote( dto.getHandleNote() );
        orderReturnApply.setHandleMan( dto.getHandleMan() );
        orderReturnApply.setReceiveMan( dto.getReceiveMan() );
        orderReturnApply.setReceiveTime( dto.getReceiveTime() );
        orderReturnApply.setReceiveNote( dto.getReceiveNote() );
        orderReturnApply.setStoreId( dto.getStoreId() );
        orderReturnApply.setAddTime( dto.getAddTime() );
        orderReturnApply.setModifyTime( dto.getModifyTime() );
        orderReturnApply.setDeleted( dto.getDeleted() );
        orderReturnApply.setOrderAmount( dto.getOrderAmount() );
        orderReturnApply.setCartInfo( dto.getCartInfo() );

        return orderReturnApply;
    }

    @Override
    public OrderReturnApplyDTO toDto(OrderReturnApply entity) {
        if ( entity == null ) {
            return null;
        }

        OrderReturnApplyDTO orderReturnApplyDTO = new OrderReturnApplyDTO();

        orderReturnApplyDTO.setId( entity.getId() );
        orderReturnApplyDTO.setOid( entity.getOid() );
        orderReturnApplyDTO.setAddressId( entity.getAddressId() );
        orderReturnApplyDTO.setOrderId( entity.getOrderId() );
        orderReturnApplyDTO.setMemberUsername( entity.getMemberUsername() );
        orderReturnApplyDTO.setReturnAmount( entity.getReturnAmount() );
        orderReturnApplyDTO.setReturnName( entity.getReturnName() );
        orderReturnApplyDTO.setReturnPhone( entity.getReturnPhone() );
        orderReturnApplyDTO.setStatus( entity.getStatus() );
        orderReturnApplyDTO.setReason( entity.getReason() );
        orderReturnApplyDTO.setDescription( entity.getDescription() );
        orderReturnApplyDTO.setProofPics( entity.getProofPics() );
        orderReturnApplyDTO.setHandleNote( entity.getHandleNote() );
        orderReturnApplyDTO.setHandleMan( entity.getHandleMan() );
        orderReturnApplyDTO.setReceiveMan( entity.getReceiveMan() );
        orderReturnApplyDTO.setReceiveTime( entity.getReceiveTime() );
        orderReturnApplyDTO.setReceiveNote( entity.getReceiveNote() );
        orderReturnApplyDTO.setStoreId( entity.getStoreId() );
        orderReturnApplyDTO.setAddTime( entity.getAddTime() );
        orderReturnApplyDTO.setModifyTime( entity.getModifyTime() );
        orderReturnApplyDTO.setDeleted( entity.getDeleted() );
        orderReturnApplyDTO.setOrderAmount( entity.getOrderAmount() );
        orderReturnApplyDTO.setCartInfo( entity.getCartInfo() );

        return orderReturnApplyDTO;
    }

    @Override
    public List<OrderReturnApply> toEntity(List<OrderReturnApplyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderReturnApply> list = new ArrayList<OrderReturnApply>( dtoList.size() );
        for ( OrderReturnApplyDTO orderReturnApplyDTO : dtoList ) {
            list.add( toEntity( orderReturnApplyDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderReturnApplyDTO> toDto(List<OrderReturnApply> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderReturnApplyDTO> list = new ArrayList<OrderReturnApplyDTO>( entityList.size() );
        for ( OrderReturnApply orderReturnApply : entityList ) {
            list.add( toDto( orderReturnApply ) );
        }

        return list;
    }
}
