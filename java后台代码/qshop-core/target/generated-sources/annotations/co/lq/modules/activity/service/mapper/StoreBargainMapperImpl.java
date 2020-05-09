package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreBargain;
import co.lq.modules.activity.service.dto.StoreBargainDTO;
import co.lq.modules.shop.service.param.StoreProductParam;
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
public class StoreBargainMapperImpl implements StoreBargainMapper {

    @Override
    public StoreBargain toEntity(StoreBargainDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreBargain storeBargain = new StoreBargain();

        storeBargain.setId( dto.getId() );
        storeBargain.setProductId( dto.getProductId() );
        storeBargain.setTitle( dto.getTitle() );
        storeBargain.setImage( dto.getImage() );
        storeBargain.setUnitName( dto.getUnitName() );
        storeBargain.setStock( dto.getStock() );
        storeBargain.setSales( dto.getSales() );
        storeBargain.setImages( dto.getImages() );
        storeBargain.setStartTime( dto.getStartTime() );
        storeBargain.setStopTime( dto.getStopTime() );
        storeBargain.setStartTimeDate( dto.getStartTimeDate() );
        storeBargain.setEndTimeDate( dto.getEndTimeDate() );
        storeBargain.setProductName( dto.getProductName() );
        storeBargain.setPrice( dto.getPrice() );
        storeBargain.setMinPrice( dto.getMinPrice() );
        storeBargain.setNum( dto.getNum() );
        storeBargain.setBargainMaxPrice( dto.getBargainMaxPrice() );
        storeBargain.setBargainMinPrice( dto.getBargainMinPrice() );
        storeBargain.setBargainNum( dto.getBargainNum() );
        storeBargain.setStatus( dto.getStatus() );
        storeBargain.setDescription( dto.getDescription() );
        storeBargain.setGiveIntegral( dto.getGiveIntegral() );
        storeBargain.setInfo( dto.getInfo() );
        storeBargain.setCost( dto.getCost() );
        storeBargain.setSort( dto.getSort() );
        storeBargain.setIsDel( dto.getIsDel() );
        storeBargain.setAddTime( dto.getAddTime() );
        storeBargain.setIsPostage( dto.getIsPostage() );
        storeBargain.setPostage( dto.getPostage() );
        storeBargain.setRule( dto.getRule() );
        storeBargain.setLook( dto.getLook() );
        storeBargain.setShare( dto.getShare() );
        storeBargain.setStoreId( dto.getStoreId() );
        if ( dto.getVerifyStatus() != null ) {
            storeBargain.setVerifyStatus( dto.getVerifyStatus().intValue() );
        }
        List<StoreProductParam> list = dto.getProductList();
        if ( list != null ) {
            storeBargain.setProductList( new ArrayList<StoreProductParam>( list ) );
        }
        else {
            storeBargain.setProductList( null );
        }

        return storeBargain;
    }

    @Override
    public StoreBargainDTO toDto(StoreBargain entity) {
        if ( entity == null ) {
            return null;
        }

        StoreBargainDTO storeBargainDTO = new StoreBargainDTO();

        storeBargainDTO.setId( entity.getId() );
        storeBargainDTO.setProductId( entity.getProductId() );
        storeBargainDTO.setTitle( entity.getTitle() );
        storeBargainDTO.setImage( entity.getImage() );
        storeBargainDTO.setUnitName( entity.getUnitName() );
        storeBargainDTO.setStock( entity.getStock() );
        storeBargainDTO.setSales( entity.getSales() );
        storeBargainDTO.setImages( entity.getImages() );
        storeBargainDTO.setStartTime( entity.getStartTime() );
        storeBargainDTO.setStopTime( entity.getStopTime() );
        storeBargainDTO.setProductName( entity.getProductName() );
        storeBargainDTO.setPrice( entity.getPrice() );
        storeBargainDTO.setMinPrice( entity.getMinPrice() );
        storeBargainDTO.setNum( entity.getNum() );
        storeBargainDTO.setBargainMaxPrice( entity.getBargainMaxPrice() );
        storeBargainDTO.setBargainMinPrice( entity.getBargainMinPrice() );
        storeBargainDTO.setBargainNum( entity.getBargainNum() );
        storeBargainDTO.setStatus( entity.getStatus() );
        storeBargainDTO.setDescription( entity.getDescription() );
        storeBargainDTO.setGiveIntegral( entity.getGiveIntegral() );
        storeBargainDTO.setInfo( entity.getInfo() );
        storeBargainDTO.setCost( entity.getCost() );
        storeBargainDTO.setSort( entity.getSort() );
        storeBargainDTO.setIsDel( entity.getIsDel() );
        storeBargainDTO.setAddTime( entity.getAddTime() );
        storeBargainDTO.setIsPostage( entity.getIsPostage() );
        storeBargainDTO.setPostage( entity.getPostage() );
        storeBargainDTO.setRule( entity.getRule() );
        storeBargainDTO.setLook( entity.getLook() );
        storeBargainDTO.setShare( entity.getShare() );
        storeBargainDTO.setStartTimeDate( entity.getStartTimeDate() );
        storeBargainDTO.setEndTimeDate( entity.getEndTimeDate() );
        storeBargainDTO.setStoreId( entity.getStoreId() );
        if ( entity.getVerifyStatus() != null ) {
            storeBargainDTO.setVerifyStatus( entity.getVerifyStatus().byteValue() );
        }
        List<StoreProductParam> list = entity.getProductList();
        if ( list != null ) {
            storeBargainDTO.setProductList( new ArrayList<StoreProductParam>( list ) );
        }
        else {
            storeBargainDTO.setProductList( null );
        }

        return storeBargainDTO;
    }

    @Override
    public List<StoreBargain> toEntity(List<StoreBargainDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreBargain> list = new ArrayList<StoreBargain>( dtoList.size() );
        for ( StoreBargainDTO storeBargainDTO : dtoList ) {
            list.add( toEntity( storeBargainDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreBargainDTO> toDto(List<StoreBargain> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreBargainDTO> list = new ArrayList<StoreBargainDTO>( entityList.size() );
        for ( StoreBargain storeBargain : entityList ) {
            list.add( toDto( storeBargain ) );
        }

        return list;
    }
}
