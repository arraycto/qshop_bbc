package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.PointsSignRule;
import co.lq.modules.shop.service.PointsSignRuleService;
import co.lq.modules.shop.service.dto.PointsSignRuleQueryCriteria;
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
@Api(tags = "积分签到规则管理")
@RestController
@RequestMapping("/api/pointsSignRule")
public class PointsSignRuleController {

    private final PointsSignRuleService pointsSignRuleService;

    public PointsSignRuleController(PointsSignRuleService pointsSignRuleService) {
        this.pointsSignRuleService = pointsSignRuleService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pointsSignRule:list')")
    public void download(HttpServletResponse response, PointsSignRuleQueryCriteria criteria) throws IOException {
        pointsSignRuleService.download(pointsSignRuleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询积分签到规则")
    @ApiOperation("查询积分签到规则")
    @PreAuthorize("@el.check('pointsSignRule:list')")
    public ResponseEntity<Object> getPointsSignRules(PointsSignRuleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pointsSignRuleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增积分签到规则")
    @ApiOperation("新增积分签到规则")
    @PreAuthorize("@el.check('pointsSignRule:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PointsSignRule resources){
        return new ResponseEntity<>(pointsSignRuleService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改积分签到规则")
    @ApiOperation("修改积分签到规则")
    @PreAuthorize("@el.check('pointsSignRule:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PointsSignRule resources){
        pointsSignRuleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除积分签到规则")
    @ApiOperation("删除积分签到规则")
    @PreAuthorize("@el.check('pointsSignRule:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        pointsSignRuleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}