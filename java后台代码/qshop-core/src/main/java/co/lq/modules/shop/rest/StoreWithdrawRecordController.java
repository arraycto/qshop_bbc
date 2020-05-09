package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreWithdrawRecord;
import co.lq.modules.shop.service.StoreWithdrawRecordService;
import co.lq.modules.shop.service.dto.StoreWithdrawRecordQueryCriteria;
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
* @date 2020-04-13
*/
@Api(tags = "提现审核表管理")
@RestController
@RequestMapping("/api/storeWithdrawRecord")
public class StoreWithdrawRecordController {

    private final StoreWithdrawRecordService storeWithdrawRecordService;

    public StoreWithdrawRecordController(StoreWithdrawRecordService storeWithdrawRecordService) {
        this.storeWithdrawRecordService = storeWithdrawRecordService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeWithdrawRecord:list')")
    public void download(HttpServletResponse response, StoreWithdrawRecordQueryCriteria criteria) throws IOException {
        storeWithdrawRecordService.download(storeWithdrawRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询提现审核表")
    @ApiOperation("查询提现审核表")
    @PreAuthorize("@el.check('storeWithdrawRecord:list')")
    public ResponseEntity<Object> getStoreWithdrawRecords(StoreWithdrawRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(storeWithdrawRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增提现审核表")
    @ApiOperation("新增提现审核表")
    @PreAuthorize("@el.check('storeWithdrawRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreWithdrawRecord resources){
        return new ResponseEntity<>(storeWithdrawRecordService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改提现审核表")
    @ApiOperation("修改提现审核表")
    @PreAuthorize("@el.check('storeWithdrawRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreWithdrawRecord resources){
        storeWithdrawRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除提现审核表")
    @ApiOperation("删除提现审核表")
    @PreAuthorize("@el.check('storeWithdrawRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeWithdrawRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}