package co.lq.modules.activity.service.mapper;

import co.lq.modules.activity.domain.StoreCouponIssue;
import co.lq.modules.activity.service.dto.StoreCouponIssueDTO;
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
public class StoreCouponIssueMapperImpl implements StoreCouponIssueMapper {

    @Override
    public StoreCouponIssue toEntity(StoreCouponIssueDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StoreCouponIssue storeCouponIssue = new StoreCouponIssue();

        storeCouponIssue.setId( dto.getId() );
        storeCouponIssue.setCid( dto.getCid() );
        storeCouponIssue.setCname( dto.getCname() );
        storeCouponIssue.setStartTime( dto.getStartTime() );
        storeCouponIssue.setStartTimeDate( dto.getStartTimeDate() );
        storeCouponIssue.setEndTimeDate( dto.getEndTimeDate() );
        storeCouponIssue.setEndTime( dto.getEndTime() );
        storeCouponIssue.setTotalCount( dto.getTotalCount() );
        storeCouponIssue.setRemainCount( dto.getRemainCount() );
        storeCouponIssue.setIsPermanent( dto.getIsPermanent() );
        storeCouponIssue.setStatus( dto.getStatus() );
        storeCouponIssue.setIsDel( dto.getIsDel() );
        storeCouponIssue.setAddTime( dto.getAddTime() );
        storeCouponIssue.setStoreId( dto.getStoreId() );

        return storeCouponIssue;
    }

    @Override
    public StoreCouponIssueDTO toDto(StoreCouponIssue entity) {
        if ( entity == null ) {
            return null;
        }

        StoreCouponIssueDTO storeCouponIssueDTO = new StoreCouponIssueDTO();

        storeCouponIssueDTO.setId( entity.getId() );
        storeCouponIssueDTO.setCid( entity.getCid() );
        storeCouponIssueDTO.setCname( entity.getCname() );
        storeCouponIssueDTO.setStartTime( entity.getStartTime() );
        storeCouponIssueDTO.setEndTime( entity.getEndTime() );
        storeCouponIssueDTO.setStartTimeDate( entity.getStartTimeDate() );
        storeCouponIssueDTO.setEndTimeDate( entity.getEndTimeDate() );
        storeCouponIssueDTO.setTotalCount( entity.getTotalCount() );
        storeCouponIssueDTO.setRemainCount( entity.getRemainCount() );
        storeCouponIssueDTO.setIsPermanent( entity.getIsPermanent() );
        storeCouponIssueDTO.setStatus( entity.getStatus() );
        storeCouponIssueDTO.setIsDel( entity.getIsDel() );
        storeCouponIssueDTO.setAddTime( entity.getAddTime() );
        storeCouponIssueDTO.setStoreId( entity.getStoreId() );

        return storeCouponIssueDTO;
    }

    @Override
    public List<StoreCouponIssue> toEntity(List<StoreCouponIssueDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StoreCouponIssue> list = new ArrayList<StoreCouponIssue>( dtoList.size() );
        for ( StoreCouponIssueDTO storeCouponIssueDTO : dtoList ) {
            list.add( toEntity( storeCouponIssueDTO ) );
        }

        return list;
    }

    @Override
    public List<StoreCouponIssueDTO> toDto(List<StoreCouponIssue> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StoreCouponIssueDTO> list = new ArrayList<StoreCouponIssueDTO>( entityList.size() );
        for ( StoreCouponIssue storeCouponIssue : entityList ) {
            list.add( toDto( storeCouponIssue ) );
        }

        return list;
    }
}
