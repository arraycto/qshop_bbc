package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.Article;
import co.lq.modules.shop.web.param.ArticleQueryParam;
import co.lq.modules.shop.web.vo.ArticleQueryVo;

/**
 * <p>
 * 文章管理表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-02
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    @Update("update article set visit=visit+1 " + "where id=#{id}")
    int incVisitNum(@Param("id") int id);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    ArticleQueryVo getArticleById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param articleQueryParam
     * @return
     */
    IPage<ArticleQueryVo> getArticlePageList(@Param("page") Page page,
                                             @Param("param") ArticleQueryParam articleQueryParam);

}
