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
import co.lq.modules.activity.service.StoreCombinationService;
import co.lq.modules.shop.domain.CombinationVertifyRecord;
import co.lq.modules.shop.service.CombinationVertifyRecordService;
import co.lq.modules.shop.service.dto.CombinationVertifyRecordQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-11
 */
@Api(tags = "拼团审核纪录管理")
@RestController
@RequestMapping("/api/combinationVertifyRecord")
public class CombinationVertifyRecordController {

    private final CombinationVertifyRecordService combinationVertifyRecordService;
    private final StoreCombinationService         storeCombinationService;

    public CombinationVertifyRecordController(CombinationVertifyRecordService combinationVertifyRecordService,
                                              StoreCombinationService storeCombinationService) {
        this.combinationVertifyRecordService = combinationVertifyRecordService;
        this.storeCombinationService = storeCombinationService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('combinationVertifyRecord:list')")
    public void download(HttpServletResponse response, CombinationVertifyRecordQueryCriteria criteria)
            throws IOException {
        combinationVertifyRecordService.download(combinationVertifyRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询拼团审核纪录")
    @ApiOperation("查询拼团审核纪录")
    @PreAuthorize("@el.check('combinationVertifyRecord:list')")
    public ResponseEntity<Object> getCombinationVertifyRecords(CombinationVertifyRecordQueryCriteria criteria,
                                                               Pageable pageable) {
        return new ResponseEntity<>(combinationVertifyRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增拼团审核纪录")
    @ApiOperation("新增拼团审核纪录")
    @PreAuthorize("@el.check('combinationVertifyRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CombinationVertifyRecord resources) {
        //System.out.println(status);
        storeCombinationService.onVerify(resources.getCombinationId(), resources.getStatus());
        if (resources.getStatus() == 1) {
            resources.setStatus(0);
        } else {
            resources.setStatus(1);
        }
        resources.setVertifyMan(SecurityUtils.getUsername());
        return new ResponseEntity<>(combinationVertifyRecordService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改拼团审核纪录")
    @ApiOperation("修改拼团审核纪录")
    @PreAuthorize("@el.check('combinationVertifyRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CombinationVertifyRecord resources) {
        combinationVertifyRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除拼团审核纪录")
    @ApiOperation("删除拼团审核纪录")
    @PreAuthorize("@el.check('combinationVertifyRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        combinationVertifyRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
