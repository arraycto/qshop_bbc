package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.FeightTemplate;
import co.lq.modules.shop.service.FeightTemplateService;
import co.lq.modules.shop.service.dto.FeightTemplateQueryCriteria;
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
@Api(tags = "订单运费模板管理")
@RestController
@RequestMapping("/api/feightTemplate")
public class FeightTemplateController {

    private final FeightTemplateService feightTemplateService;

    public FeightTemplateController(FeightTemplateService feightTemplateService) {
        this.feightTemplateService = feightTemplateService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('feightTemplate:list')")
    public void download(HttpServletResponse response, FeightTemplateQueryCriteria criteria) throws IOException {
        feightTemplateService.download(feightTemplateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单运费模板")
    @ApiOperation("查询订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:list')")
    public ResponseEntity<Object> getFeightTemplates(FeightTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(feightTemplateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单运费模板")
    @ApiOperation("新增订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody FeightTemplate resources){
        return new ResponseEntity<>(feightTemplateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单运费模板")
    @ApiOperation("修改订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody FeightTemplate resources){
        feightTemplateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单运费模板")
    @ApiOperation("删除订单运费模板")
    @PreAuthorize("@el.check('feightTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        feightTemplateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}