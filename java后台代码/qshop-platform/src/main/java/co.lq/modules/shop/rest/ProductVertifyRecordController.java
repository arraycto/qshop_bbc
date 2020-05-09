package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.ProductVertifyRecord;
import co.lq.modules.shop.service.ProductVertifyRecordService;
import co.lq.modules.shop.service.StoreProductService;
import co.lq.modules.shop.service.dto.ProductVertifyRecordQueryCriteria;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-10
 */
@Api(tags = "店铺审核纪录管理")
@RestController
@RequestMapping("/api/productVertifyRecord")
public class ProductVertifyRecordController {

    private final ProductVertifyRecordService productVertifyRecordService;
    private final StoreProductService         storeProductService;

    public ProductVertifyRecordController(ProductVertifyRecordService productVertifyRecordService,
                                          StoreProductService storeProductService) {
        this.productVertifyRecordService = productVertifyRecordService;
        this.storeProductService = storeProductService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('productVertifyRecord:list')")
    public void download(HttpServletResponse response, ProductVertifyRecordQueryCriteria criteria) throws IOException {
        productVertifyRecordService.download(productVertifyRecordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询店铺审核纪录")
    @ApiOperation("查询店铺审核纪录")
    @PreAuthorize("@el.check('productVertifyRecord:list')")
    public ResponseEntity<Object> getProductVertifyRecords(ProductVertifyRecordQueryCriteria criteria,
                                                           Pageable pageable) {
        return new ResponseEntity<>(productVertifyRecordService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Log("查询店铺审核纪录")
    @ApiOperation("查询店铺审核纪录")
    @PreAuthorize("@el.check('productVertifyRecord:detail')")
    public ResponseEntity<Object> getProductVertifyRecord(@PathVariable Integer id) {
        ProductVertifyRecordQueryCriteria criteria = new ProductVertifyRecordQueryCriteria();
        criteria.setProductId(id);
        return new ResponseEntity<>(productVertifyRecordService.queryAll(criteria), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺审核纪录")
    @ApiOperation("新增店铺审核纪录")
    @PreAuthorize("@el.check('productVertifyRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ProductVertifyRecord resources) {
        storeProductService.onVerify(resources.getProductId(), resources.getStatus());
        if (resources.getStatus() == 1) {
            resources.setStatus(0);
        } else {
            resources.setStatus(1);
        }
        resources.setVertifier(SecurityUtils.getUsername());
        StoreProductDTO storeProductDTO = storeProductService.findById(resources.getProductId());
        resources.setStoreId(storeProductDTO.getStoreId());
        return new ResponseEntity<>(productVertifyRecordService.create(resources), HttpStatus.CREATED);
    }
}
