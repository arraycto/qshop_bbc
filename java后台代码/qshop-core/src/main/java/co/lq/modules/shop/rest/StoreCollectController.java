package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreCollect;
import co.lq.modules.shop.service.StoreCollectService;
import co.lq.modules.shop.service.dto.StoreCollectQueryCriteria;
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
* @date 2020-04-05
*/
@Api(tags = "店铺收藏表管理")
@RestController
@RequestMapping("/api/storeCollect")
public class StoreCollectController {

    private final StoreCollectService storeCollectService;

    public StoreCollectController(StoreCollectService storeCollectService) {
        this.storeCollectService = storeCollectService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeCollect:list')")
    public void download(HttpServletResponse response, StoreCollectQueryCriteria criteria) throws IOException {
        storeCollectService.download(storeCollectService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺收藏表")
    @ApiOperation("查询店铺收藏表")
    @PreAuthorize("@el.check('storeCollect:list')")
    public ResponseEntity<Object> getStoreCollects(StoreCollectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeCollectService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺收藏表")
    @ApiOperation("新增店铺收藏表")
    @PreAuthorize("@el.check('storeCollect:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreCollect resources){
        return new ResponseEntity<>(storeCollectService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺收藏表")
    @ApiOperation("修改店铺收藏表")
    @PreAuthorize("@el.check('storeCollect:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreCollect resources){
        storeCollectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺收藏表")
    @ApiOperation("删除店铺收藏表")
    @PreAuthorize("@el.check('storeCollect:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeCollectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}