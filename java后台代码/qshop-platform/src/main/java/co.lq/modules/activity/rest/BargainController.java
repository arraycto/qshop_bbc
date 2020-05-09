package co.lq.modules.activity.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.modules.activity.domain.StoreBargain;
import co.lq.modules.activity.service.StoreBargainService;
import co.lq.modules.activity.service.dto.StoreBargainQueryCriteria;
import co.lq.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-22
 */
@Api(tags = "商城:砍价管理")
@RestController
@RequestMapping("api")
public class BargainController {

    private final StoreBargainService storeBargainService;

    public BargainController(StoreBargainService storeBargainService) {
        this.storeBargainService = storeBargainService;
    }

    @Log("查询砍价")
    @ApiOperation(value = "查询砍价")
    @GetMapping(value = "/storeBargain")
    @PreAuthorize("@el.check('admin','STOREBARGAIN_ALL','STOREBARGAIN_SELECT')")
    public ResponseEntity getStoreBargains(StoreBargainQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(storeBargainService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("修改砍价")
    @ApiOperation(value = "修改砍价")
    @PutMapping(value = "/storeBargain")
    @PreAuthorize("@el.check('admin','STOREBARGAIN_ALL','STOREBARGAIN_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreBargain resources) {

        if (ObjectUtil.isNotNull(resources.getStartTimeDate())) {
            resources.setStartTime(OrderUtil.dateToTimestamp(resources.getStartTimeDate()));
        }
        if (ObjectUtil.isNotNull(resources.getEndTimeDate())) {
            resources.setStopTime(OrderUtil.dateToTimestamp(resources.getEndTimeDate()));
        }
        if (ObjectUtil.isNull(resources.getId())) {
            resources.setAddTime(OrderUtil.getSecondTimestampTwo());
            return new ResponseEntity(storeBargainService.create(resources), HttpStatus.CREATED);
        } else {
            storeBargainService.update(resources);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @Log("删除砍价")
    @ApiOperation(value = "删除砍价")
    @DeleteMapping(value = "/storeBargain/{id}")
    @PreAuthorize("@el.check('admin','STOREBARGAIN_ALL','STOREBARGAIN_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeBargainService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
