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
import co.lq.modules.activity.domain.StoreSeckill;
import co.lq.modules.activity.service.StoreSeckillService;
import co.lq.modules.activity.service.dto.StoreSeckillQueryCriteria;
import co.lq.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-14
 */
@Api(tags = "商城:秒杀管理")
@RestController
@RequestMapping("api")
public class SeckillController {

    private final StoreSeckillService storeSeckillService;

    public SeckillController(StoreSeckillService storeSeckillService) {
        this.storeSeckillService = storeSeckillService;
    }

    @Log("列表")
    @ApiOperation(value = "列表")
    @GetMapping(value = "/storeSeckill")
    @PreAuthorize("@el.check('admin','STORESECKILL_ALL','STORESECKILL_SELECT')")
    public ResponseEntity getStoreSeckills(StoreSeckillQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(storeSeckillService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("发布")
    @ApiOperation(value = "发布")
    @PutMapping(value = "/storeSeckill")
    @PreAuthorize("@el.check('admin','STORESECKILL_ALL','STORESECKILL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreSeckill resources) {
        if (ObjectUtil.isNotNull(resources.getStartTimeDate())) {
            resources.setStartTime(OrderUtil.dateToTimestamp(resources.getStartTimeDate()));
        }
        if (ObjectUtil.isNotNull(resources.getEndTimeDate())) {
            resources.setStopTime(OrderUtil.dateToTimestamp(resources.getEndTimeDate()));
        }
        if (ObjectUtil.isNull(resources.getId())) {
            resources.setAddTime(String.valueOf(OrderUtil.getSecondTimestampTwo()));
            return new ResponseEntity(storeSeckillService.create(resources), HttpStatus.CREATED);
        } else {
            storeSeckillService.update(resources);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/storeSeckill/{id}")
    @PreAuthorize("@el.check('admin','STORESECKILL_ALL','STORESECKILL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeSeckillService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
