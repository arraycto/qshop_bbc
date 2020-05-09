package co.lq.modules.shop.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import co.lq.modules.shop.domain.Catalog;
import co.lq.modules.shop.service.CatalogService;
import co.lq.modules.shop.service.dto.CatalogDTO;
import co.lq.modules.shop.service.dto.CatalogQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-03-10
 */
@Api(tags = "平台类目管理")
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('catalog:list')")
    public void download(HttpServletResponse response, CatalogQueryCriteria criteria) throws IOException {
        catalogService.download(catalogService.queryAll(criteria), response);
    }

    @GetMapping(value = "/list")
    @Log("查询平台类目")
    @ApiOperation("查询平台类目")
    @PreAuthorize("@el.check('admin', 'catalog:list')")
    public ResponseEntity<Object> getQshopCatalogs(CatalogQueryCriteria criteria) {
        List<CatalogDTO> catalogDTOList = catalogService.queryAll(criteria);
        return new ResponseEntity<>(catalogService.buildTree(catalogDTOList), HttpStatus.OK);
    }

    @GetMapping(value = "/detail/{id}")
    @Log("查询平台类目")
    @ApiOperation("查询平台类目")
    @PreAuthorize("@el.check('catalog:detail')")
    public ResponseEntity getQshopCatalogInfo(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(catalogService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @Log("查询平台类目")
    @ApiOperation("查询平台类目")
    @PreAuthorize("@el.check('catalog:list')")
    public ResponseEntity getQshopCatalogList(CatalogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(catalogService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增平台类目")
    @ApiOperation("新增平台类目")
    @PreAuthorize("@el.check('catalog:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Catalog resources) {
        return new ResponseEntity<>(catalogService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改平台类目")
    @ApiOperation("修改平台类目")
    @PreAuthorize("@el.check('catalog:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Catalog resources) {
        catalogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除平台类目")
    @ApiOperation("删除平台类目")
    @PreAuthorize("@el.check('catalog:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        catalogService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
