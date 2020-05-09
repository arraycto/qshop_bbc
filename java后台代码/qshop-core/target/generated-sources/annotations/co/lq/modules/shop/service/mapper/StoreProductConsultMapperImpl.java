package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreProductConsult;
import co.lq.modules.shop.service.dto.StoreProductConsultDTO;
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
public class StoreProductConsultMapperImpl implements StoreProductConsultMapper {

    @Override
    public StoreProductConsult toEntity(StoreProductConsultDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreProductConsult storeProductConsult = new StoreProductConsult();

        storeProductConsult.setId( dto.getId() );
        storeProductConsult.setProductId( dto.getProductId() );
        storeProductConsult.setProductName( dto.getProductName() );
        storeProductConsult.setUid( dto.getUid() );
        storeProductConsult.setMemberName( dto.getMemberName() );
        storeProductConsult.setStoreId( dto.getStoreId() );
        storeProductConsult.setEmail( dto.getEmail() );
        storeProductConsult.setConsultContent( dto.getConsultContent() );
        storeProductConsult.setConsultAddtime( dto.getConsultAddtime() );
        storeProductConsult.setConsultReply( dto.getConsultReply() );
        storeProductConsult.setConsultReplyTime( dto.getConsultReplyTime() );
        storeProductConsult.setIsanonymous( dto.getIsanonymous() );
        storeProductConsult.setIsDel( dto.getIsDel() );
        storeProductConsult.setPic( dto.getPic() );
        storeProductConsult.setAttr( dto.getAttr() );
        storeProductConsult.setStars( dto.getStars() );
        storeProductConsult.setOid( dto.getOid() );
        storeProductConsult.setType( dto.getType() );
        storeProductConsult.setAddTime( dto.getAddTime() );
        storeProductConsult.setModifyTime( dto.getModifyTime() );
        storeProductConsult.setDeleted( dto.getDeleted() );

        return storeProductConsult;
    }

    @Override
    public StoreProductConsultDTO toDto(StoreProductConsult entity) {
        if ( entity == null ) {
            return null;
        }

        StoreProductConsultDTO storeProductConsultDTO = new StoreProductConsultDTO();

        storeProductConsultDTO.setId( entity.getId() );
        storeProductConsultDTO.setProductId( entity.getProductId() );
        storeProductConsultDTO.setProductName( entity.getProductName() );
        storeProductConsultDTO.setUid( entity.getUid() );
        storeProductConsultDTO.setMemberName( entity.getMemberName() );
        storeProductConsultDTO.setStoreId( entity.getStoreId() );
        storeProductConsultDTO.setEmail( entity.getEmail() );
        storeProductConsultDTO.setConsultContent( entity.getConsultContent() );
        storeProductConsultDTO.setConsultAddtime( entity.getConsultAddtime() );
        storeProductConsultDTO.setConsultReply( entity.getConsultReply() );
        storeProductConsultDTO.setConsultReplyTime( entity.getConsultReplyTime() );
        storeProductConsultDTO.setIsanonymous( entity.getIsanonymous() );
        storeProductConsultDTO.setIsDel( entity.getIsDel() );
        storeProductConsultDTO.setPic( entity.getPic() );
        storeProductConsultDTO.setAttr( entity.getAttr() );
        storeProductConsultDTO.setStars( entity.getStars() );
        storeProductConsultDTO.setOid( entity.getOid() );
        storeProductConsultDTO.setType( entity.getType() );
        storeProductConsultDTO.setAddTime( entity.getAddTime() );
        storeProductConsultDTO.setModifyTime( entity.getModifyTime() );
        storeProductConsultDTO.setDeleted( entity.getDeleted() );

        return storeProductConsultDTO;
    }

    @Override
    public List<StoreProductConsult> toEntity(List<StoreProductConsultDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreProductConsult> list = new ArrayList<StoreProductConsult>( dtoList.size() );
        for ( StoreProductConsultDTO storeProductConsultDTO : dtoList ) {
            list.add( toEntity( storeProductConsultDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreProductConsultDTO> toDto(List<StoreProductConsult> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreProductConsultDTO> list = new ArrayList<StoreProductConsultDTO>( entityList.size() );
        for ( StoreProductConsult storeProductConsult : entityList ) {
            list.add( toDto( storeProductConsult ) );
        }

        return list;
    }
}
