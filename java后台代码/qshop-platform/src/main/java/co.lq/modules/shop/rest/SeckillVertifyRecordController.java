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
import co.lq.modules.activity.service.StoreSeckillService;
import co.lq.modules.shop.domain.SeckillVertifyRecord;
import co.lq.modules.shop.service.SeckillVertifyRecordService;
import co.lq.modules.shop.service.dto.SeckillVertifyRecordQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-11
 */
@Api(tags = "秒杀审核纪录管理")
@RestController
@RequestMapping("/api/seckillVertifyRecord")
public class SeckillVertifyRecordController {

    private final SeckillVertifyRecordService seckillVertifyRecordService;
    private final StoreSeckillService         storeSeckillService;

    public SeckillVertifyRecordController(SeckillVertifyRecordService seckillVertifyRecordService,
                                          StoreSeckillService storeSeckillService) {
        this.seckillVertifyRecordService = seckillVertifyRecordService;
        this.storeSeckillService = storeSeckillService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('seckillVertifyRecord:list')")
    public void download(HttpServletResponse response, SeckillVertifyRecordQueryCriteria criteria) throws IOException {
        seckillVertifyRecordService.download(seckillVertifyRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询秒杀审核纪录")
    @ApiOperation("查询秒杀审核纪录")
    @PreAuthorize("@el.check('seckillVertifyRecord:list')")
    public ResponseEntity<Object> getSeckillVertifyRecords(SeckillVertifyRecordQueryCriteria criteria,
                                                           Pageable pageable) {
        return new ResponseEntity<>(seckillVertifyRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增秒杀审核纪录")
    @ApiOperation("新增秒杀审核纪录")
    @PreAuthorize("@el.check('seckillVertifyRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SeckillVertifyRecord resources) {
        storeSeckillService.onVerify(resources.getSeckillId(), resources.getStatus());
        if (resources.getStatus() == 1) {
            resources.setStatus(0);
        } else {
            resources.setStatus(1);
        }
        resources.setVertifyMan(SecurityUtils.getUsername());
        return new ResponseEntity<>(seckillVertifyRecordService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改秒杀审核纪录")
    @ApiOperation("修改秒杀审核纪录")
    @PreAuthorize("@el.check('seckillVertifyRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SeckillVertifyRecord resources) {
        seckillVertifyRecordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除秒杀审核纪录")
    @ApiOperation("删除秒杀审核纪录")
    @PreAuthorize("@el.check('seckillVertifyRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        seckillVertifyRecordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
