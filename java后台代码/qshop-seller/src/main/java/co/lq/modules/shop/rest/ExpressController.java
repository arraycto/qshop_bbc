package co.lq.modules.shop.rest;

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

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.Express;
import co.lq.modules.shop.service.ExpressService;
import co.lq.modules.shop.service.dto.ExpressQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-12
 */
@Api(tags = "商城:快递管理")
@RestController
@RequestMapping("api")
public class ExpressController {

    private final ExpressService expressService;

    public ExpressController(ExpressService expressService) {
        this.expressService = expressService;
    }

    @Log("查询快递")
    @ApiOperation(value = "查询快递")
    @GetMapping(value = "/express")
    @PreAuthorize("@el.check('admin','EXPRESS_ALL','EXPRESS_SELECT')")
    public ResponseEntity getExpresss(ExpressQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(expressService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增快递")
    @ApiOperation(value = "新增快递")
    @PostMapping(value = "/express")
    @PreAuthorize("@el.check('admin','EXPRESS_ALL','EXPRESS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Express resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        return new ResponseEntity(expressService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改快递")
    @ApiOperation(value = "修改快递")
    @PutMapping(value = "/express")
    @PreAuthorize("@el.check('admin','EXPRESS_ALL','EXPRESS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Express resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        expressService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除快递")
    @ApiOperation(value = "删除快递")
    @DeleteMapping(value = "/express/{id}")
    @PreAuthorize("@el.check('admin','EXPRESS_ALL','EXPRESS_DELETE')")
    public ResponseEntity delete(@PathVariable Integer id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        expressService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
