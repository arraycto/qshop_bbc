package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreOrder;
import co.lq.modules.shop.service.dto.StoreOrderDTO;
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
public class StoreOrderMapperImpl implements StoreOrderMapper {

    @Override
    public StoreOrder toEntity(StoreOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreOrder storeOrder = new StoreOrder();

        storeOrder.setId( dto.getId() );
        storeOrder.setOrderId( dto.getOrderId() );
        storeOrder.setUid( dto.getUid() );
        storeOrder.setRealName( dto.getRealName() );
        storeOrder.setUserPhone( dto.getUserPhone() );
        storeOrder.setUserAddress( dto.getUserAddress() );
        storeOrder.setCartId( dto.getCartId() );
        storeOrder.setFreightPrice( dto.getFreightPrice() );
        storeOrder.setTotalNum( dto.getTotalNum() );
        storeOrder.setTotalPrice( dto.getTotalPrice() );
        storeOrder.setTotalPostage( dto.getTotalPostage() );
        storeOrder.setPayPrice( dto.getPayPrice() );
        storeOrder.setPayPostage( dto.getPayPostage() );
        storeOrder.setDeductionPrice( dto.getDeductionPrice() );
        storeOrder.setCouponId( dto.getCouponId() );
        storeOrder.setCouponPrice( dto.getCouponPrice() );
        storeOrder.setPaid( dto.getPaid() );
        storeOrder.setPayTime( dto.getPayTime() );
        storeOrder.setPayType( dto.getPayType() );
        storeOrder.setAddTime( dto.getAddTime() );
        storeOrder.setStatus( dto.getStatus() );
        storeOrder.setRefundStatus( dto.getRefundStatus() );
        storeOrder.setRefundReasonWapImg( dto.getRefundReasonWapImg() );
        storeOrder.setRefundReasonWapExplain( dto.getRefundReasonWapExplain() );
        storeOrder.setRefundReasonTime( dto.getRefundReasonTime() );
        storeOrder.setRefundReasonWap( dto.getRefundReasonWap() );
        storeOrder.setRefundReason( dto.getRefundReason() );
        storeOrder.setRefundPrice( dto.getRefundPrice() );
        storeOrder.setDeliveryName( dto.getDeliveryName() );
        storeOrder.setDeliverySn( dto.getDeliverySn() );
        storeOrder.setDeliveryType( dto.getDeliveryType() );
        storeOrder.setDeliveryId( dto.getDeliveryId() );
        storeOrder.setGainIntegral( dto.getGainIntegral() );
        storeOrder.setUseIntegral( dto.getUseIntegral() );
        storeOrder.setBackIntegral( dto.getBackIntegral() );
        storeOrder.setMark( dto.getMark() );
        storeOrder.setIsDel( dto.getIsDel() );
        storeOrder.setUnique( dto.getUnique() );
        storeOrder.setRemark( dto.getRemark() );
        storeOrder.setMerId( dto.getMerId() );
        storeOrder.setIsMerCheck( dto.getIsMerCheck() );
        storeOrder.setCombinationId( dto.getCombinationId() );
        storeOrder.setPinkId( dto.getPinkId() );
        storeOrder.setCost( dto.getCost() );
        storeOrder.setSeckillId( dto.getSeckillId() );
        storeOrder.setBargainId( dto.getBargainId() );
        storeOrder.setVerifyCode( dto.getVerifyCode() );
        storeOrder.setStoreId( dto.getStoreId() );
        storeOrder.setShippingType( dto.getShippingType() );
        storeOrder.setIsChannel( dto.getIsChannel() );
        storeOrder.setIsRemind( dto.getIsRemind() );
        storeOrder.setIsSystemDel( dto.getIsSystemDel() );
        storeOrder.setShopId( dto.getShopId() );
        storeOrder.setIntegralId( dto.getIntegralId() );
        storeOrder.setPromotionPrice( dto.getPromotionPrice() );
        storeOrder.setStorePromotionPrice( dto.getStorePromotionPrice() );
        storeOrder.setCommission( dto.getCommission() );
        storeOrder.setChargePrice( dto.getChargePrice() );
        storeOrder.setSettlementStatus( dto.getSettlementStatus() );

        return storeOrder;
    }

