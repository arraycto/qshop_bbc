package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.StoreProductReply;
import co.lq.modules.shop.service.dto.StoreProductReplyDTO;
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
public class StoreProductReplyMapperImpl implements StoreProductReplyMapper {

    @Override
    public StoreProductReply toEntity(StoreProductReplyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreProductReply storeProductReply = new StoreProductReply();

        storeProductReply.setId( dto.getId() );
        if ( dto.getUid() != null ) {
            storeProductReply.setUid( dto.getUid().longValue() );
        }
        if ( dto.getOid() != null ) {
            storeProductReply.setOid( dto.getOid().longValue() );
        }
        storeProductReply.setUnique( dto.getUnique() );
        storeProductReply.setProductId( dto.getProductId() );
        storeProductReply.setReplyType( dto.getReplyType() );
        storeProductReply.setProductScore( dto.getProductScore() );
        storeProductReply.setServiceScore( dto.getServiceScore() );
        storeProductReply.setComment( dto.getComment() );
        storeProductReply.setPics( dto.getPics() );
        storeProductReply.setAddTime( dto.getAddTime() );
        storeProductReply.setMerchantReplyContent( dto.getMerchantReplyContent() );
        storeProductReply.setMerchantReplyTime( dto.getMerchantReplyTime() );
        storeProductReply.setIsDel( dto.getIsDel() );
        storeProductReply.setIsReply( dto.getIsReply() );
        storeProductReply.setStoreId( dto.getStoreId() );

        return storeProductReply;
    }

    @Override
    public StoreProductReplyDTO toDto(StoreProductReply entity) {
        if ( entity == null ) {
            return null;
        }

        StoreProductReplyDTO storeProductReplyDTO = new StoreProductReplyDTO();

        storeProductReplyDTO.setId( entity.getId() );
        if ( entity.getUid() != null ) {
            storeProductReplyDTO.setUid( entity.getUid().intValue() );
        }
        if ( entity.getOid() != null ) {
            storeProductReplyDTO.setOid( entity.getOid().intValue() );
        }
        storeProductReplyDTO.setUnique( entity.getUnique() );
        storeProductReplyDTO.setProductId( entity.getProductId() );
        storeProductReplyDTO.setReplyType( entity.getReplyType() );
        storeProductReplyDTO.setProductScore( entity.getProductScore() );
        storeProductReplyDTO.setServiceScore( entity.getServiceScore() );
        storeProductReplyDTO.setComment( entity.getComment() );
        storeProductReplyDTO.setPics( entity.getPics() );
        storeProductReplyDTO.setAddTime( entity.getAddTime() );
        storeProductReplyDTO.setMerchantReplyContent( entity.getMerchantReplyContent() );
        storeProductReplyDTO.setMerchantReplyTime( entity.getMerchantReplyTime() );
        storeProductReplyDTO.setIsDel( entity.getIsDel() );
        storeProductReplyDTO.setIsReply( entity.getIsReply() );
        storeProductReplyDTO.setStoreId( entity.getStoreId() );

        return storeProductReplyDTO;
    }

    @Override
    public List<StoreProductReply> toEntity(List<StoreProductReplyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreProductReply> list = new ArrayList<StoreProductReply>( dtoList.size() );
        for ( StoreProductReplyDTO storeProductReplyDTO : dtoList ) {
            list.add( toEntity( storeProductReplyDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreProductReplyDTO> toDto(List<StoreProductReply> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreProductReplyDTO> list = new ArrayList<StoreProductReplyDTO>( entityList.size() );
        for ( StoreProductReply storeProductReply : entityList ) {
            list.add( toDto( storeProductReply ) );
        }

        return list;
    }
}
