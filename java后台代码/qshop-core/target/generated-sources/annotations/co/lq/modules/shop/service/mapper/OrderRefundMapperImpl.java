package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.OrderRefund;
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.dto.OrderRefundDTO;
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
public class OrderRefundMapperImpl implements OrderRefundMapper {

    @Override
    public OrderRefund toEntity(OrderRefundDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OrderRefund orderRefund = new OrderRefund();

        orderRefund.setId( dto.getId() );
        orderRefund.setOrderReturnId( dto.getOrderReturnId() );
        orderRefund.setOrderId( dto.getOrderId() );
        orderRefund.setStatus( dto.getStatus() );
        orderRefund.setOrderAmount( dto.getOrderAmount() );
        orderRefund.setReturnAmount( dto.getReturnAmount() );
        orderRefund.setRealAmount( dto.getRealAmount() );
        orderRefund.setReason( dto.getReason() );
        orderRefund.setHandleMan( dto.getHandleMan() );
        orderRefund.setStoreId( dto.getStoreId() );
        orderRefund.setAddTime( dto.getAddTime() );
        orderRefund.setModifyTime( dto.getModifyTime() );
        orderRefund.setDeleted( dto.getDeleted() );
        orderRefund.setVerifyStatus( dto.getVerifyStatus() );
        orderRefund.setRefundTime( dto.getRefundTime() );

        return orderRefund;
    }

    @Override
    public List<OrderRefund> toEntity(List<OrderRefundDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OrderRefund> list = new ArrayList<OrderRefund>( dtoList.size() );
        for ( OrderRefundDTO orderRefundDTO : dtoList ) {
            list.add( toEntity( orderRefundDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderRefundDTO> toDto(List<OrderRefund> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OrderRefundDTO> list = new ArrayList<OrderRefundDTO>( entityList.size() );
        for ( OrderRefund orderRefund : entityList ) {
            list.add( toDto( orderRefund ) );
        }

        return list;
    }

    @Override
    public OrderRefundDTO toDto(OrderRefund orderRefund) {
        if ( orderRefund == null ) {
            return null;
        }

        OrderRefundDTO orderRefundDTO = new OrderRefundDTO();

        String name = orderRefundShopName( orderRefund );
        if ( name != null ) {
            orderRefundDTO.setShopName( name );
        }
        orderRefundDTO.setId( orderRefund.getId() );
        orderRefundDTO.setOrderReturnId( orderRefund.getOrderReturnId() );
        orderRefundDTO.setOrderId( orderRefund.getOrderId() );
        orderRefundDTO.setStatus( orderRefund.getStatus() );
        orderRefundDTO.setOrderAmount( orderRefund.getOrderAmount() );
        orderRefundDTO.setReturnAmount( orderRefund.getReturnAmount() );
        orderRefundDTO.setRealAmount( orderRefund.getRealAmount() );
        orderRefundDTO.setReason( orderRefund.getReason() );
        orderRefundDTO.setHandleMan( orderRefund.getHandleMan() );
        orderRefundDTO.setStoreId( orderRefund.getStoreId() );
        orderRefundDTO.setAddTime( orderRefund.getAddTime() );
        orderRefundDTO.setModifyTime( orderRefund.getModifyTime() );
        orderRefundDTO.setDeleted( orderRefund.getDeleted() );
        orderRefundDTO.setVerifyStatus( orderRefund.getVerifyStatus() );
        orderRefundDTO.setRefundTime( orderRefund.getRefundTime() );

        return orderRefundDTO;
    }

    private String orderRefundShopName(OrderRefund orderRefund) {
        if ( orderRefund == null ) {
            return null;
        }
        Shop shop = orderRefund.getShop();
        if ( shop == null ) {
            return null;
        }
        String name = shop.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
