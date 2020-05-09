package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreSeckill;
import co.lq.modules.activity.service.dto.StoreSeckillDTO;
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
public class StoreSeckillMapperImpl implements StoreSeckillMapper {

    @Override
    public StoreSeckill toEntity(StoreSeckillDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreSeckill storeSeckill = new StoreSeckill();

        storeSeckill.setId( dto.getId() );
        storeSeckill.setProductId( dto.getProductId() );
        storeSeckill.setImage( dto.getImage() );
        storeSeckill.setImages( dto.getImages() );
        storeSeckill.setTitle( dto.getTitle() );
        storeSeckill.setInfo( dto.getInfo() );
        storeSeckill.setPrice( dto.getPrice() );
        storeSeckill.setCost( dto.getCost() );
        storeSeckill.setOtPrice( dto.getOtPrice() );
        storeSeckill.setGiveIntegral( dto.getGiveIntegral() );
        storeSeckill.setSort( dto.getSort() );
        storeSeckill.setStock( dto.getStock() );
        storeSeckill.setSales( dto.getSales() );
        storeSeckill.setUnitName( dto.getUnitName() );
        storeSeckill.setPostage( dto.getPostage() );
        storeSeckill.setDescription( dto.getDescription() );
        storeSeckill.setStartTime( dto.getStartTime() );
        storeSeckill.setStopTime( dto.getStopTime() );
        storeSeckill.setStartTimeDate( dto.getStartTimeDate() );
        storeSeckill.setEndTimeDate( dto.getEndTimeDate() );
        storeSeckill.setAddTime( dto.getAddTime() );
        storeSeckill.setStatus( dto.getStatus() );
        storeSeckill.setIsPostage( dto.getIsPostage() );
        storeSeckill.setIsDel( dto.getIsDel() );
        storeSeckill.setNum( dto.getNum() );
        storeSeckill.setIsShow( dto.getIsShow() );
        storeSeckill.setStoreId( dto.getStoreId() );
        if ( dto.getVerifyStatus() != null ) {
            storeSeckill.setVerifyStatus( dto.getVerifyStatus().intValue() );
        }
        List<StoreProductParam> list = dto.getProductList();
        if ( list != null ) {
            storeSeckill.setProductList( new ArrayList<StoreProductParam>( list ) );
        }
        else {
            storeSeckill.setProductList( null );
        }

        return storeSeckill;
    }

    @Override
    public StoreSeckillDTO toDto(StoreSeckill entity) {
        if ( entity == null ) {
            return null;
        }

        StoreSeckillDTO storeSeckillDTO = new StoreSeckillDTO();

        storeSeckillDTO.setId( entity.getId() );
        storeSeckillDTO.setProductId( entity.getProductId() );
        storeSeckillDTO.setImage( entity.getImage() );
        storeSeckillDTO.setImages( entity.getImages() );
        storeSeckillDTO.setTitle( entity.getTitle() );
        storeSeckillDTO.setInfo( entity.getInfo() );
        storeSeckillDTO.setPrice( entity.getPrice() );
        storeSeckillDTO.setCost( entity.getCost() );
        storeSeckillDTO.setOtPrice( entity.getOtPrice() );
        storeSeckillDTO.setGiveIntegral( entity.getGiveIntegral() );
        storeSeckillDTO.setSort( entity.getSort() );
        storeSeckillDTO.setStock( entity.getStock() );
        storeSeckillDTO.setSales( entity.getSales() );
        storeSeckillDTO.setUnitName( entity.getUnitName() );
        storeSeckillDTO.setPostage( entity.getPostage() );
        storeSeckillDTO.setDescription( entity.getDescription() );
        storeSeckillDTO.setStartTime( entity.getStartTime() );
        storeSeckillDTO.setStopTime( entity.getStopTime() );
        storeSeckillDTO.setAddTime( entity.getAddTime() );
        storeSeckillDTO.setStatus( entity.getStatus() );
        storeSeckillDTO.setIsPostage( entity.getIsPostage() );
        storeSeckillDTO.setIsDel( entity.getIsDel() );
        storeSeckillDTO.setNum( entity.getNum() );
        storeSeckillDTO.setIsShow( entity.getIsShow() );
        storeSeckillDTO.setStartTimeDate( entity.getStartTimeDate() );
        storeSeckillDTO.setEndTimeDate( entity.getEndTimeDate() );
        storeSeckillDTO.setStoreId( entity.getStoreId() );
        if ( entity.getVerifyStatus() != null ) {
            storeSeckillDTO.setVerifyStatus( entity.getVerifyStatus().byteValue() );
        }
        List<StoreProductParam> list = entity.getProductList();
        if ( list != null ) {
            storeSeckillDTO.setProductList( new ArrayList<StoreProductParam>( list ) );
        }
        else {
            storeSeckillDTO.setProductList( null );
        }

        return storeSeckillDTO;
    }

    @Override
    public List<StoreSeckill> toEntity(List<StoreSeckillDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreSeckill> list = new ArrayList<StoreSeckill>( dtoList.size() );
        for ( StoreSeckillDTO storeSeckillDTO : dtoList ) {
            list.add( toEntity( storeSeckillDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreSeckillDTO> toDto(List<StoreSeckill> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreSeckillDTO> list = new ArrayList<StoreSeckillDTO>( entityList.size() );
        for ( StoreSeckill storeSeckill : entityList ) {
            list.add( toDto( storeSeckill ) );
        }

        return list;
    }
}
