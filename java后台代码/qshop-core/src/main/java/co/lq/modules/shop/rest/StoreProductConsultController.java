package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreProductConsult;
import co.lq.modules.shop.service.StoreProductConsultService;
import co.lq.modules.shop.service.dto.StoreProductConsultQueryCriteria;
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
@Api(tags = "商品资讯管理")
@RestController
@RequestMapping("/api/storeProductConsult")
public class StoreProductConsultController {

    private final StoreProductConsultService storeProductConsultService;

    public StoreProductConsultController(StoreProductConsultService storeProductConsultService) {
        this.storeProductConsultService = storeProductConsultService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeProductConsult:list')")
    public void download(HttpServletResponse response, StoreProductConsultQueryCriteria criteria) throws IOException {
        storeProductConsultService.download(storeProductConsultService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询商品资讯")
    @ApiOperation("查询商品资讯")
    @PreAuthorize("@el.check('storeProductConsult:list')")
    public ResponseEntity<Object> getStoreProductConsults(StoreProductConsultQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeProductConsultService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增商品资讯")
    @ApiOperation("新增商品资讯")
    @PreAuthorize("@el.check('storeProductConsult:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreProductConsult resources){
        return new ResponseEntity<>(storeProductConsultService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改商品资讯")
    @ApiOperation("修改商品资讯")
    @PreAuthorize("@el.check('storeProductConsult:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreProductConsult resources){
        storeProductConsultService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品资讯")
    @ApiOperation("删除商品资讯")
    @PreAuthorize("@el.check('storeProductConsult:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeProductConsultService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}