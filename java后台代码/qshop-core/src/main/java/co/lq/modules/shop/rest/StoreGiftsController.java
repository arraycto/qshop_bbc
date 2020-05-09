package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreGifts;
import co.lq.modules.shop.service.StoreGiftsService;
import co.lq.modules.shop.service.dto.StoreGiftsQueryCriteria;
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
@Api(tags = "赠品管理")
@RestController
@RequestMapping("/api/storeGifts")
public class StoreGiftsController {

    private final StoreGiftsService storeGiftsService;

    public StoreGiftsController(StoreGiftsService storeGiftsService) {
        this.storeGiftsService = storeGiftsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeGifts:list')")
    public void download(HttpServletResponse response, StoreGiftsQueryCriteria criteria) throws IOException {
        storeGiftsService.download(storeGiftsService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询赠品")
    @ApiOperation("查询赠品")
    @PreAuthorize("@el.check('storeGifts:list')")
    public ResponseEntity<Object> getStoreGiftss(StoreGiftsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeGiftsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增赠品")
    @ApiOperation("新增赠品")
    @PreAuthorize("@el.check('storeGifts:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreGifts resources){
        return new ResponseEntity<>(storeGiftsService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改赠品")
    @ApiOperation("修改赠品")
    @PreAuthorize("@el.check('storeGifts:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreGifts resources){
        storeGiftsService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除赠品")
    @ApiOperation("删除赠品")
    @PreAuthorize("@el.check('storeGifts:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeGiftsService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}