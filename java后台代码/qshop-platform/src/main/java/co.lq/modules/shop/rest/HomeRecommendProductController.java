package co.lq.modules.shop.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.HomeRecommendProduct;
import co.lq.modules.shop.service.HomeRecommendProductService;
import co.lq.modules.shop.service.dto.HomeRecommendProductQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "人气推荐商品表管理")
@RestController
@RequestMapping("/api/homeRecommendProduct")
public class HomeRecommendProductController {

    private final HomeRecommendProductService homeRecommendProductService;

    public HomeRecommendProductController(HomeRecommendProductService homeRecommendProductService) {
        this.homeRecommendProductService = homeRecommendProductService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('homeRecommendProduct:list')")
    public void download(HttpServletResponse response, HomeRecommendProductQueryCriteria criteria) throws IOException {
        homeRecommendProductService.download(homeRecommendProductService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询人气推荐商品表")
    @ApiOperation("查询人气推荐商品表")
    @PreAuthorize("@el.check('homeRecommendProduct:list')")
    public ResponseEntity<Object> getHomeRecommendProducts(HomeRecommendProductQueryCriteria criteria,
                                                           Pageable pageable) {
        return new ResponseEntity<>(homeRecommendProductService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增人气推荐商品表")
    @ApiOperation("新增人气推荐商品表")
    @PreAuthorize("@el.check('homeRecommendProduct:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody List<HomeRecommendProduct> resources) {
        return new ResponseEntity<>(homeRecommendProductService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改人气推荐商品表")
    @ApiOperation("修改人气推荐商品表")
    @PreAuthorize("@el.check('homeRecommendProduct:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HomeRecommendProduct resources) {
        homeRecommendProductService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除人气推荐商品表")
    @ApiOperation("删除人气推荐商品表")
    @PreAuthorize("@el.check('homeRecommendProduct:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        homeRecommendProductService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
