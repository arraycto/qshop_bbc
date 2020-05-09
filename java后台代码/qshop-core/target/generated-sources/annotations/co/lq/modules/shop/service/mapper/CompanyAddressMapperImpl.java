package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CompanyAddress;
import co.lq.modules.shop.service.dto.CompanyAddressDTO;
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
public class CompanyAddressMapperImpl implements CompanyAddressMapper {

    @Override
    public CompanyAddress toEntity(CompanyAddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CompanyAddress companyAddress = new CompanyAddress();

        companyAddress.setId( dto.getId() );
        companyAddress.setAddressName( dto.getAddressName() );
        companyAddress.setSendStatus( dto.getSendStatus() );
        companyAddress.setReceiveStatus( dto.getReceiveStatus() );
        companyAddress.setName( dto.getName() );
        companyAddress.setPhone( dto.getPhone() );
        companyAddress.setProvince( dto.getProvince() );
        companyAddress.setCity( dto.getCity() );
        companyAddress.setRegion( dto.getRegion() );
        companyAddress.setDetailAddress( dto.getDetailAddress() );
        companyAddress.setStoreId( dto.getStoreId() );
        companyAddress.setAddTime( dto.getAddTime() );
        companyAddress.setModifyTime( dto.getModifyTime() );
        companyAddress.setDeleted( dto.getDeleted() );

        return companyAddress;
    }

    @Override
    public CompanyAddressDTO toDto(CompanyAddress entity) {
        if ( entity == null ) {
            return null;
        }

        CompanyAddressDTO companyAddressDTO = new CompanyAddressDTO();

        companyAddressDTO.setId( entity.getId() );
        companyAddressDTO.setAddressName( entity.getAddressName() );
        companyAddressDTO.setSendStatus( entity.getSendStatus() );
        companyAddressDTO.setReceiveStatus( entity.getReceiveStatus() );
        companyAddressDTO.setName( entity.getName() );
        companyAddressDTO.setPhone( entity.getPhone() );
        companyAddressDTO.setProvince( entity.getProvince() );
        companyAddressDTO.setCity( entity.getCity() );
        companyAddressDTO.setRegion( entity.getRegion() );
        companyAddressDTO.setDetailAddress( entity.getDetailAddress() );
        companyAddressDTO.setStoreId( entity.getStoreId() );
        companyAddressDTO.setAddTime( entity.getAddTime() );
        companyAddressDTO.setModifyTime( entity.getModifyTime() );
        companyAddressDTO.setDeleted( entity.getDeleted() );

        return companyAddressDTO;
    }

    @Override
    public List<CompanyAddress> toEntity(List<CompanyAddressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CompanyAddress> list = new ArrayList<CompanyAddress>( dtoList.size() );
        for ( CompanyAddressDTO companyAddressDTO : dtoList ) {
            list.add( toEntity( companyAddressDTO ) );
        }

        return list;
    }

    @Override
    public List<CompanyAddressDTO> toDto(List<CompanyAddress> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CompanyAddressDTO> list = new ArrayList<CompanyAddressDTO>( entityList.size() );
        for ( CompanyAddress companyAddress : entityList ) {
            list.add( toDto( companyAddress ) );
        }

        return list;
    }
}
