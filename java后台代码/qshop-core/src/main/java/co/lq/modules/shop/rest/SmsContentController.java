package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.SmsContent;
import co.lq.modules.shop.service.SmsContentService;
import co.lq.modules.shop.service.dto.SmsContentQueryCriteria;
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
@Api(tags = "短信模版内容管理")
@RestController
@RequestMapping("/api/smsContent")
public class SmsContentController {

    private final SmsContentService smsContentService;

    public SmsContentController(SmsContentService smsContentService) {
        this.smsContentService = smsContentService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('smsContent:list')")
    public void download(HttpServletResponse response, SmsContentQueryCriteria criteria) throws IOException {
        smsContentService.download(smsContentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询短信模版内容")
    @ApiOperation("查询短信模版内容")
    @PreAuthorize("@el.check('smsContent:list')")
    public ResponseEntity<Object> getSmsContents(SmsContentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(smsContentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增短信模版内容")
    @ApiOperation("新增短信模版内容")
    @PreAuthorize("@el.check('smsContent:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SmsContent resources){
        return new ResponseEntity<>(smsContentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改短信模版内容")
    @ApiOperation("修改短信模版内容")
    @PreAuthorize("@el.check('smsContent:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SmsContent resources){
        smsContentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除短信模版内容")
    @ApiOperation("删除短信模版内容")
    @PreAuthorize("@el.check('smsContent:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        smsContentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}