package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.MemberSharedProduct;
import co.lq.modules.shop.service.dto.MemberSharedProductDTO;
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
public class MemberSharedProductMapperImpl implements MemberSharedProductMapper {

    @Override
    public MemberSharedProduct toEntity(MemberSharedProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MemberSharedProduct memberSharedProduct = new MemberSharedProduct();

        memberSharedProduct.setId( dto.getId() );
        memberSharedProduct.setMerId( dto.getMerId() );
        memberSharedProduct.setVipPrice( dto.getVipPrice() );
        memberSharedProduct.setStoreId( dto.getStoreId() );
        memberSharedProduct.setStatus( dto.getStatus() );
        memberSharedProduct.setDeleted( dto.getDeleted() );

        return memberSharedProduct;
    }

    @Override
    public MemberSharedProductDTO toDto(MemberSharedProduct entity) {
        if ( entity == null ) {
            return null;
        }

        MemberSharedProductDTO memberSharedProductDTO = new MemberSharedProductDTO();

        memberSharedProductDTO.setId( entity.getId() );
        memberSharedProductDTO.setMerId( entity.getMerId() );
        memberSharedProductDTO.setVipPrice( entity.getVipPrice() );
        memberSharedProductDTO.setStoreId( entity.getStoreId() );
        memberSharedProductDTO.setStatus( entity.getStatus() );
        memberSharedProductDTO.setDeleted( entity.getDeleted() );

        return memberSharedProductDTO;
    }

    @Override
    public List<MemberSharedProduct> toEntity(List<MemberSharedProductDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MemberSharedProduct> list = new ArrayList<MemberSharedProduct>( dtoList.size() );
        for ( MemberSharedProductDTO memberSharedProductDTO : dtoList ) {
            list.add( toEntity( memberSharedProductDTO ) );
        }

        return list;
    }

    @Override
    public List<MemberSharedProductDTO> toDto(List<MemberSharedProduct> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MemberSharedProductDTO> list = new ArrayList<MemberSharedProductDTO>( entityList.size() );
        for ( MemberSharedProduct memberSharedProduct : entityList ) {
            list.add( toDto( memberSharedProduct ) );
        }

        return list;
    }
}
