package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.UserExtract;
import co.lq.modules.activity.service.dto.UserExtractDTO;
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
public class UserExtractMapperImpl implements UserExtractMapper {

    @Override
    public UserExtract toEntity(UserExtractDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserExtract userExtract = new UserExtract();

        userExtract.setId( dto.getId() );
        userExtract.setUid( dto.getUid() );
        userExtract.setRealName( dto.getRealName() );
        userExtract.setExtractType( dto.getExtractType() );
        userExtract.setBankCode( dto.getBankCode() );
        userExtract.setBankAddress( dto.getBankAddress() );
        userExtract.setAlipayCode( dto.getAlipayCode() );
        userExtract.setExtractPrice( dto.getExtractPrice() );
        userExtract.setMark( dto.getMark() );
        userExtract.setBalance( dto.getBalance() );
        userExtract.setFailMsg( dto.getFailMsg() );
        userExtract.setFailTime( dto.getFailTime() );
        userExtract.setAddTime( dto.getAddTime() );
        userExtract.setStatus( dto.getStatus() );
        userExtract.setWechat( dto.getWechat() );
        userExtract.setStoreId( dto.getStoreId() );

        return userExtract;
    }

    @Override
    public UserExtractDTO toDto(UserExtract entity) {
        if ( entity == null ) {
            return null;
        }

        UserExtractDTO userExtractDTO = new UserExtractDTO();

        userExtractDTO.setId( entity.getId() );
        userExtractDTO.setUid( entity.getUid() );
        userExtractDTO.setRealName( entity.getRealName() );
        userExtractDTO.setExtractType( entity.getExtractType() );
        userExtractDTO.setBankCode( entity.getBankCode() );
        userExtractDTO.setBankAddress( entity.getBankAddress() );
        userExtractDTO.setAlipayCode( entity.getAlipayCode() );
        userExtractDTO.setExtractPrice( entity.getExtractPrice() );
        userExtractDTO.setMark( entity.getMark() );
        userExtractDTO.setBalance( entity.getBalance() );
        userExtractDTO.setFailMsg( entity.getFailMsg() );
        userExtractDTO.setFailTime( entity.getFailTime() );
        userExtractDTO.setAddTime( entity.getAddTime() );
        userExtractDTO.setStatus( entity.getStatus() );
        userExtractDTO.setWechat( entity.getWechat() );
        userExtractDTO.setStoreId( entity.getStoreId() );

        return userExtractDTO;
    }

    @Override
    public List<UserExtract> toEntity(List<UserExtractDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserExtract> list = new ArrayList<UserExtract>( dtoList.size() );
        for ( UserExtractDTO userExtractDTO : dtoList ) {
            list.add( toEntity( userExtractDTO ) );
        }

        return list;
    }

    @Override
    public List<UserExtractDTO> toDto(List<UserExtract> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserExtractDTO> list = new ArrayList<UserExtractDTO>( entityList.size() );
        for ( UserExtract userExtract : entityList ) {
            list.add( toDto( userExtract ) );
        }

        return list;
    }
}
