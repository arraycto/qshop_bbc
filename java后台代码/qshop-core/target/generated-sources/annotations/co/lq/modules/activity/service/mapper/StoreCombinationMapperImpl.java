package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreCombination;
import co.lq.modules.activity.service.dto.StoreCombinationDTO;
import co.lq.modules.shop.service.param.StoreProductParam;
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
public class StoreCombinationMapperImpl implements StoreCombinationMapper {

    @Override
    public StoreCombination toEntity(StoreCombinationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCombination storeCombination = new StoreCombination();

        storeCombination.setId( dto.getId() );
        storeCombination.setProductId( dto.getProductId() );
        storeCombination.setImage( dto.getImage() );
        storeCombination.setImages( dto.getImages() );
        storeCombination.setTitle( dto.getTitle() );
        storeCombination.setAttr( dto.getAttr() );
        storeCombination.setStatus( dto.getStatus() );
        storeCombination.setPeople( dto.getPeople() );
        storeCombination.setInfo( dto.getInfo() );
        storeCombination.setPrice( dto.getPrice() );
        storeCombination.setSort( dto.getSort() );
        storeCombination.setSales( dto.getSales() );
        storeCombination.setStock( dto.getStock() );
        storeCombination.setAddTime( dto.getAddTime() );
        storeCombination.setIsShow( dto.getIsShow() );
        storeCombination.setIsDel( dto.getIsDel() );
        storeCombination.setCombination( dto.getCombination() );
        storeCombination.setIsPostage( dto.getIsPostage() );
        storeCombination.setPostage( dto.getPostage() );
        storeCombination.setDescription( dto.getDescription() );
        storeCombination.setStartTime( dto.getStartTime() );
        storeCombination.setStopTime( dto.getStopTime() );
        storeCombination.setStartTimeDate( dto.getStartTimeDate() );
        storeCombination.setEndTimeDate( dto.getEndTimeDate() );
        storeCombination.setEffectiveTime( dto.getEffectiveTime() );
        storeCombination.setCost( dto.getCost() );
        storeCombination.setBrowse( dto.getBrowse() );
        storeCombination.setUnitName( dto.getUnitName() );
        storeCombination.setStoreId( dto.getStoreId() );
        if ( dto.getVerifyStatus() != null ) {
            storeCombination.setVerifyStatus( dto.getVerifyStatus().intValue() );
        }
        List<StoreProductParam> list = dto.getProductList();
        if ( list != null ) {
            storeCombination.setProductList( new ArrayList<StoreProductParam>( list ) );
        }
        else {
            storeCombination.setProductList( null );
        }

        return storeCombination;
    }

    @Override
    public StoreCombinationDTO toDto(StoreCombination entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCombinationDTO storeCombinationDTO = new StoreCombinationDTO();

        storeCombinationDTO.setId( entity.getId() );
        storeCombinationDTO.setProductId( entity.getProductId() );
        storeCombinationDTO.setImage( entity.getImage() );
        storeCombinationDTO.setImages( entity.getImages() );
        storeCombinationDTO.setTitle( entity.getTitle() );
        storeCombinationDTO.setAttr( entity.getAttr() );
        storeCombinationDTO.setPeople( entity.getPeople() );
        storeCombinationDTO.setInfo( entity.getInfo() );
        storeCombinationDTO.setPrice( entity.getPrice() );
        storeCombinationDTO.setSort( entity.getSort() );
        storeCombinationDTO.setSales( entity.getSales() );
        storeCombinationDTO.setStock( entity.getStock() );
        storeCombinationDTO.setAddTime( entity.getAddTime() );
        storeCombinationDTO.setIsShow( entity.getIsShow() );
        storeCombinationDTO.setIsDel( entity.getIsDel() );
        storeCombinationDTO.setCombination( entity.getCombination() );
        storeCombinationDTO.setIsPostage( entity.getIsPostage() );
        storeCombinationDTO.setPostage( entity.getPostage() );
        storeCombinationDTO.setDescription( entity.getDescription() );
        storeCombinationDTO.setStartTime( entity.getStartTime() );
        storeCombinationDTO.setStopTime( entity.getStopTime() );
        storeCombinationDTO.setStartTimeDate( entity.getStartTimeDate() );
        storeCombinationDTO.setEndTimeDate( entity.getEndTimeDate() );
        storeCombinationDTO.setEffectiveTime( entity.getEffectiveTime() );
        storeCombinationDTO.setCost( entity.getCost() );
        storeCombinationDTO.setBrowse( entity.getBrowse() );
        storeCombinationDTO.setUnitName( entity.getUnitName() );
        storeCombinationDTO.setStoreId( entity.getStoreId() );
        if ( entity.getVerifyStatus() != null ) {
            storeCombinationDTO.setVerifyStatus( entity.getVerifyStatus().byteValue() );
        }
        storeCombinationDTO.setStatus( entity.getStatus() );
        List<StoreProductParam> list = entity.getProductList();
        if ( list != null ) {
            storeCombinationDTO.setProductList( new ArrayList<StoreProductParam>( list ) );
        }
        else {
            storeCombinationDTO.setProductList( null );
        }

        return storeCombinationDTO;
    }

    @Override
    public List<StoreCombination> toEntity(List<StoreCombinationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCombination> list = new ArrayList<StoreCombination>( dtoList.size() );
        for ( StoreCombinationDTO storeCombinationDTO : dtoList ) {
            list.add( toEntity( storeCombinationDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCombinationDTO> toDto(List<StoreCombination> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCombinationDTO> list = new ArrayList<StoreCombinationDTO>( entityList.size() );
        for ( StoreCombination storeCombination : entityList ) {
            list.add( toDto( storeCombination ) );
        }

        return list;
    }
}
