package co.lq.modules.shop.service.mapper;

import co.lq.modules.shop.domain.HomeRecommendSubject;
import co.lq.modules.shop.service.dto.HomeRecommendSubjectDTO;
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
public class HomeRecommendSubjectMapperImpl implements HomeRecommendSubjectMapper {

    @Override
    public HomeRecommendSubject toEntity(HomeRecommendSubjectDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HomeRecommendSubject homeRecommendSubject = new HomeRecommendSubject();

        homeRecommendSubject.setId( dto.getId() );
        homeRecommendSubject.setSubjectId( dto.getSubjectId() );
        homeRecommendSubject.setSubjectName( dto.getSubjectName() );
        homeRecommendSubject.setRecommendStatus( dto.getRecommendStatus() );
        homeRecommendSubject.setSort( dto.getSort() );
        homeRecommendSubject.setStoreId( dto.getStoreId() );
        homeRecommendSubject.setAddTime( dto.getAddTime() );
        homeRecommendSubject.setModifyTime( dto.getModifyTime() );
        homeRecommendSubject.setDeleted( dto.getDeleted() );

        return homeRecommendSubject;
    }

    @Override
    public HomeRecommendSubjectDTO toDto(HomeRecommendSubject entity) {
        if ( entity == null ) {
            return null;
        }

        HomeRecommendSubjectDTO homeRecommendSubjectDTO = new HomeRecommendSubjectDTO();

        homeRecommendSubjectDTO.setId( entity.getId() );
        homeRecommendSubjectDTO.setSubjectId( entity.getSubjectId() );
        homeRecommendSubjectDTO.setSubjectName( entity.getSubjectName() );
        homeRecommendSubjectDTO.setRecommendStatus( entity.getRecommendStatus() );
        homeRecommendSubjectDTO.setSort( entity.getSort() );
        homeRecommendSubjectDTO.setStoreId( entity.getStoreId() );
        homeRecommendSubjectDTO.setAddTime( entity.getAddTime() );
        homeRecommendSubjectDTO.setModifyTime( entity.getModifyTime() );
        homeRecommendSubjectDTO.setDeleted( entity.getDeleted() );

        return homeRecommendSubjectDTO;
    }

    @Override
    public List<HomeRecommendSubject> toEntity(List<HomeRecommendSubjectDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HomeRecommendSubject> list = new ArrayList<HomeRecommendSubject>( dtoList.size() );
        for ( HomeRecommendSubjectDTO homeRecommendSubjectDTO : dtoList ) {
            list.add( toEntity( homeRecommendSubjectDTO ) );
        }

        return list;
    }

    @Override
    public List<HomeRecommendSubjectDTO> toDto(List<HomeRecommendSubject> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HomeRecommendSubjectDTO> list = new ArrayList<HomeRecommendSubjectDTO>( entityList.size() );
        for ( HomeRecommendSubject homeRecommendSubject : entityList ) {
            list.add( toDto( homeRecommendSubject ) );
        }

        return list;
    }
}
