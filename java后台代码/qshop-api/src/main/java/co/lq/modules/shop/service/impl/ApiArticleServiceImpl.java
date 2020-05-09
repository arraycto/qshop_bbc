package co.lq.modules.shop.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.Article;
import co.lq.modules.shop.mapper.ArticleMapper;
import co.lq.modules.shop.service.ApiArticleService;
import co.lq.modules.shop.web.param.ArticleQueryParam;
import co.lq.modules.shop.web.vo.ArticleQueryVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 文章管理表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-10-02
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ApiArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements ApiArticleService {

    private final ArticleMapper articleMapper;

    @Override
    public ArticleQueryVo getArticleById(Serializable id) throws Exception {
        return articleMapper.getArticleById(id);
    }

    @Override
    public Paging<ArticleQueryVo> getArticlePageList(ArticleQueryParam articleQueryParam) {
        Page page = setPageParam(articleQueryParam, OrderItem.desc("add_time"));
        IPage<ArticleQueryVo> iPage = articleMapper.getArticlePageList(page, articleQueryParam);
        return new Paging(iPage);
    }

    @Override
    public void incVisitNum(int id) {
        articleMapper.incVisitNum(id);
    }
}
