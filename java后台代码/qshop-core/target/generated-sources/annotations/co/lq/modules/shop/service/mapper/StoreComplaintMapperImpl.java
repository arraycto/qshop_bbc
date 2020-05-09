package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.domain.StoreComplaint;
import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.dto.StoreComplaintDTO;
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
public class StoreComplaintMapperImpl implements StoreComplaintMapper {

    @Override
    public StoreComplaint toEntity(StoreComplaintDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreComplaint storeComplaint = new StoreComplaint();

        storeComplaint.setId( dto.getId() );
        storeComplaint.setUid( dto.getUid() );
        storeComplaint.setReason( dto.getReason() );
        storeComplaint.setDesc( dto.getDesc() );
        storeComplaint.setStoreId( dto.getStoreId() );
        storeComplaint.setAddTime( dto.getAddTime() );
        storeComplaint.setModifyTime( dto.getModifyTime() );
        storeComplaint.setDeleted( dto.getDeleted() );

        return storeComplaint;
    }

    @Override
    public List<StoreComplaint> toEntity(List<StoreComplaintDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreComplaint> list = new ArrayList<StoreComplaint>( dtoList.size() );
        for ( StoreComplaintDTO storeComplaintDTO : dtoList ) {
            list.add( toEntity( storeComplaintDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreComplaintDTO> toDto(List<StoreComplaint> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreComplaintDTO> list = new ArrayList<StoreComplaintDTO>( entityList.size() );
        for ( StoreComplaint storeComplaint : entityList ) {
            list.add( toDto( storeComplaint ) );
        }

        return list;
    }

    @Override
    public StoreComplaintDTO toDto(StoreComplaint storeComplaint) {
        if ( storeComplaint == null ) {
            return null;
        }

        StoreComplaintDTO storeComplaintDTO = new StoreComplaintDTO();

        String nickname = storeComplaintUserNickname( storeComplaint );
        if ( nickname != null ) {
            storeComplaintDTO.setNickname( nickname );
        }
        String name = storeComplaintShopName( storeComplaint );
        if ( name != null ) {
            storeComplaintDTO.setShopName( name );
        }
        storeComplaintDTO.setId( storeComplaint.getId() );
        storeComplaintDTO.setUid( storeComplaint.getUid() );
        storeComplaintDTO.setReason( storeComplaint.getReason() );
        storeComplaintDTO.setDesc( storeComplaint.getDesc() );
        storeComplaintDTO.setStoreId( storeComplaint.getStoreId() );
        storeComplaintDTO.setAddTime( storeComplaint.getAddTime() );
        storeComplaintDTO.setModifyTime( storeComplaint.getModifyTime() );
        storeComplaintDTO.setDeleted( storeComplaint.getDeleted() );

        return storeComplaintDTO;
    }

    private String storeComplaintUserNickname(StoreComplaint storeComplaint) {
        if ( storeComplaint == null ) {
            return null;
        }
        User user = storeComplaint.getUser();
        if ( user == null ) {
            return null;
        }
        String nickname = user.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }

    private String storeComplaintShopName(StoreComplaint storeComplaint) {
        if ( storeComplaint == null ) {
            return null;
        }
        Shop shop = storeComplaint.getShop();
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
