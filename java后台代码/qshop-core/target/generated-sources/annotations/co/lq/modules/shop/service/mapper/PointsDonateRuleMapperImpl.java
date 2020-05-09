package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.PointsDonateRule;
import co.lq.modules.shop.service.dto.PointsDonateRuleDTO;
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
public class PointsDonateRuleMapperImpl implements PointsDonateRuleMapper {

    @Override
    public PointsDonateRule toEntity(PointsDonateRuleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PointsDonateRule pointsDonateRule = new PointsDonateRule();

        pointsDonateRule.setId( dto.getId() );
        pointsDonateRule.setDonateType( dto.getDonateType() );
        pointsDonateRule.setDonateCondtion( dto.getDonateCondtion() );
        pointsDonateRule.setDonateIntegration( dto.getDonateIntegration() );
        pointsDonateRule.setStoreId( dto.getStoreId() );
        pointsDonateRule.setAddTime( dto.getAddTime() );
        pointsDonateRule.setModifyTime( dto.getModifyTime() );
        pointsDonateRule.setDeleted( dto.getDeleted() );

        return pointsDonateRule;
    }

    @Override
    public PointsDonateRuleDTO toDto(PointsDonateRule entity) {
        if ( entity == null ) {
            return null;
        }

        PointsDonateRuleDTO pointsDonateRuleDTO = new PointsDonateRuleDTO();

        pointsDonateRuleDTO.setId( entity.getId() );
        pointsDonateRuleDTO.setDonateType( entity.getDonateType() );
        pointsDonateRuleDTO.setDonateCondtion( entity.getDonateCondtion() );
        pointsDonateRuleDTO.setDonateIntegration( entity.getDonateIntegration() );
        pointsDonateRuleDTO.setStoreId( entity.getStoreId() );
        pointsDonateRuleDTO.setAddTime( entity.getAddTime() );
        pointsDonateRuleDTO.setModifyTime( entity.getModifyTime() );
        pointsDonateRuleDTO.setDeleted( entity.getDeleted() );

        return pointsDonateRuleDTO;
    }

    @Override
    public List<PointsDonateRule> toEntity(List<PointsDonateRuleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PointsDonateRule> list = new ArrayList<PointsDonateRule>( dtoList.size() );
        for ( PointsDonateRuleDTO pointsDonateRuleDTO : dtoList ) {
            list.add( toEntity( pointsDonateRuleDTO ) );
        }

        return list;
    }

    @Override
    public List<PointsDonateRuleDTO> toDto(List<PointsDonateRule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PointsDonateRuleDTO> list = new ArrayList<PointsDonateRuleDTO>( entityList.size() );
        for ( PointsDonateRule pointsDonateRule : entityList ) {
            list.add( toDto( pointsDonateRule ) );
        }

        return list;
    }
}
