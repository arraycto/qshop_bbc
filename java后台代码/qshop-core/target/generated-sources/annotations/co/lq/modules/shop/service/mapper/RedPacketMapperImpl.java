package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.RedPacket;
import co.lq.modules.shop.service.dto.RedPacketDTO;
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
public class RedPacketMapperImpl implements RedPacketMapper {

    @Override
    public RedPacket toEntity(RedPacketDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RedPacket redPacket = new RedPacket();

        redPacket.setId( dto.getId() );
        redPacket.setUserId( dto.getUserId() );
        redPacket.setAmount( dto.getAmount() );
        redPacket.setSendDate( dto.getSendDate() );
        redPacket.setTotal( dto.getTotal() );
        redPacket.setUnitAmount( dto.getUnitAmount() );
        redPacket.setStock( dto.getStock() );
        redPacket.setType( dto.getType() );
        redPacket.setNote( dto.getNote() );
        redPacket.setStoreId( dto.getStoreId() );
        redPacket.setAddTime( dto.getAddTime() );
        redPacket.setModifyTime( dto.getModifyTime() );
        redPacket.setDeleted( dto.getDeleted() );

        return redPacket;
    }

    @Override
    public RedPacketDTO toDto(RedPacket entity) {
        if ( entity == null ) {
            return null;
        }

        RedPacketDTO redPacketDTO = new RedPacketDTO();

        redPacketDTO.setId( entity.getId() );
        redPacketDTO.setUserId( entity.getUserId() );
        redPacketDTO.setAmount( entity.getAmount() );
        redPacketDTO.setSendDate( entity.getSendDate() );
        redPacketDTO.setTotal( entity.getTotal() );
        redPacketDTO.setUnitAmount( entity.getUnitAmount() );
        redPacketDTO.setStock( entity.getStock() );
        redPacketDTO.setType( entity.getType() );
        redPacketDTO.setNote( entity.getNote() );
        redPacketDTO.setStoreId( entity.getStoreId() );
        redPacketDTO.setAddTime( entity.getAddTime() );
        redPacketDTO.setModifyTime( entity.getModifyTime() );
        redPacketDTO.setDeleted( entity.getDeleted() );

        return redPacketDTO;
    }

    @Override
    public List<RedPacket> toEntity(List<RedPacketDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RedPacket> list = new ArrayList<RedPacket>( dtoList.size() );
        for ( RedPacketDTO redPacketDTO : dtoList ) {
            list.add( toEntity( redPacketDTO ) );
        }

        return list;
    }

    @Override
    public List<RedPacketDTO> toDto(List<RedPacket> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RedPacketDTO> list = new ArrayList<RedPacketDTO>( entityList.size() );
        for ( RedPacket redPacket : entityList ) {
            list.add( toDto( redPacket ) );
        }

        return list;
    }
}
