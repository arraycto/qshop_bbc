package co.lq.mp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.mp.domain.Article;
import co.lq.mp.service.dto.ArticleDTO;

/**
 * @author billy
 * @date 2019-10-07
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

}
