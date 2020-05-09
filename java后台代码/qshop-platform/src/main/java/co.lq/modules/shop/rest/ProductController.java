package co.lq.modules.shop.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.CatalogAttr;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.domain.StoreProductAttr;
import co.lq.modules.shop.domain.StoreProductAttrValue;
import co.lq.modules.shop.repository.CatalogAttrRepository;
import co.lq.modules.shop.repository.StoreProductAttrRepository;
import co.lq.modules.shop.repository.StoreProductAttrValueRepository;
import co.lq.modules.shop.service.StoreProductService;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.modules.shop.service.dto.StoreProductQueryCriteria;
import co.lq.modules.shop.service.param.ProductAttributeValueParam;
import co.lq.modules.shop.service.param.SkuStockParam;
import co.lq.utils.OrderUtil;
import co.lq.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-04
 */
@Api(tags = "店铺:商品管理")
@RestController
@RequestMapping("api")
public class ProductController {

    private final StoreProductService             storeProductService;
    private final CatalogAttrRepository           catalogAttrRepository;
    private final StoreProductAttrRepository      storeProductAttrRepository;
    private final StoreProductAttrValueRepository storeProductAttrValueRepository;

    public ProductController(StoreProductService storeProductService, CatalogAttrRepository catalogAttrRepository,
                             StoreProductAttrRepository storeProductAttrRepository,
                             StoreProductAttrValueRepository storeProductAttrValueRepository) {
        this.storeProductService = storeProductService;
        this.catalogAttrRepository = catalogAttrRepository;
        this.storeProductAttrRepository = storeProductAttrRepository;
        this.storeProductAttrValueRepository = storeProductAttrValueRepository;
    }

    @Log("查询商品")
    @ApiOperation(value = "查询商品")
    @GetMapping(value = "/product")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_SELECT')")
    public ResponseEntity getStoreProducts(StoreProductQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(storeProductService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增商品")
    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/product")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody StoreProduct resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        if (ObjectUtil.isEmpty(resources.getGiveIntegral())) {
            resources.setGiveIntegral(BigDecimal.ZERO);
        }
        if (ObjectUtil.isEmpty(resources.getCost())) {
            resources.setCost(BigDecimal.ZERO);
        }
        StoreProductDTO storeProduct = storeProductService.create(resources);
        //通过商品类目id获取商品的规格和属性
        final List<CatalogAttr> catalogAttrList = catalogAttrRepository
                .findByCatalogIdAndIsSpecOrderBySortAsc(resources.getCatalogId(), 1);
        final Integer skuSize = catalogAttrList.size();
        final Integer[] index = { 0 };
        List<SkuStockParam> skuList = resources.getSkuStockList();
        List<ProductAttributeValueParam> productAttributeValueList = resources.getProductAttributeValueList();
        List<StoreProductAttrValue> storeProductAttrValueList = new ArrayList<>();
        List<StoreProductAttr> storeProductAttrList = new ArrayList<>();
        skuList.forEach(sku -> {
            StoreProductAttrValue storeProductAttrValue = new StoreProductAttrValue();
            String attrValue = "";
            if (skuSize == 1) {
                attrValue = sku.getSp1();
            } else if (skuSize == 2) {
                attrValue = sku.getSp1() + "," + sku.getSp2();
            } else {
                attrValue = sku.getSp1() + "," + sku.getSp2() + "," + sku.getSp3();
            }
            storeProductAttrValue.setSuk(attrValue);
            storeProductAttrValue.setBarCode(sku.getBarCode());
            storeProductAttrValue.setCost(sku.getCost());
            storeProductAttrValue.setPrice(sku.getPrice());
            storeProductAttrValue.setImage(StringUtils.join(sku.getPic(), ","));
            storeProductAttrValue.setUnique(IdUtil.simpleUUID());
            storeProductAttrValue.setStock(sku.getStock());
            storeProductAttrValue.setSales(sku.getSales());
            storeProductAttrValue.setProductId(storeProduct.getId());
            storeProductAttrValueList.add(storeProductAttrValue);
            index[0]++;
        });
        productAttributeValueList.forEach(productAttr -> {
            StoreProductAttr storeProductAttr = new StoreProductAttr();
            storeProductAttr.setCatalogAttrId(productAttr.getProductAttributeId());
            storeProductAttr.setAttrName(productAttr.getName());
            storeProductAttr.setAttrValues(productAttr.getValue());
            storeProductAttr.setProductId(storeProduct.getId());
            storeProductAttr.setType(0);
            storeProductAttrList.add(storeProductAttr);
        });

        final List<CatalogAttr> catalogAttrList1 = catalogAttrRepository
                .findByCatalogIdAndIsSpecOrderBySortAsc(resources.getCatalogId(), 0);
        final Integer attributeSize = catalogAttrList.size();
        catalogAttrList1.forEach(catalogAttr -> {
            //留待之后添加
        });
        storeProductAttrRepository.saveAll(storeProductAttrList);
        storeProductAttrValueRepository.saveAll(storeProductAttrValueList);
        return new ResponseEntity<>(storeProduct, HttpStatus.CREATED);
    }

    @Log("修改商品")
    @ApiOperation(value = "修改商品")
    @PutMapping(value = "/product")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreProduct resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeProductService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品")
    @ApiOperation(value = "删除商品")
    @DeleteMapping(value = "/product/{id}")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "恢复数据")
    @DeleteMapping(value = "/product/recovery/{id}")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_DELETE')")
    public ResponseEntity recovery(@PathVariable Long id) {
        storeProductService.recovery(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "商品上架/下架")
    @PostMapping(value = "/product/onsale/{id}")
    public ResponseEntity onSale(@PathVariable Long id, @RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int status = Integer.valueOf(jsonObject.get("status").toString());
        //System.out.println(status);
        storeProductService.onSale(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "生成属性")
    @PostMapping(value = "/product/isFormatAttr/{id}")
    public ResponseEntity isFormatAttr(@PathVariable Long id, @RequestBody String jsonStr) {
        return new ResponseEntity(storeProductService.isFormatAttr(id, jsonStr), HttpStatus.OK);
    }

    @ApiOperation(value = "设置保存属性")
    @PostMapping(value = "/product/setAttr/{id}")
    public ResponseEntity setAttr(@PathVariable Long id, @RequestBody String jsonStr) {
        storeProductService.createProductAttr(id, jsonStr);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "清除属性")
    @PostMapping(value = "/product/clearAttr/{id}")
    public ResponseEntity clearAttr(@PathVariable Long id) {
        storeProductService.clearProductAttr(id, true);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "获取属性")
    public ResponseEntity attr(@PathVariable Long id) {
        String str = storeProductService.getStoreProductAttrResult(id);
        if (StrUtil.isEmpty(str)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        JSONObject jsonObject = JSON.parseObject(str);

        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

}
