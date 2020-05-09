package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.MemberSharedProduct;
import co.lq.modules.shop.service.MemberSharedProductService;
import co.lq.modules.shop.service.dto.MemberSharedProductQueryCriteria;
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
* @date 2020-03-11
*/
@Api(tags = "分销商品管理")
@RestController
@RequestMapping("/api/memberSharedProduct")
public class MemberSharedProductController {

    private final MemberSharedProductService memberSharedProductService;

    public MemberSharedProductController(MemberSharedProductService memberSharedProductService) {
        this.memberSharedProductService = memberSharedProductService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('memberSharedProduct:list')")
    public void download(HttpServletResponse response, MemberSharedProductQueryCriteria criteria) throws IOException {
        memberSharedProductService.download(memberSharedProductService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询分销商品")
    @ApiOperation("查询分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:list')")
    public ResponseEntity<Object> getMemberSharedProducts(MemberSharedProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(memberSharedProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增分销商品")
    @ApiOperation("新增分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MemberSharedProduct resources){
        return new ResponseEntity<>(memberSharedProductService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改分销商品")
    @ApiOperation("修改分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MemberSharedProduct resources){
        memberSharedProductService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除分销商品")
    @ApiOperation("删除分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        memberSharedProductService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}