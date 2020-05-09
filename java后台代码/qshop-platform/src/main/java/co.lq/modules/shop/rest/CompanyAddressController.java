package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CompanyAddress;
import co.lq.modules.shop.service.CompanyAddressService;
import co.lq.modules.shop.service.dto.CompanyAddressQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-27
 */
@Api(tags = "订单发货地址管理")
@RestController
@RequestMapping("/api/companyAddress")
public class CompanyAddressController {

    private final CompanyAddressService companyAddressService;

    public CompanyAddressController(CompanyAddressService companyAddressService) {
        this.companyAddressService = companyAddressService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('companyAddress:list')")
    public void download(HttpServletResponse response, CompanyAddressQueryCriteria criteria) throws IOException {
        companyAddressService.download(companyAddressService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单发货地址")
    @ApiOperation("查询订单发货地址")
    @PreAuthorize("@el.check('companyAddress:list')")
    public ResponseEntity<Object> getCompanyAddresss(CompanyAddressQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(companyAddressService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单发货地址")
    @ApiOperation("新增订单发货地址")
    @PreAuthorize("@el.check('companyAddress:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CompanyAddress resources) {
        return new ResponseEntity<>(companyAddressService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单发货地址")
    @ApiOperation("修改订单发货地址")
    @PreAuthorize("@el.check('companyAddress:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CompanyAddress resources) {
        companyAddressService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除订单发货地址")
    @ApiOperation("删除订单发货地址")
    @PreAuthorize("@el.check('companyAddress:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        companyAddressService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
