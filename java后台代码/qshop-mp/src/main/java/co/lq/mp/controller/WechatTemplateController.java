package co.lq.mp.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.mp.domain.WechatTemplate;
import co.lq.mp.service.WechatTemplateService;
import co.lq.mp.service.dto.WechatTemplateQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-10
 */
@Api(tags = "商城:微信模板管理")
@RestController
@RequestMapping("api")
public class WechatTemplateController {

    private final WechatTemplateService wechatTemplateService;

    public WechatTemplateController(WechatTemplateService wechatTemplateService) {
        this.wechatTemplateService = wechatTemplateService;
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "/wechatTemplate")
    @PreAuthorize("@el.check('admin','WECHATTEMPLATE_ALL','WECHATTEMPLATE_SELECT')")
    public ResponseEntity getWechatTemplates(WechatTemplateQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(wechatTemplateService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/wechatTemplate")
    @PreAuthorize("@el.check('admin','WECHATTEMPLATE_ALL','WECHATTEMPLATE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody WechatTemplate resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        return new ResponseEntity(wechatTemplateService.create(resources), HttpStatus.CREATED);
    }

    @ApiOperation(value = "修改")
    @PutMapping(value = "/wechatTemplate")
    @PreAuthorize("@el.check('admin','WECHATTEMPLATE_ALL','WECHATTEMPLATE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody WechatTemplate resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        wechatTemplateService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/wechatTemplate/{id}")
    @PreAuthorize("@el.check('admin','WECHATTEMPLATE_ALL','WECHATTEMPLATE_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        wechatTemplateService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
