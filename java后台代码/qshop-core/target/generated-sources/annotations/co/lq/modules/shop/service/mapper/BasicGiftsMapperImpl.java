package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.BasicGifts;
import co.lq.modules.shop.service.dto.BasicGiftsDTO;
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
public class BasicGiftsMapperImpl implements BasicGiftsMapper {

    @Override
    public BasicGifts toEntity(BasicGiftsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BasicGifts basicGifts = new BasicGifts();

        basicGifts.setId( dto.getId() );
        basicGifts.setName( dto.getName() );
        basicGifts.setStatus( dto.getStatus() );
        basicGifts.setActiviUser( dto.getActiviUser() );
        basicGifts.setActiviGoods( dto.getActiviGoods() );
        basicGifts.setBigType( dto.getBigType() );
        basicGifts.setSmallType( dto.getSmallType() );
        basicGifts.setRules( dto.getRules() );
        basicGifts.setGoodsIds( dto.getGoodsIds() );
        basicGifts.setUserLevel( dto.getUserLevel() );
        basicGifts.setStartTime( dto.getStartTime() );
        basicGifts.setEndTime( dto.getEndTime() );
        basicGifts.setGiftIds( dto.getGiftIds() );
        basicGifts.setAddTime( dto.getAddTime() );
        basicGifts.setModifyTime( dto.getModifyTime() );
        basicGifts.setDeleted( dto.getDeleted() );
        basicGifts.setNote( dto.getNote() );
        basicGifts.setStoreId( dto.getStoreId() );

        return basicGifts;
    }

    @Override
    public BasicGiftsDTO toDto(BasicGifts entity) {
        if ( entity == null ) {
            return null;
        }

        BasicGiftsDTO basicGiftsDTO = new BasicGiftsDTO();

        basicGiftsDTO.setId( entity.getId() );
        basicGiftsDTO.setName( entity.getName() );
        basicGiftsDTO.setStatus( entity.getStatus() );
        basicGiftsDTO.setActiviUser( entity.getActiviUser() );
        basicGiftsDTO.setActiviGoods( entity.getActiviGoods() );
        basicGiftsDTO.setBigType( entity.getBigType() );
        basicGiftsDTO.setSmallType( entity.getSmallType() );
        basicGiftsDTO.setRules( entity.getRules() );
        basicGiftsDTO.setGoodsIds( entity.getGoodsIds() );
        basicGiftsDTO.setUserLevel( entity.getUserLevel() );
        basicGiftsDTO.setStartTime( entity.getStartTime() );
        basicGiftsDTO.setEndTime( entity.getEndTime() );
        basicGiftsDTO.setGiftIds( entity.getGiftIds() );
        basicGiftsDTO.setAddTime( entity.getAddTime() );
        basicGiftsDTO.setModifyTime( entity.getModifyTime() );
        basicGiftsDTO.setDeleted( entity.getDeleted() );
        basicGiftsDTO.setNote( entity.getNote() );
        basicGiftsDTO.setStoreId( entity.getStoreId() );

        return basicGiftsDTO;
    }

    @Override
    public List<BasicGifts> toEntity(List<BasicGiftsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<BasicGifts> list = new ArrayList<BasicGifts>( dtoList.size() );
        for ( BasicGiftsDTO basicGiftsDTO : dtoList ) {
            list.add( toEntity( basicGiftsDTO ) );
        }

        return list;
    }

    @Override
    public List<BasicGiftsDTO> toDto(List<BasicGifts> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BasicGiftsDTO> list = new ArrayList<BasicGiftsDTO>( entityList.size() );
        for ( BasicGifts basicGifts : entityList ) {
            list.add( toDto( basicGifts ) );
        }

        return list;
    }
}
