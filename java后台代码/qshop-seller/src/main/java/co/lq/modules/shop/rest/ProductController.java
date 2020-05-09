package co.lq.modules.shop.rest;

import java.math.BigDecimal;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.aop.log.Log;
import co.lq.constant.ShopConstants;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.service.StoreProductService;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.modules.shop.service.dto.StoreProductQueryCriteria;
import co.lq.utils.OrderUtil;
import co.lq.utils.SecurityUtils;
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

    private final StoreProductService storeProductService;
    private final UserDetailsService  userDetailsService;

    public ProductController(StoreProductService storeProductService, UserDetailsService userDetailsService) {
        this.storeProductService = storeProductService;
        this.userDetailsService = userDetailsService;
    }

    @Log("查询商品")
    @ApiOperation(value = "查询商品")
    @GetMapping(value = "/storeProduct")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_SELECT')")
    public ResponseEntity getStoreProducts(StoreProductQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeProductService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询商品")
    @ApiOperation(value = "查询商品")
    @GetMapping(value = "/storeProduct/{id}")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_SELECT')")
    public ResponseEntity getStoreProductInfo(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(storeProductService.findById(id), HttpStatus.OK);
    }

    @Log("新增商品")
    @ApiOperation(value = "新增商品")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @PostMapping(value = "/storeProduct")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody StoreProduct resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        resources.setAddTime(OrderUtil.getSecondTimestampTwo());
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        if (ObjectUtil.isEmpty(resources.getGiveIntegral())) {
            resources.setGiveIntegral(BigDecimal.ZERO);
        }
        if (ObjectUtil.isEmpty(resources.getCost())) {
            resources.setCost(BigDecimal.ZERO);
        }
        StoreProductDTO storeProduct = storeProductService.create(resources);
        storeProductService.saveProductAttributes(storeProduct.getId(), resources);
        storeProductService.saveProductRelationSubjects(storeProduct.getId(), resources);
        storeProductService.saveProductPrefrenceAreas(storeProduct.getId(), resources);
        return new ResponseEntity<>(storeProduct, HttpStatus.CREATED);
    }

    @Log("修改商品")
    @ApiOperation(value = "修改商品")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @PutMapping(value = "/storeProduct")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreProduct resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeProductService.update(resources);
        storeProductService.saveProductAttributes(resources.getId(), resources);
        storeProductService.saveProductRelationSubjects(resources.getId(), resources);
        storeProductService.saveProductPrefrenceAreas(resources.getId(), resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("修改商品")
    @ApiOperation(value = "修改商品")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @PostMapping(value = "/storeProduct/updateStatus")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_EDIT')")
    public ResponseEntity updateStatus(@Validated @RequestBody StoreProductQueryCriteria resources) {
        StoreProduct storeProduct = new StoreProduct();
        if (resources.getIsShow() != null) {
            storeProduct.setIsShow(resources.getIsShow());
        }
        if (resources.getIsBenefit() != null) {
            storeProduct.setIsBenefit(resources.getIsBenefit());
        }
        if (resources.getIsBest() != null) {
            storeProduct.setIsBest(resources.getIsBest());
        }
        if (resources.getIsGood() != null) {
            storeProduct.setIsGood(resources.getIsGood());
        }
        if (resources.getIsHot() != null) {
            storeProduct.setIsHot(resources.getIsHot());
        }
        if (resources.getIsNew() != null) {
            storeProduct.setIsNew(resources.getIsNew());
        }
        if (resources.getIsPostage() != null) {
            storeProduct.setIsPostage(resources.getIsPostage());
        }
        if (resources.getIsDel() != null) {
            storeProduct.setIsDel(resources.getIsDel());
        }
        if (resources.getId() != null) {
            storeProduct.setId(resources.getId());
            storeProductService.update(storeProduct);
        }
        if (resources.getIds() != null) {
            resources.getIds().stream().forEach(id -> {
                storeProduct.setId(id);
                storeProductService.update(storeProduct);
            });
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品")
    @ApiOperation(value = "删除商品")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @DeleteMapping(value = "/storeProduct/{id}")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeProductService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "恢复数据")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @DeleteMapping(value = "/storeProduct/recovery/{id}")
    @PreAuthorize("@el.check('admin','STOREPRODUCT_ALL','STOREPRODUCT_DELETE')")
    public ResponseEntity recovery(@PathVariable Long id) {
        storeProductService.recovery(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "商品上架/下架")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @PostMapping(value = "/storeProduct/onsale/{id}")
    public ResponseEntity onSale(@PathVariable Long id, @RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int status = Integer.valueOf(jsonObject.get("status").toString());
        //System.out.println(status);
        storeProductService.onSale(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "生成属性")
    @PostMapping(value = "/storeProduct/isFormatAttr/{id}")
    public ResponseEntity isFormatAttr(@PathVariable Long id, @RequestBody String jsonStr) {
        return new ResponseEntity<>(storeProductService.isFormatAttr(id, jsonStr), HttpStatus.OK);
    }

    @ApiOperation(value = "设置保存属性")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @PostMapping(value = "/storeProduct/setAttr/{id}")
    public ResponseEntity setAttr(@PathVariable Long id, @RequestBody String jsonStr) {
        storeProductService.createProductAttr(id, jsonStr);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "清除属性")
    @CacheEvict(cacheNames = ShopConstants.QSHOP_REDIS_INDEX_KEY, allEntries = true)
    @PostMapping(value = "/storeProduct/clearAttr/{id}")
    public ResponseEntity clearAttr(@PathVariable Long id) {
        storeProductService.clearProductAttr(id, true);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "获取属性")
    @GetMapping(value = "/storeProduct/attr/{id}")
    public ResponseEntity attr(@PathVariable Long id) {
        String str = storeProductService.getStoreProductAttrResult(id);
        if (StrUtil.isEmpty(str)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        JSONObject jsonObject = JSON.parseObject(str);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

}
