package co.lq.modules.shop.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.Article;
import co.lq.modules.shop.web.param.ArticleQueryParam;
import co.lq.modules.shop.web.vo.ArticleQueryVo;

/**
 * <p>
 * 文章管理表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-02
 */
public interface ApiArticleService extends BaseService<Article> {

    void incVisitNum(int id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    ArticleQueryVo getArticleById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param articleQueryParam
     * @return
     */
    Paging<ArticleQueryVo> getArticlePageList(ArticleQueryParam articleQueryParam);

}
