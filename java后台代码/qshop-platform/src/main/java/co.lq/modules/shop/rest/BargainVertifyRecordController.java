package co.lq.modules.shop.rest;

import java.io.IOException;

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
import co.lq.modules.activity.service.StoreBargainService;
import co.lq.modules.activity.service.dto.StoreBargainDTO;
import co.lq.modules.shop.domain.BargainVertifyRecord;
import co.lq.modules.shop.service.BargainVertifyRecordService;
import co.lq.modules.shop.service.dto.BargainVertifyRecordQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-11
 */
@Api(tags = "砍价审核纪录管理")
@RestController
@RequestMapping("/api/bargainVertifyRecord")
public class BargainVertifyRecordController {

    private final BargainVertifyRecordService bargainVertifyRecordService;
    private final StoreBargainService         storeBargainService;

    public BargainVertifyRecordController(BargainVertifyRecordService bargainVertifyRecordService,
                                          StoreBargainService storeBargainService) {
        this.bargainVertifyRecordService = bargainVertifyRecordService;
        this.storeBargainService = storeBargainService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bargainVertifyRecord:list')")
    public void download(HttpServletResponse response, BargainVertifyRecordQueryCriteria criteria) throws IOException {
        bargainVertifyRecordService.download(bargainVertifyRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询砍价审核纪录")
    @ApiOperation("查询砍价审核纪录")
    @PreAuthorize("@el.check('bargainVertifyRecord:list')")
    public ResponseEntity<Object> getBargainVertifyRecords(BargainVertifyRecordQueryCriteria criteria,
                                                           Pageable pageable) {
        return new ResponseEntity<>(bargainVertifyRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增砍价审核纪录")
    @ApiOperation("新增砍价审核纪录")
    @PreAuthorize("@el.check('bargainVertifyRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BargainVertifyRecord resources) {
        storeBargainService.onVerify(resources.getBargainId(), resources.getStatus());
        StoreBargainDTO storeBargainDTO = storeBargainService.findById(resources.getBargainId());
        if (resources.getStatus() == 1) {
            resources.setStatus(0);
        } else {
            resources.setStatus(1);
        }
        resources.setStoreId(storeBargainDTO.getStoreId());
        resources.setVertifyMan(SecurityUtils.getUsername());
        return new ResponseEntity<>(bargainVertifyRecordService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改砍价审核纪录")
    @ApiOperation("修改砍价审核纪录")
    @PreAuthorize("@el.check('bargainVertifyRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BargainVertifyRecord resources) {
        bargainVertifyRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除砍价审核纪录")
    @ApiOperation("删除砍价审核纪录")
    @PreAuthorize("@el.check('bargainVertifyRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        bargainVertifyRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
