package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.SmsContentMsg;
import co.lq.modules.shop.service.dto.SmsContentMsgDTO;
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
public class SmsContentMsgMapperImpl implements SmsContentMsgMapper {

    @Override
    public SmsContentMsg toEntity(SmsContentMsgDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SmsContentMsg smsContentMsg = new SmsContentMsg();

        smsContentMsg.setId( dto.getId() );
        smsContentMsg.setPhone( dto.getPhone() );
        smsContentMsg.setStatus( dto.getStatus() );
        smsContentMsg.setContent( dto.getContent() );
        smsContentMsg.setResult( dto.getResult() );
        smsContentMsg.setContentId( dto.getContentId() );
        smsContentMsg.setType( dto.getType() );
        smsContentMsg.setStoreId( dto.getStoreId() );
        smsContentMsg.setAddTime( dto.getAddTime() );
        smsContentMsg.setModifyTime( dto.getModifyTime() );
        smsContentMsg.setDeleted( dto.getDeleted() );

        return smsContentMsg;
    }

    @Override
    public SmsContentMsgDTO toDto(SmsContentMsg entity) {
        if ( entity == null ) {
            return null;
        }

        SmsContentMsgDTO smsContentMsgDTO = new SmsContentMsgDTO();

        smsContentMsgDTO.setId( entity.getId() );
        smsContentMsgDTO.setPhone( entity.getPhone() );
        smsContentMsgDTO.setStatus( entity.getStatus() );
        smsContentMsgDTO.setContent( entity.getContent() );
        smsContentMsgDTO.setResult( entity.getResult() );
        smsContentMsgDTO.setContentId( entity.getContentId() );
        smsContentMsgDTO.setType( entity.getType() );
        smsContentMsgDTO.setStoreId( entity.getStoreId() );
        smsContentMsgDTO.setAddTime( entity.getAddTime() );
        smsContentMsgDTO.setModifyTime( entity.getModifyTime() );
        smsContentMsgDTO.setDeleted( entity.getDeleted() );

        return smsContentMsgDTO;
    }

    @Override
    public List<SmsContentMsg> toEntity(List<SmsContentMsgDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SmsContentMsg> list = new ArrayList<SmsContentMsg>( dtoList.size() );
        for ( SmsContentMsgDTO smsContentMsgDTO : dtoList ) {
            list.add( toEntity( smsContentMsgDTO ) );
        }

        return list;
    }

    @Override
    public List<SmsContentMsgDTO> toDto(List<SmsContentMsg> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SmsContentMsgDTO> list = new ArrayList<SmsContentMsgDTO>( entityList.size() );
        for ( SmsContentMsg smsContentMsg : entityList ) {
            list.add( toDto( smsContentMsg ) );
        }

        return list;
    }
}
