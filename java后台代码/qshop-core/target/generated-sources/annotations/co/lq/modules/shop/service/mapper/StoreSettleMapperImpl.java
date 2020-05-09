package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreSettle;
import co.lq.modules.shop.service.dto.StoreSettleDTO;
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
public class StoreSettleMapperImpl implements StoreSettleMapper {

    @Override
    public StoreSettle toEntity(StoreSettleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreSettle storeSettle = new StoreSettle();

        storeSettle.setId( dto.getId() );
        storeSettle.setStoreId( dto.getStoreId() );
        storeSettle.setCompanyName( dto.getCompanyName() );
        storeSettle.setLicenseNo( dto.getLicenseNo() );
        storeSettle.setRepresentative( dto.getRepresentative() );
        storeSettle.setIdNo( dto.getIdNo() );
        storeSettle.setRepresentIdentity1( dto.getRepresentIdentity1() );
        storeSettle.setRepresentIdentity2( dto.getRepresentIdentity2() );
        storeSettle.setLicenseStartTime( dto.getLicenseStartTime() );
        storeSettle.setLicenseEndTime( dto.getLicenseEndTime() );
        storeSettle.setArea( dto.getArea() );
        storeSettle.setAddressDetail( dto.getAddressDetail() );
        storeSettle.setTel( dto.getTel() );
        storeSettle.setContact( dto.getContact() );
        storeSettle.setPhone( dto.getPhone() );
        storeSettle.setCapital( dto.getCapital() );
        storeSettle.setWebsiteUrl( dto.getWebsiteUrl() );
        storeSettle.setAddTime( dto.getAddTime() );
        storeSettle.setModifyTime( dto.getModifyTime() );
        storeSettle.setDeleted( dto.getDeleted() );
        storeSettle.setStatus( dto.getStatus() );
        storeSettle.setClosed( dto.getClosed() );

        return storeSettle;
    }

    @Override
    public StoreSettleDTO toDto(StoreSettle entity) {
        if ( entity == null ) {
            return null;
        }

        StoreSettleDTO storeSettleDTO = new StoreSettleDTO();

        storeSettleDTO.setId( entity.getId() );
        storeSettleDTO.setStoreId( entity.getStoreId() );
        storeSettleDTO.setCompanyName( entity.getCompanyName() );
        storeSettleDTO.setLicenseNo( entity.getLicenseNo() );
        storeSettleDTO.setRepresentative( entity.getRepresentative() );
        storeSettleDTO.setIdNo( entity.getIdNo() );
        storeSettleDTO.setRepresentIdentity1( entity.getRepresentIdentity1() );
        storeSettleDTO.setRepresentIdentity2( entity.getRepresentIdentity2() );
        storeSettleDTO.setLicenseStartTime( entity.getLicenseStartTime() );
        storeSettleDTO.setLicenseEndTime( entity.getLicenseEndTime() );
        storeSettleDTO.setArea( entity.getArea() );
        storeSettleDTO.setAddressDetail( entity.getAddressDetail() );
        storeSettleDTO.setTel( entity.getTel() );
        storeSettleDTO.setContact( entity.getContact() );
        storeSettleDTO.setPhone( entity.getPhone() );
        storeSettleDTO.setCapital( entity.getCapital() );
        storeSettleDTO.setWebsiteUrl( entity.getWebsiteUrl() );
        storeSettleDTO.setAddTime( entity.getAddTime() );
        storeSettleDTO.setModifyTime( entity.getModifyTime() );
        storeSettleDTO.setDeleted( entity.getDeleted() );
        storeSettleDTO.setStatus( entity.getStatus() );
        storeSettleDTO.setClosed( entity.getClosed() );

        return storeSettleDTO;
    }

    @Override
    public List<StoreSettle> toEntity(List<StoreSettleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreSettle> list = new ArrayList<StoreSettle>( dtoList.size() );
        for ( StoreSettleDTO storeSettleDTO : dtoList ) {
            list.add( toEntity( storeSettleDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreSettleDTO> toDto(List<StoreSettle> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreSettleDTO> list = new ArrayList<StoreSettleDTO>( entityList.size() );
        for ( StoreSettle storeSettle : entityList ) {
            list.add( toDto( storeSettle ) );
        }

        return list;
    }
}
