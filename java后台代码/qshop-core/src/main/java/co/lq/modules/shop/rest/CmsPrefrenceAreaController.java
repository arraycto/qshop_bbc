package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CmsPrefrenceArea;
import co.lq.modules.shop.service.CmsPrefrenceAreaService;
import co.lq.modules.shop.service.dto.CmsPrefrenceAreaQueryCriteria;
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
* @date 2020-04-11
*/
@Api(tags = "优选专区管理")
@RestController
@RequestMapping("/api/cmsPrefrenceArea")
public class CmsPrefrenceAreaController {

    private final CmsPrefrenceAreaService cmsPrefrenceAreaService;

    public CmsPrefrenceAreaController(CmsPrefrenceAreaService cmsPrefrenceAreaService) {
        this.cmsPrefrenceAreaService = cmsPrefrenceAreaService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cmsPrefrenceArea:list')")
    public void download(HttpServletResponse response, CmsPrefrenceAreaQueryCriteria criteria) throws IOException {
        cmsPrefrenceAreaService.download(cmsPrefrenceAreaService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询优选专区")
    @ApiOperation("查询优选专区")
    @PreAuthorize("@el.check('cmsPrefrenceArea:list')")
    public ResponseEntity<Object> getCmsPrefrenceAreas(CmsPrefrenceAreaQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cmsPrefrenceAreaService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增优选专区")
    @ApiOperation("新增优选专区")
    @PreAuthorize("@el.check('cmsPrefrenceArea:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CmsPrefrenceArea resources){
        return new ResponseEntity<>(cmsPrefrenceAreaService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改优选专区")
    @ApiOperation("修改优选专区")
    @PreAuthorize("@el.check('cmsPrefrenceArea:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CmsPrefrenceArea resources){
        cmsPrefrenceAreaService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除优选专区")
    @ApiOperation("删除优选专区")
    @PreAuthorize("@el.check('cmsPrefrenceArea:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        cmsPrefrenceAreaService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}