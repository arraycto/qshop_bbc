package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.SmsContentMsg;
import co.lq.modules.shop.service.SmsContentMsgService;
import co.lq.modules.shop.service.dto.SmsContentMsgQueryCriteria;
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
@Api(tags = "短信发送记录管理")
@RestController
@RequestMapping("/api/smsContentMsg")
public class SmsContentMsgController {

    private final SmsContentMsgService smsContentMsgService;

    public SmsContentMsgController(SmsContentMsgService smsContentMsgService) {
        this.smsContentMsgService = smsContentMsgService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('smsContentMsg:list')")
    public void download(HttpServletResponse response, SmsContentMsgQueryCriteria criteria) throws IOException {
        smsContentMsgService.download(smsContentMsgService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询短信发送记录")
    @ApiOperation("查询短信发送记录")
    @PreAuthorize("@el.check('smsContentMsg:list')")
    public ResponseEntity<Object> getSmsContentMsgs(SmsContentMsgQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(smsContentMsgService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增短信发送记录")
    @ApiOperation("新增短信发送记录")
    @PreAuthorize("@el.check('smsContentMsg:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SmsContentMsg resources){
        return new ResponseEntity<>(smsContentMsgService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改短信发送记录")
    @ApiOperation("修改短信发送记录")
    @PreAuthorize("@el.check('smsContentMsg:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SmsContentMsg resources){
        smsContentMsgService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除短信发送记录")
    @ApiOperation("删除短信发送记录")
    @PreAuthorize("@el.check('smsContentMsg:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        smsContentMsgService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}