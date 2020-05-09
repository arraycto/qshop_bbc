package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreOrderReturnReason;
import co.lq.modules.shop.service.StoreOrderReturnReasonService;
import co.lq.modules.shop.service.dto.StoreOrderReturnReasonQueryCriteria;
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
* @date 2020-03-29
*/
@Api(tags = "商家退货原因表管理")
@RestController
@RequestMapping("/api/storeOrderReturnReason")
public class StoreOrderReturnReasonController {

    private final StoreOrderReturnReasonService storeOrderReturnReasonService;

    public StoreOrderReturnReasonController(StoreOrderReturnReasonService storeOrderReturnReasonService) {
        this.storeOrderReturnReasonService = storeOrderReturnReasonService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeOrderReturnReason:list')")
    public void download(HttpServletResponse response, StoreOrderReturnReasonQueryCriteria criteria) throws IOException {
        storeOrderReturnReasonService.download(storeOrderReturnReasonService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询商家退货原因表")
    @ApiOperation("查询商家退货原因表")
    @PreAuthorize("@el.check('storeOrderReturnReason:list')")
    public ResponseEntity<Object> getStoreOrderReturnReasons(StoreOrderReturnReasonQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeOrderReturnReasonService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增商家退货原因表")
    @ApiOperation("新增商家退货原因表")
    @PreAuthorize("@el.check('storeOrderReturnReason:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreOrderReturnReason resources){
        return new ResponseEntity<>(storeOrderReturnReasonService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改商家退货原因表")
    @ApiOperation("修改商家退货原因表")
    @PreAuthorize("@el.check('storeOrderReturnReason:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreOrderReturnReason resources){
        storeOrderReturnReasonService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除商家退货原因表")
    @ApiOperation("删除商家退货原因表")
    @PreAuthorize("@el.check('storeOrderReturnReason:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeOrderReturnReasonService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}