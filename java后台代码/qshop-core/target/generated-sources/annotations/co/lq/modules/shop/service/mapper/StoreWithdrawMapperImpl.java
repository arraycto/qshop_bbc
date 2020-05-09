package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.domain.StoreWithdraw;
import co.lq.modules.shop.service.dto.StoreWithdrawDTO;
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
public class StoreWithdrawMapperImpl implements StoreWithdrawMapper {

    @Override
    public StoreWithdraw toEntity(StoreWithdrawDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreWithdraw storeWithdraw = new StoreWithdraw();

        storeWithdraw.setId( dto.getId() );
        storeWithdraw.setApplyAmount( dto.getApplyAmount() );
        storeWithdraw.setVerifyTime( dto.getVerifyTime() );
        storeWithdraw.setApplyStatus( dto.getApplyStatus() );
        storeWithdraw.setStoreId( dto.getStoreId() );
        storeWithdraw.setAddTime( dto.getAddTime() );
        storeWithdraw.setModifyTime( dto.getModifyTime() );
        storeWithdraw.setDeleted( dto.getDeleted() );

        return storeWithdraw;
    }

    @Override
    public List<StoreWithdraw> toEntity(List<StoreWithdrawDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreWithdraw> list = new ArrayList<StoreWithdraw>( dtoList.size() );
        for ( StoreWithdrawDTO storeWithdrawDTO : dtoList ) {
            list.add( toEntity( storeWithdrawDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreWithdrawDTO> toDto(List<StoreWithdraw> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreWithdrawDTO> list = new ArrayList<StoreWithdrawDTO>( entityList.size() );
        for ( StoreWithdraw storeWithdraw : entityList ) {
            list.add( toDto( storeWithdraw ) );
        }

        return list;
    }

    @Override
    public StoreWithdrawDTO toDto(StoreWithdraw StoreWithdraw) {
        if ( StoreWithdraw == null ) {
            return null;
        }

        StoreWithdrawDTO storeWithdrawDTO = new StoreWithdrawDTO();

        String name = storeWithdrawShopName( StoreWithdraw );
        if ( name != null ) {
            storeWithdrawDTO.setShopName( name );
        }
        storeWithdrawDTO.setId( StoreWithdraw.getId() );
        storeWithdrawDTO.setApplyAmount( StoreWithdraw.getApplyAmount() );
        storeWithdrawDTO.setVerifyTime( StoreWithdraw.getVerifyTime() );
        storeWithdrawDTO.setApplyStatus( StoreWithdraw.getApplyStatus() );
        storeWithdrawDTO.setStoreId( StoreWithdraw.getStoreId() );
        storeWithdrawDTO.setAddTime( StoreWithdraw.getAddTime() );
        storeWithdrawDTO.setModifyTime( StoreWithdraw.getModifyTime() );
        storeWithdrawDTO.setDeleted( StoreWithdraw.getDeleted() );

        return storeWithdrawDTO;
    }

    private String storeWithdrawShopName(StoreWithdraw storeWithdraw) {
        if ( storeWithdraw == null ) {
            return null;
        }
        Shop shop = storeWithdraw.getShop();
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
