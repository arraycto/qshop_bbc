package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.CmsPrefrenceArea;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaDTO;
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
public class CmsPrefrenceAreaMapperImpl implements CmsPrefrenceAreaMapper {

    @Override
    public CmsPrefrenceArea toEntity(CmsPrefrenceAreaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CmsPrefrenceArea cmsPrefrenceArea = new CmsPrefrenceArea();

        cmsPrefrenceArea.setId( dto.getId() );
        cmsPrefrenceArea.setName( dto.getName() );
        cmsPrefrenceArea.setSubTitle( dto.getSubTitle() );
        cmsPrefrenceArea.setPic( dto.getPic() );
        cmsPrefrenceArea.setSort( dto.getSort() );
        cmsPrefrenceArea.setShowStatus( dto.getShowStatus() );
        cmsPrefrenceArea.setAddTime( dto.getAddTime() );
        cmsPrefrenceArea.setModifyTime( dto.getModifyTime() );
        cmsPrefrenceArea.setDeleted( dto.getDeleted() );

        return cmsPrefrenceArea;
    }

    @Override
    public CmsPrefrenceAreaDTO toDto(CmsPrefrenceArea entity) {
        if ( entity == null ) {
            return null;
        }

        CmsPrefrenceAreaDTO cmsPrefrenceAreaDTO = new CmsPrefrenceAreaDTO();

        cmsPrefrenceAreaDTO.setId( entity.getId() );
        cmsPrefrenceAreaDTO.setName( entity.getName() );
        cmsPrefrenceAreaDTO.setSubTitle( entity.getSubTitle() );
        cmsPrefrenceAreaDTO.setPic( entity.getPic() );
        cmsPrefrenceAreaDTO.setSort( entity.getSort() );
        cmsPrefrenceAreaDTO.setShowStatus( entity.getShowStatus() );
        cmsPrefrenceAreaDTO.setAddTime( entity.getAddTime() );
        cmsPrefrenceAreaDTO.setModifyTime( entity.getModifyTime() );
        cmsPrefrenceAreaDTO.setDeleted( entity.getDeleted() );

        return cmsPrefrenceAreaDTO;
    }

    @Override
    public List<CmsPrefrenceArea> toEntity(List<CmsPrefrenceAreaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CmsPrefrenceArea> list = new ArrayList<CmsPrefrenceArea>( dtoList.size() );
        for ( CmsPrefrenceAreaDTO cmsPrefrenceAreaDTO : dtoList ) {
            list.add( toEntity( cmsPrefrenceAreaDTO ) );
        }

        return list;
    }

    @Override
    public List<CmsPrefrenceAreaDTO> toDto(List<CmsPrefrenceArea> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CmsPrefrenceAreaDTO> list = new ArrayList<CmsPrefrenceAreaDTO>( entityList.size() );
        for ( CmsPrefrenceArea cmsPrefrenceArea : entityList ) {
            list.add( toDto( cmsPrefrenceArea ) );
        }

        return list;
    }
}
