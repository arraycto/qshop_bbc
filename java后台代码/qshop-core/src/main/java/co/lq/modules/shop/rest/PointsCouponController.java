package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.PointsCoupon;
import co.lq.modules.shop.service.PointsCouponService;
import co.lq.modules.shop.service.dto.PointsCouponQueryCriteria;
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
@Api(tags = "积分券管理")
@RestController
@RequestMapping("/api/pointsCoupon")
public class PointsCouponController {

    private final PointsCouponService pointsCouponService;

    public PointsCouponController(PointsCouponService pointsCouponService) {
        this.pointsCouponService = pointsCouponService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pointsCoupon:list')")
    public void download(HttpServletResponse response, PointsCouponQueryCriteria criteria) throws IOException {
        pointsCouponService.download(pointsCouponService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询积分券")
    @ApiOperation("查询积分券")
    @PreAuthorize("@el.check('pointsCoupon:list')")
    public ResponseEntity<Object> getPointsCoupons(PointsCouponQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pointsCouponService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增积分券")
    @ApiOperation("新增积分券")
    @PreAuthorize("@el.check('pointsCoupon:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PointsCoupon resources){
        return new ResponseEntity<>(pointsCouponService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改积分券")
    @ApiOperation("修改积分券")
    @PreAuthorize("@el.check('pointsCoupon:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody PointsCoupon resources){
        pointsCouponService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除积分券")
    @ApiOperation("删除积分券")
    @PreAuthorize("@el.check('pointsCoupon:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        pointsCouponService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}