    @Override
    public StoreOrderDTO toDto(StoreOrder entity) {
        if ( entity == null ) {
            return null;
        }

        StoreOrderDTO storeOrderDTO = new StoreOrderDTO();

        storeOrderDTO.setId( entity.getId() );
        storeOrderDTO.setOrderId( entity.getOrderId() );
        storeOrderDTO.setUid( entity.getUid() );
        storeOrderDTO.setRealName( entity.getRealName() );
        storeOrderDTO.setUserPhone( entity.getUserPhone() );
        storeOrderDTO.setUserAddress( entity.getUserAddress() );
        storeOrderDTO.setCartId( entity.getCartId() );
        storeOrderDTO.setFreightPrice( entity.getFreightPrice() );
        storeOrderDTO.setTotalNum( entity.getTotalNum() );
        storeOrderDTO.setTotalPrice( entity.getTotalPrice() );
        storeOrderDTO.setTotalPostage( entity.getTotalPostage() );
        storeOrderDTO.setPayPrice( entity.getPayPrice() );
        storeOrderDTO.setPayPostage( entity.getPayPostage() );
        storeOrderDTO.setDeductionPrice( entity.getDeductionPrice() );
        storeOrderDTO.setCouponId( entity.getCouponId() );
        storeOrderDTO.setCouponPrice( entity.getCouponPrice() );
        storeOrderDTO.setPaid( entity.getPaid() );
        storeOrderDTO.setPayTime( entity.getPayTime() );
        storeOrderDTO.setPayType( entity.getPayType() );
        storeOrderDTO.setAddTime( entity.getAddTime() );
        storeOrderDTO.setStatus( entity.getStatus() );
        storeOrderDTO.setRefundStatus( entity.getRefundStatus() );
        storeOrderDTO.setRefundReasonWapImg( entity.getRefundReasonWapImg() );
        storeOrderDTO.setRefundReasonWapExplain( entity.getRefundReasonWapExplain() );
        storeOrderDTO.setRefundReasonTime( entity.getRefundReasonTime() );
        storeOrderDTO.setRefundReasonWap( entity.getRefundReasonWap() );
        storeOrderDTO.setRefundReason( entity.getRefundReason() );
        storeOrderDTO.setRefundPrice( entity.getRefundPrice() );
        storeOrderDTO.setDeliveryName( entity.getDeliveryName() );
        storeOrderDTO.setDeliverySn( entity.getDeliverySn() );
        storeOrderDTO.setDeliveryType( entity.getDeliveryType() );
        storeOrderDTO.setDeliveryId( entity.getDeliveryId() );
        storeOrderDTO.setGainIntegral( entity.getGainIntegral() );
        storeOrderDTO.setUseIntegral( entity.getUseIntegral() );
        storeOrderDTO.setBackIntegral( entity.getBackIntegral() );
        storeOrderDTO.setMark( entity.getMark() );
        storeOrderDTO.setIsDel( entity.getIsDel() );
        storeOrderDTO.setUnique( entity.getUnique() );
        storeOrderDTO.setRemark( entity.getRemark() );
        storeOrderDTO.setMerId( entity.getMerId() );
        storeOrderDTO.setIsMerCheck( entity.getIsMerCheck() );
        storeOrderDTO.setCombinationId( entity.getCombinationId() );
        storeOrderDTO.setPinkId( entity.getPinkId() );
        storeOrderDTO.setCost( entity.getCost() );
        storeOrderDTO.setSeckillId( entity.getSeckillId() );
        storeOrderDTO.setBargainId( entity.getBargainId() );
        storeOrderDTO.setVerifyCode( entity.getVerifyCode() );
        storeOrderDTO.setStoreId( entity.getStoreId() );
        storeOrderDTO.setShippingType( entity.getShippingType() );
        storeOrderDTO.setIsChannel( entity.getIsChannel() );
        storeOrderDTO.setIsRemind( entity.getIsRemind() );
        storeOrderDTO.setIsSystemDel( entity.getIsSystemDel() );
        storeOrderDTO.setShopId( entity.getShopId() );
        storeOrderDTO.setIntegralId( entity.getIntegralId() );
        storeOrderDTO.setPromotionPrice( entity.getPromotionPrice() );
        storeOrderDTO.setStorePromotionPrice( entity.getStorePromotionPrice() );
        storeOrderDTO.setCommission( entity.getCommission() );
        storeOrderDTO.setChargePrice( entity.getChargePrice() );
        storeOrderDTO.setSettlementStatus( entity.getSettlementStatus() );

        return storeOrderDTO;
    }

    @Override
    public List<StoreOrder> toEntity(List<StoreOrderDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreOrder> list = new ArrayList<StoreOrder>( dtoList.size() );
        for ( StoreOrderDTO storeOrderDTO : dtoList ) {
            list.add( toEntity( storeOrderDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreOrderDTO> toDto(List<StoreOrder> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreOrderDTO> list = new ArrayList<StoreOrderDTO>( entityList.size() );
        for ( StoreOrder storeOrder : entityList ) {
            list.add( toDto( storeOrder ) );
        }

        return list;
    }
}
