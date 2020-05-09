package co.lq.modules.shop.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.GroupActivity;
import co.lq.modules.shop.domain.StoreProduct;
import co.lq.modules.shop.service.GroupActivityService;
import co.lq.modules.shop.service.StoreProductService;
import co.lq.modules.shop.service.dto.GroupActivityDTO;
import co.lq.modules.shop.service.dto.GroupActivityQueryCriteria;
import co.lq.modules.shop.service.dto.StoreProductDTO;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author billy
 * @date 2020-04-02
 */
@Api(tags = "团购活动管理")
@RestController
@RequestMapping("/api/smsGroupActivity")
public class GroupActivityController {

    private final GroupActivityService groupActivityService;
    private final UserDetailsService   userDetailsService;
    private final StoreProductService  storeProductService;

    public GroupActivityController(GroupActivityService groupActivityService, UserDetailsService userDetailsService,
                                   StoreProductService storeProductService) {
        this.groupActivityService = groupActivityService;
        this.userDetailsService = userDetailsService;
        this.storeProductService = storeProductService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('smsGroupActivity:list')")
    public void download(HttpServletResponse response, GroupActivityQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        groupActivityService.download(groupActivityService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询团购活动")
    @ApiOperation("查询团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:list')")
    public ResponseEntity<Object> getSmsGroupActivitys(GroupActivityQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(groupActivityService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增团购活动")
    @ApiOperation("新增团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody GroupActivity resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        List<StoreProduct> list = resources.getProductList();
        String goodsIs = "";
        BigDecimal originPrice = BigDecimal.ZERO;
        for (StoreProduct p : list) {
            originPrice = originPrice.add(p.getPrice());
            goodsIs = goodsIs + p.getId() + ",";
        }
        resources.setOriginprice(originPrice);
        resources.setGoodsIds(goodsIs.substring(0, goodsIs.length() - 1));
        return new ResponseEntity<>(groupActivityService.create(resources), HttpStatus.CREATED);
    }

    @Log("查询团购活动明细")
    @ApiOperation("查询明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@el.check('smsGroupActivity:detail')")
    public Object getSmsBasicMarkingById(@ApiParam("id") @PathVariable Long id) {
        GroupActivityDTO entity = groupActivityService.findById(id);
        List<StoreProductDTO> list = new ArrayList<>();
        String goodsIs = entity.getGoodsIds();
        String[] arrGoodsIds = goodsIs.split(",");
        StoreProductDTO storeProductDTO;
        for (String goodsId : arrGoodsIds) {
            storeProductDTO = storeProductService.findById(Long.parseLong(goodsId));
            list.add(storeProductDTO);
        }
        entity.setProductList(list);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PutMapping
    @Log("修改团购活动")
    @ApiOperation("修改团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody GroupActivity resources) {
        List<StoreProduct> list = resources.getProductList();
        String goodsIs = "";
        BigDecimal originPrice = BigDecimal.ZERO;
        for (StoreProduct p : list) {
            originPrice = originPrice.add(p.getPrice());
            goodsIs = goodsIs + p.getId() + ",";
        }
        resources.setOriginprice(originPrice);
        resources.setGoodsIds(goodsIs.substring(0, goodsIs.length() - 1));
        groupActivityService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除团购活动")
    @ApiOperation("删除团购活动")
    @PreAuthorize("@el.check('smsGroupActivity:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        groupActivityService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
