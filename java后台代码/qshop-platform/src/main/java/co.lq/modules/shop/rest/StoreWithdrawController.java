package co.lq.modules.shop.rest;

import java.io.IOException;
import java.sql.Timestamp;

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
import co.lq.modules.shop.domain.StoreWithdraw;
import co.lq.modules.shop.domain.StoreWithdrawRecord;
import co.lq.modules.shop.service.StoreWithdrawRecordService;
import co.lq.modules.shop.service.StoreWithdrawService;
import co.lq.modules.shop.service.dto.StoreWithdrawQueryCriteria;
import co.lq.modules.shop.service.param.StoreWithdrawStatusParam;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-04-13
 */
@Api(tags = "提现表管理")
@RestController
@RequestMapping("/api/storeWithdraw")
public class StoreWithdrawController {

    private final StoreWithdrawService       storeWithdrawService;
    private final StoreWithdrawRecordService storeWithdrawRecordService;

    public StoreWithdrawController(StoreWithdrawService storeWithdrawService,
                                   StoreWithdrawRecordService storeWithdrawRecordService) {
        this.storeWithdrawService = storeWithdrawService;
        this.storeWithdrawRecordService = storeWithdrawRecordService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeWithdraw:list')")
    public void download(HttpServletResponse response, StoreWithdrawQueryCriteria criteria) throws IOException {
        storeWithdrawService.download(storeWithdrawService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询提现表")
    @ApiOperation("查询提现表")
    @PreAuthorize("@el.check('storeWithdraw:list')")
    public ResponseEntity<Object> getStoreWithdraws(StoreWithdrawQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(storeWithdrawService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增提现表")
    @ApiOperation("新增提现表")
    @PreAuthorize("@el.check('storeWithdraw:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreWithdraw resources) {
        return new ResponseEntity<>(storeWithdrawService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改提现表")
    @ApiOperation("修改提现表")
    @PreAuthorize("@el.check('storeWithdraw:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreWithdraw resources) {
        storeWithdrawService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "verifyStatus")
    @Log("修改提现表状态")
    @ApiOperation("修改提现表状态")
    @PreAuthorize("@el.check('storeWithdraw:edit')")
    public ResponseEntity<Object> updateStatus(@Validated @RequestBody StoreWithdrawStatusParam resources) {
        StoreWithdraw storeWithdraw = new StoreWithdraw();
        storeWithdraw.setId(resources.getId());
        if (resources.getApplyStatus() == 1) {
            storeWithdraw.setApplyStatus(2);
        } else {
            storeWithdraw.setApplyStatus(3);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        storeWithdraw.setVerifyTime(timestamp);
        storeWithdrawService.update(storeWithdraw);

        StoreWithdrawRecord storeWithdrawRecord = new StoreWithdrawRecord();
        if (resources.getApplyStatus() == 1) {
            storeWithdrawRecord.setStatus(1);
        } else {
            storeWithdrawRecord.setStatus(0);
        }
        storeWithdrawRecord.setDetail(resources.getDetail());
        storeWithdrawRecord.setVertifier(SecurityUtils.getUsername());
        storeWithdrawRecord.setStoreWithdrawId(resources.getId());
        storeWithdrawRecord.setStoreId(resources.getStoreId());
        storeWithdrawRecordService.create(storeWithdrawRecord);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除提现表")
    @ApiOperation("删除提现表")
    @PreAuthorize("@el.check('storeWithdraw:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeWithdrawService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
