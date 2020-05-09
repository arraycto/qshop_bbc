package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.PointsDonateRule;
import co.lq.modules.shop.service.PointsDonateRuleService;
import co.lq.modules.shop.service.dto.PointsDonateRuleQueryCriteria;
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
@Api(tags = "积分赠送规则管理")
@RestController
@RequestMapping("/api/pointsDonateRule")
public class PointsDonateRuleController {

    private final PointsDonateRuleService pointsDonateRuleService;

    public PointsDonateRuleController(PointsDonateRuleService pointsDonateRuleService) {
        this.pointsDonateRuleService = pointsDonateRuleService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pointsDonateRule:list')")
    public void download(HttpServletResponse response, PointsDonateRuleQueryCriteria criteria) throws IOException {
        pointsDonateRuleService.download(pointsDonateRuleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询积分赠送规则")
    @ApiOperation("查询积分赠送规则")
    @PreAuthorize("@el.check('pointsDonateRule:list')")
    public ResponseEntity<Object> getPointsDonateRules(PointsDonateRuleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pointsDonateRuleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增积分赠送规则")
    @ApiOperation("新增积分赠送规则")
    @PreAuthorize("@el.check('pointsDonateRule:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PointsDonateRule resources){
        return new ResponseEntity<>(pointsDonateRuleService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改积分赠送规则")
    @ApiOperation("修改积分赠送规则")
    @PreAuthorize("@el.check('pointsDonateRule:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PointsDonateRule resources){
        pointsDonateRuleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除积分赠送规则")
    @ApiOperation("删除积分赠送规则")
    @PreAuthorize("@el.check('pointsDonateRule:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        pointsDonateRuleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}