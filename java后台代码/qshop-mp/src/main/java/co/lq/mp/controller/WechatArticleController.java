package co.lq.mp.controller;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import co.lq.mp.domain.Article;
import co.lq.mp.service.ArticleService;
import co.lq.mp.service.dto.ArticleDTO;
import co.lq.mp.service.dto.ArticleQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-07
 */
@Api(tags = "商城:微信图文管理")
@RestController
@RequestMapping("api")
public class WechatArticleController {

    private final ArticleService articleService;

    public WechatArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "/article")
    @PreAuthorize("@el.check('admin','ARTICLE_ALL','ARTICLE_SELECT')")
    public ResponseEntity getArticle(ArticleQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(articleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/article")
    @PreAuthorize("@el.check('admin','ARTICLE_ALL','ARTICLE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Article resources) {
        resources.setAddTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        return new ResponseEntity(articleService.create(resources), HttpStatus.CREATED);
    }

    @ApiOperation(value = "修改")
    @PutMapping(value = "/article")
    @PreAuthorize("@el.check('admin','ARTICLE_ALL','ARTICLE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Article resources) {
        articleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/article/{id}")
    @PreAuthorize("@el.check('admin','ARTICLE_ALL','ARTICLE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        articleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "发布文章")
    @GetMapping(value = "/article/publish/{id}")
    @PreAuthorize("@el.check('admin','ARTICLE_ALL','ARTICLE_DELETE')")
    public ResponseEntity publish(@PathVariable Long id) throws Exception {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        ArticleDTO articleDTO = articleService.findById(id);
        articleService.uploadNews(articleDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

}
