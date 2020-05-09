package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.PointsSignRule;
import co.lq.modules.shop.service.dto.PointsSignRuleDTO;
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
public class PointsSignRuleMapperImpl implements PointsSignRuleMapper {

    @Override
    public PointsSignRule toEntity(PointsSignRuleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PointsSignRule pointsSignRule = new PointsSignRule();

        pointsSignRule.setId( dto.getId() );
        pointsSignRule.setContineuCount( dto.getContineuCount() );
        pointsSignRule.setDonateIntegrtion( dto.getDonateIntegrtion() );
        pointsSignRule.setStoreId( dto.getStoreId() );
        pointsSignRule.setAddTime( dto.getAddTime() );
        pointsSignRule.setModifyTime( dto.getModifyTime() );
        pointsSignRule.setDeleted( dto.getDeleted() );

        return pointsSignRule;
    }

    @Override
    public PointsSignRuleDTO toDto(PointsSignRule entity) {
        if ( entity == null ) {
            return null;
        }

        PointsSignRuleDTO pointsSignRuleDTO = new PointsSignRuleDTO();

        pointsSignRuleDTO.setId( entity.getId() );
        pointsSignRuleDTO.setContineuCount( entity.getContineuCount() );
        pointsSignRuleDTO.setDonateIntegrtion( entity.getDonateIntegrtion() );
        pointsSignRuleDTO.setStoreId( entity.getStoreId() );
        pointsSignRuleDTO.setAddTime( entity.getAddTime() );
        pointsSignRuleDTO.setModifyTime( entity.getModifyTime() );
        pointsSignRuleDTO.setDeleted( entity.getDeleted() );

        return pointsSignRuleDTO;
    }

    @Override
    public List<PointsSignRule> toEntity(List<PointsSignRuleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PointsSignRule> list = new ArrayList<PointsSignRule>( dtoList.size() );
        for ( PointsSignRuleDTO pointsSignRuleDTO : dtoList ) {
            list.add( toEntity( pointsSignRuleDTO ) );
        }

        return list;
    }

    @Override
    public List<PointsSignRuleDTO> toDto(List<PointsSignRule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PointsSignRuleDTO> list = new ArrayList<PointsSignRuleDTO>( entityList.size() );
        for ( PointsSignRule pointsSignRule : entityList ) {
            list.add( toDto( pointsSignRule ) );
        }

        return list;
    }
}
