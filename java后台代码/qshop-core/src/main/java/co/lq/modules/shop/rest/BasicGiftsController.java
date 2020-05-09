package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.BasicGifts;
import co.lq.modules.shop.service.BasicGiftsService;
import co.lq.modules.shop.service.dto.BasicGiftsQueryCriteria;
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
@Api(tags = "基础营销赠品管理")
@RestController
@RequestMapping("/api/basicGifts")
public class BasicGiftsController {

    private final BasicGiftsService basicGiftsService;

    public BasicGiftsController(BasicGiftsService basicGiftsService) {
        this.basicGiftsService = basicGiftsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('basicGifts:list')")
    public void download(HttpServletResponse response, BasicGiftsQueryCriteria criteria) throws IOException {
        basicGiftsService.download(basicGiftsService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询基础营销赠品")
    @ApiOperation("查询基础营销赠品")
    @PreAuthorize("@el.check('basicGifts:list')")
    public ResponseEntity<Object> getBasicGiftss(BasicGiftsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(basicGiftsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增基础营销赠品")
    @ApiOperation("新增基础营销赠品")
    @PreAuthorize("@el.check('basicGifts:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BasicGifts resources){
        return new ResponseEntity<>(basicGiftsService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改基础营销赠品")
    @ApiOperation("修改基础营销赠品")
    @PreAuthorize("@el.check('basicGifts:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BasicGifts resources){
        basicGiftsService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除基础营销赠品")
    @ApiOperation("删除基础营销赠品")
    @PreAuthorize("@el.check('basicGifts:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        basicGiftsService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}