package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.FeightTemplate;
import co.lq.modules.shop.domain.Shop;
import co.lq.modules.shop.service.dto.FeightTemplateDTO;
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
public class FeightTemplateMapperImpl implements FeightTemplateMapper {

    @Override
    public FeightTemplate toEntity(FeightTemplateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FeightTemplate feightTemplate = new FeightTemplate();

        feightTemplate.setId( dto.getId() );
        feightTemplate.setName( dto.getName() );
        feightTemplate.setChargeType( dto.getChargeType() );
        feightTemplate.setFirstWeight( dto.getFirstWeight() );
        feightTemplate.setFirstFee( dto.getFirstFee() );
        feightTemplate.setContinueWeight( dto.getContinueWeight() );
        feightTemplate.setContinmeFee( dto.getContinmeFee() );
        feightTemplate.setDest( dto.getDest() );
        feightTemplate.setStoreId( dto.getStoreId() );
        feightTemplate.setAddTime( dto.getAddTime() );
        feightTemplate.setModifyTime( dto.getModifyTime() );
        feightTemplate.setDeleted( dto.getDeleted() );

        return feightTemplate;
    }

    @Override
    public List<FeightTemplate> toEntity(List<FeightTemplateDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FeightTemplate> list = new ArrayList<FeightTemplate>( dtoList.size() );
        for ( FeightTemplateDTO feightTemplateDTO : dtoList ) {
            list.add( toEntity( feightTemplateDTO ) );
        }

        return list;
    }

    @Override
    public List<FeightTemplateDTO> toDto(List<FeightTemplate> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeightTemplateDTO> list = new ArrayList<FeightTemplateDTO>( entityList.size() );
        for ( FeightTemplate feightTemplate : entityList ) {
            list.add( toDto( feightTemplate ) );
        }

        return list;
    }

    @Override
    public FeightTemplateDTO toDto(FeightTemplate feightTemplate) {
        if ( feightTemplate == null ) {
            return null;
        }

        FeightTemplateDTO feightTemplateDTO = new FeightTemplateDTO();

        String name = feightTemplateShopName( feightTemplate );
        if ( name != null ) {
            feightTemplateDTO.setShopName( name );
        }
        feightTemplateDTO.setId( feightTemplate.getId() );
        feightTemplateDTO.setName( feightTemplate.getName() );
        feightTemplateDTO.setChargeType( feightTemplate.getChargeType() );
        feightTemplateDTO.setFirstWeight( feightTemplate.getFirstWeight() );
        feightTemplateDTO.setFirstFee( feightTemplate.getFirstFee() );
        feightTemplateDTO.setContinueWeight( feightTemplate.getContinueWeight() );
        feightTemplateDTO.setContinmeFee( feightTemplate.getContinmeFee() );
        feightTemplateDTO.setDest( feightTemplate.getDest() );
        feightTemplateDTO.setStoreId( feightTemplate.getStoreId() );
        feightTemplateDTO.setAddTime( feightTemplate.getAddTime() );
        feightTemplateDTO.setModifyTime( feightTemplate.getModifyTime() );
        feightTemplateDTO.setDeleted( feightTemplate.getDeleted() );

        return feightTemplateDTO;
    }

    private String feightTemplateShopName(FeightTemplate feightTemplate) {
        if ( feightTemplate == null ) {
            return null;
        }
        Shop shop = feightTemplate.getShop();
        if ( shop == null ) {
            return null;
        }
        String name = shop.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
