package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreGiftsCategory;
import co.lq.modules.shop.service.StoreGiftsCategoryService;
import co.lq.modules.shop.service.dto.StoreGiftsCategoryQueryCriteria;
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
@Api(tags = "赠品分类管理")
@RestController
@RequestMapping("/api/storeGiftsCategory")
public class StoreGiftsCategoryController {

    private final StoreGiftsCategoryService storeGiftsCategoryService;

    public StoreGiftsCategoryController(StoreGiftsCategoryService storeGiftsCategoryService) {
        this.storeGiftsCategoryService = storeGiftsCategoryService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeGiftsCategory:list')")
    public void download(HttpServletResponse response, StoreGiftsCategoryQueryCriteria criteria) throws IOException {
        storeGiftsCategoryService.download(storeGiftsCategoryService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询赠品分类")
    @ApiOperation("查询赠品分类")
    @PreAuthorize("@el.check('storeGiftsCategory:list')")
    public ResponseEntity<Object> getStoreGiftsCategorys(StoreGiftsCategoryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeGiftsCategoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增赠品分类")
    @ApiOperation("新增赠品分类")
    @PreAuthorize("@el.check('storeGiftsCategory:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreGiftsCategory resources){
        return new ResponseEntity<>(storeGiftsCategoryService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改赠品分类")
    @ApiOperation("修改赠品分类")
    @PreAuthorize("@el.check('storeGiftsCategory:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreGiftsCategory resources){
        storeGiftsCategoryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除赠品分类")
    @ApiOperation("删除赠品分类")
    @PreAuthorize("@el.check('storeGiftsCategory:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeGiftsCategoryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}