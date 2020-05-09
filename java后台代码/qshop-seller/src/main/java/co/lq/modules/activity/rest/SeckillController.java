package co.lq.modules.activity.rest;

import java.io.IOException;
import java.math.BigDecimal;

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

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.modules.activity.domain.StoreSeckill;
import co.lq.modules.activity.service.StoreSeckillService;
import co.lq.modules.activity.service.dto.StoreSeckillQueryCriteria;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.utils.OrderUtil;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-14
 */
@Api(tags = "商城:秒杀管理")
@RestController
@RequestMapping("api")
public class SeckillController {

    private final StoreSeckillService storeSeckillService;
    private final UserDetailsService  userDetailsService;

    public SeckillController(StoreSeckillService storeSeckillService, UserDetailsService userDetailsService) {
        this.storeSeckillService = storeSeckillService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('basicMarking:list')")
    public void download(HttpServletResponse response, StoreSeckillQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeSeckillService.download(storeSeckillService.queryAll(criteria), response);
    }

    @Log("列表")
    @ApiOperation(value = "列表")
    @GetMapping(value = "/storeSeckill")
    @PreAuthorize("@el.check('admin','STORESECKILL_ALL','STORESECKILL_SELECT')")
    public ResponseEntity getStoreSeckills(StoreSeckillQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeSeckillService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增秒杀商品")
    @ApiOperation("新增秒杀商品")
    @PostMapping(value = "/storeSeckill")
    @PreAuthorize("@el.check('STORESECKILL_CREATE')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreSeckill resources) {
        if (resources.getSales() == null) {
            resources.setSales(0);
        }
        if (resources.getIsPostage() == 1) {
            resources.setPostage(BigDecimal.ZERO);
        }
        if (ObjectUtil.isNotNull(resources.getStartTimeDate())) {
            resources.setStartTime(OrderUtil.dateToTimestamp(resources.getStartTimeDate()));
        }
        if (ObjectUtil.isNotNull(resources.getEndTimeDate())) {
            resources.setStopTime(OrderUtil.dateToTimestamp(resources.getEndTimeDate()));
        }
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        if (resources.getProductList() != null) {
            resources.setProductId(resources.getProductList().get(0).getId());
        }
        return new ResponseEntity<>(storeSeckillService.create(resources), HttpStatus.CREATED);
    }

    @Log("发布")
    @ApiOperation(value = "发布")
    @PutMapping(value = "/storeSeckill")
    @PreAuthorize("@el.check('admin','STORESECKILL_ALL','STORESECKILL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreSeckill resources) {
        if (resources.getSales() == null) {
            resources.setSales(0);
        }
        if (resources.getIsPostage() == 1) {
            resources.setPostage(BigDecimal.ZERO);
        }
        if (ObjectUtil.isNotNull(resources.getStartTimeDate())) {
            resources.setStartTime(OrderUtil.dateToTimestamp(resources.getStartTimeDate()));
        }
        if (ObjectUtil.isNotNull(resources.getEndTimeDate())) {
            resources.setStopTime(OrderUtil.dateToTimestamp(resources.getEndTimeDate()));
        }
        if (resources.getProductList() != null) {
            resources.setProductId(resources.getProductList().get(0).getId());
        }
        storeSeckillService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/storeSeckill/{id}")
    @PreAuthorize("@el.check('admin','STORESECKILL_ALL','STORESECKILL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        storeSeckillService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
