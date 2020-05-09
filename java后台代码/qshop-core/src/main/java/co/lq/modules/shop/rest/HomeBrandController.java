package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.HomeBrand;
import co.lq.modules.shop.service.HomeBrandService;
import co.lq.modules.shop.service.dto.HomeBrandQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
@Api(tags = "首页推荐品牌表管理")
@RestController
@RequestMapping("/api/homeBrand")
public class HomeBrandController {

    private final HomeBrandService homeBrandService;

    public HomeBrandController(HomeBrandService homeBrandService) {
        this.homeBrandService = homeBrandService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('homeBrand:list')")
    public void download(HttpServletResponse response, HomeBrandQueryCriteria criteria) throws IOException {
        homeBrandService.download(homeBrandService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询首页推荐品牌表")
    @ApiOperation("查询首页推荐品牌表")
    @PreAuthorize("@el.check('homeBrand:list')")
    public ResponseEntity<Object> getHomeBrands(HomeBrandQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(homeBrandService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增首页推荐品牌表")
    @ApiOperation("新增首页推荐品牌表")
    @PreAuthorize("@el.check('homeBrand:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody HomeBrand resources){
        return new ResponseEntity<>(homeBrandService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改首页推荐品牌表")
    @ApiOperation("修改首页推荐品牌表")
    @PreAuthorize("@el.check('homeBrand:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HomeBrand resources){
        homeBrandService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除首页推荐品牌表")
    @ApiOperation("删除首页推荐品牌表")
    @PreAuthorize("@el.check('homeBrand:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        homeBrandService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}