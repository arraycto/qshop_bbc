package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreVertifyRecord;
import co.lq.modules.shop.service.StoreVertifyRecordService;
import co.lq.modules.shop.service.dto.StoreVertifyRecordQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-28
 */
@Api(tags = "店铺审核记录管理")
@RestController
@RequestMapping("/api/storeVertifyRecord")
public class StoreVertifyRecordController {

    private final StoreVertifyRecordService storeVertifyRecordService;

    public StoreVertifyRecordController(StoreVertifyRecordService storeVertifyRecordService) {
        this.storeVertifyRecordService = storeVertifyRecordService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeVertifyRecord:list')")
    public void download(HttpServletResponse response, StoreVertifyRecordQueryCriteria criteria) throws IOException {
        storeVertifyRecordService.download(storeVertifyRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺审核记录")
    @ApiOperation("查询店铺审核记录")
    @PreAuthorize("@el.check('storeVertifyRecord:list')")
    public ResponseEntity<Object> getStoreVertifyRecords(StoreVertifyRecordQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(storeVertifyRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺审核记录")
    @ApiOperation("新增店铺审核记录")
    @PreAuthorize("@el.check('storeVertifyRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreVertifyRecord resources) {
        storeVertifyRecordService.onVerify(resources.getStoreId(), resources.getStatus());
        resources.setVertifier(SecurityUtils.getUsername());
        return new ResponseEntity<>(storeVertifyRecordService.create(resources), HttpStatus.CREATED);
    }

    @PostMapping(value = "/onClosed")
    @Log("新增店铺关闭")
    @ApiOperation("新增店铺审核记录")
    @PreAuthorize("@el.check('storeVertifyRecord:closed')")
    public ResponseEntity<Object> onClosed(@Validated @RequestBody StoreVertifyRecord resources) {
        storeVertifyRecordService.onClosed(resources.getStoreId(), resources.getStatus());
        return new ResponseEntity<>(resources, HttpStatus.CREATED);
    }
}
