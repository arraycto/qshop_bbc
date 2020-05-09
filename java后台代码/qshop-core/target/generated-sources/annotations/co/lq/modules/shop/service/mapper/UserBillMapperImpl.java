package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.UserBill;
import co.lq.modules.shop.service.dto.UserBillDTO;
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
public class UserBillMapperImpl implements UserBillMapper {

    @Override
    public UserBill toEntity(UserBillDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserBill userBill = new UserBill();

        userBill.setId( dto.getId() );
        userBill.setUid( dto.getUid() );
        userBill.setLinkId( dto.getLinkId() );
        userBill.setPm( dto.getPm() );
        userBill.setTitle( dto.getTitle() );
        userBill.setCategory( dto.getCategory() );
        userBill.setType( dto.getType() );
        userBill.setNumber( dto.getNumber() );
        userBill.setBalance( dto.getBalance() );
        userBill.setMark( dto.getMark() );
        userBill.setAddTime( dto.getAddTime() );
        userBill.setStatus( dto.getStatus() );
        userBill.setStoreId( dto.getStoreId() );

        return userBill;
    }

    @Override
    public UserBillDTO toDto(UserBill entity) {
        if ( entity == null ) {
            return null;
        }

        UserBillDTO userBillDTO = new UserBillDTO();

        userBillDTO.setId( entity.getId() );
        userBillDTO.setUid( entity.getUid() );
        userBillDTO.setLinkId( entity.getLinkId() );
        userBillDTO.setPm( entity.getPm() );
        userBillDTO.setTitle( entity.getTitle() );
        userBillDTO.setCategory( entity.getCategory() );
        userBillDTO.setType( entity.getType() );
        userBillDTO.setNumber( entity.getNumber() );
        userBillDTO.setBalance( entity.getBalance() );
        userBillDTO.setMark( entity.getMark() );
        userBillDTO.setAddTime( entity.getAddTime() );
        userBillDTO.setStatus( entity.getStatus() );
        userBillDTO.setStoreId( entity.getStoreId() );

        return userBillDTO;
    }

    @Override
    public List<UserBill> toEntity(List<UserBillDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserBill> list = new ArrayList<UserBill>( dtoList.size() );
        for ( UserBillDTO userBillDTO : dtoList ) {
            list.add( toEntity( userBillDTO ) );
        }

        return list;
    }

    @Override
    public List<UserBillDTO> toDto(List<UserBill> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserBillDTO> list = new ArrayList<UserBillDTO>( entityList.size() );
        for ( UserBill userBill : entityList ) {
            list.add( toDto( userBill ) );
        }

        return list;
    }
}
