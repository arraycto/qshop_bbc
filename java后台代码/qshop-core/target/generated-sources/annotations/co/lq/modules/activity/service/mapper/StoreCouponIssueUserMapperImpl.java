package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreCouponIssueUser;
import co.lq.modules.activity.service.dto.StoreCouponIssueUserDTO;
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
public class StoreCouponIssueUserMapperImpl implements StoreCouponIssueUserMapper {

    @Override
    public StoreCouponIssueUser toEntity(StoreCouponIssueUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCouponIssueUser storeCouponIssueUser = new StoreCouponIssueUser();

        storeCouponIssueUser.setId( dto.getId() );
        storeCouponIssueUser.setUid( dto.getUid() );
        storeCouponIssueUser.setIssueCouponId( dto.getIssueCouponId() );
        storeCouponIssueUser.setAddTime( dto.getAddTime() );
        storeCouponIssueUser.setStoreId( dto.getStoreId() );

        return storeCouponIssueUser;
    }

    @Override
    public StoreCouponIssueUserDTO toDto(StoreCouponIssueUser entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCouponIssueUserDTO storeCouponIssueUserDTO = new StoreCouponIssueUserDTO();

        storeCouponIssueUserDTO.setId( entity.getId() );
        storeCouponIssueUserDTO.setUid( entity.getUid() );
        storeCouponIssueUserDTO.setIssueCouponId( entity.getIssueCouponId() );
        storeCouponIssueUserDTO.setAddTime( entity.getAddTime() );
        storeCouponIssueUserDTO.setStoreId( entity.getStoreId() );

        return storeCouponIssueUserDTO;
    }

    @Override
    public List<StoreCouponIssueUser> toEntity(List<StoreCouponIssueUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCouponIssueUser> list = new ArrayList<StoreCouponIssueUser>( dtoList.size() );
        for ( StoreCouponIssueUserDTO storeCouponIssueUserDTO : dtoList ) {
            list.add( toEntity( storeCouponIssueUserDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCouponIssueUserDTO> toDto(List<StoreCouponIssueUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCouponIssueUserDTO> list = new ArrayList<StoreCouponIssueUserDTO>( entityList.size() );
        for ( StoreCouponIssueUser storeCouponIssueUser : entityList ) {
            list.add( toDto( storeCouponIssueUser ) );
        }

        return list;
    }
}
