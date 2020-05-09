package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.PointsConsumeSetting;
import co.lq.modules.shop.service.PointsConsumeSettingService;
import co.lq.modules.shop.service.dto.PointsConsumeSettingQueryCriteria;
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
@Api(tags = "积分消费设置管理")
@RestController
@RequestMapping("/api/pointsConsumeSetting")
public class PointsConsumeSettingController {

    private final PointsConsumeSettingService pointsConsumeSettingService;

    public PointsConsumeSettingController(PointsConsumeSettingService pointsConsumeSettingService) {
        this.pointsConsumeSettingService = pointsConsumeSettingService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pointsConsumeSetting:list')")
    public void download(HttpServletResponse response, PointsConsumeSettingQueryCriteria criteria) throws IOException {
        pointsConsumeSettingService.download(pointsConsumeSettingService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询积分消费设置")
    @ApiOperation("查询积分消费设置")
    @PreAuthorize("@el.check('pointsConsumeSetting:list')")
    public ResponseEntity<Object> getPointsConsumeSettings(PointsConsumeSettingQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pointsConsumeSettingService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增积分消费设置")
    @ApiOperation("新增积分消费设置")
    @PreAuthorize("@el.check('pointsConsumeSetting:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PointsConsumeSetting resources){
        return new ResponseEntity<>(pointsConsumeSettingService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改积分消费设置")
    @ApiOperation("修改积分消费设置")
    @PreAuthorize("@el.check('pointsConsumeSetting:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PointsConsumeSetting resources){
        pointsConsumeSettingService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除积分消费设置")
    @ApiOperation("删除积分消费设置")
    @PreAuthorize("@el.check('pointsConsumeSetting:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        pointsConsumeSettingService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}