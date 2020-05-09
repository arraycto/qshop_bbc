package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.SmsContent;
import co.lq.modules.shop.service.dto.SmsContentDTO;
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
public class SmsContentMapperImpl implements SmsContentMapper {

    @Override
    public SmsContent toEntity(SmsContentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SmsContent smsContent = new SmsContent();

        smsContent.setId( dto.getId() );
        smsContent.setName( dto.getName() );
        smsContent.setType( dto.getType() );
        smsContent.setMeno( dto.getMeno() );
        smsContent.setCode( dto.getCode() );
        smsContent.setRemark( dto.getRemark() );
        smsContent.setStoreId( dto.getStoreId() );
        smsContent.setAddTime( dto.getAddTime() );
        smsContent.setModifyTime( dto.getModifyTime() );
        smsContent.setDeleted( dto.getDeleted() );

        return smsContent;
    }

    @Override
    public SmsContentDTO toDto(SmsContent entity) {
        if ( entity == null ) {
            return null;
        }

        SmsContentDTO smsContentDTO = new SmsContentDTO();

        smsContentDTO.setId( entity.getId() );
        smsContentDTO.setName( entity.getName() );
        smsContentDTO.setType( entity.getType() );
        smsContentDTO.setMeno( entity.getMeno() );
        smsContentDTO.setCode( entity.getCode() );
        smsContentDTO.setRemark( entity.getRemark() );
        smsContentDTO.setStoreId( entity.getStoreId() );
        smsContentDTO.setAddTime( entity.getAddTime() );
        smsContentDTO.setModifyTime( entity.getModifyTime() );
        smsContentDTO.setDeleted( entity.getDeleted() );

        return smsContentDTO;
    }

    @Override
    public List<SmsContent> toEntity(List<SmsContentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SmsContent> list = new ArrayList<SmsContent>( dtoList.size() );
        for ( SmsContentDTO smsContentDTO : dtoList ) {
            list.add( toEntity( smsContentDTO ) );
        }

        return list;
    }

    @Override
    public List<SmsContentDTO> toDto(List<SmsContent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SmsContentDTO> list = new ArrayList<SmsContentDTO>( entityList.size() );
        for ( SmsContent smsContent : entityList ) {
            list.add( toDto( smsContent ) );
        }

        return list;
    }
}
