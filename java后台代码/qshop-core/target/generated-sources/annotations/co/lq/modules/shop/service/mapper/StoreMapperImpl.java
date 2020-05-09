package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.dto.ShopDTO;
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
public class StoreMapperImpl implements StoreMapper {

    @Override
    public Shop toEntity(ShopDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Shop shop = new Shop();

        shop.setId( dto.getId() );
        shop.setRegisterType( dto.getRegisterType() );
        shop.setExpireTime( dto.getExpireTime() );
        shop.setTryTime( dto.getTryTime() );
        shop.setContactMobile( dto.getContactMobile() );
        shop.setIsChecked( dto.getIsChecked() );
        shop.setServicePhone( dto.getServicePhone() );
        shop.setAddressLat( dto.getAddressLat() );
        shop.setContactName( dto.getContactName() );
        shop.setDeleteTime( dto.getDeleteTime() );
        shop.setIsStar( dto.getIsStar() );
        shop.setIsTry( dto.getIsTry() );
        shop.setLogo( dto.getLogo() );
        shop.setAddressDetail( dto.getAddressDetail() );
        shop.setName( dto.getName() );
        shop.setUid( dto.getUid() );
        shop.setContactQq( dto.getContactQq() );
        shop.setAddressLng( dto.getAddressLng() );
        shop.setLastLoginTime( dto.getLastLoginTime() );
        shop.setSupportPhone( dto.getSupportPhone() );
        shop.setContactQrcode( dto.getContactQrcode() );
        shop.setDescription( dto.getDescription() );
        shop.setCollect( dto.getCollect() );
        shop.setHit( dto.getHit() );
        shop.setGoodsCount( dto.getGoodsCount() );
        shop.setMemberCount( dto.getMemberCount() );
        shop.setOrderCount( dto.getOrderCount() );
        shop.setPayAmount( dto.getPayAmount() );
        shop.setArticleCount( dto.getArticleCount() );
        shop.setIsBoutique( dto.getIsBoutique() );
        shop.setAddTime( dto.getAddTime() );
        shop.setModifyTime( dto.getModifyTime() );
        shop.setDeleted( dto.getDeleted() );
        shop.setOwnerIdentity1( dto.getOwnerIdentity1() );
        shop.setOwnerIdentity2( dto.getOwnerIdentity2() );

        return shop;
    }

    @Override
    public ShopDTO toDto(Shop entity) {
        if ( entity == null ) {
            return null;
        }

        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setId( entity.getId() );
        shopDTO.setRegisterType( entity.getRegisterType() );
        shopDTO.setExpireTime( entity.getExpireTime() );
        shopDTO.setTryTime( entity.getTryTime() );
        shopDTO.setContactMobile( entity.getContactMobile() );
        shopDTO.setIsChecked( entity.getIsChecked() );
        shopDTO.setServicePhone( entity.getServicePhone() );
        shopDTO.setAddressLat( entity.getAddressLat() );
        shopDTO.setContactName( entity.getContactName() );
        shopDTO.setDeleteTime( entity.getDeleteTime() );
        shopDTO.setIsStar( entity.getIsStar() );
        shopDTO.setIsTry( entity.getIsTry() );
        shopDTO.setLogo( entity.getLogo() );
        shopDTO.setAddressDetail( entity.getAddressDetail() );
        shopDTO.setName( entity.getName() );
        shopDTO.setUid( entity.getUid() );
        shopDTO.setContactQq( entity.getContactQq() );
        shopDTO.setAddressLng( entity.getAddressLng() );
        shopDTO.setLastLoginTime( entity.getLastLoginTime() );
        shopDTO.setSupportPhone( entity.getSupportPhone() );
        shopDTO.setContactQrcode( entity.getContactQrcode() );
        shopDTO.setDescription( entity.getDescription() );
        shopDTO.setCollect( entity.getCollect() );
        shopDTO.setHit( entity.getHit() );
        shopDTO.setGoodsCount( entity.getGoodsCount() );
        shopDTO.setMemberCount( entity.getMemberCount() );
        shopDTO.setOrderCount( entity.getOrderCount() );
        shopDTO.setPayAmount( entity.getPayAmount() );
        shopDTO.setArticleCount( entity.getArticleCount() );
        shopDTO.setIsBoutique( entity.getIsBoutique() );
        shopDTO.setAddTime( entity.getAddTime() );
        shopDTO.setModifyTime( entity.getModifyTime() );
        shopDTO.setDeleted( entity.getDeleted() );
        shopDTO.setOwnerIdentity1( entity.getOwnerIdentity1() );
        shopDTO.setOwnerIdentity2( entity.getOwnerIdentity2() );

        return shopDTO;
    }

    @Override
    public List<Shop> toEntity(List<ShopDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Shop> list = new ArrayList<Shop>( dtoList.size() );
        for ( ShopDTO shopDTO : dtoList ) {
            list.add( toEntity( shopDTO ) );
        }

        return list;
    }

    @Override
    public List<ShopDTO> toDto(List<Shop> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ShopDTO> list = new ArrayList<ShopDTO>( entityList.size() );
        for ( Shop shop : entityList ) {
            list.add( toDto( shop ) );
        }

        return list;
    }
}
