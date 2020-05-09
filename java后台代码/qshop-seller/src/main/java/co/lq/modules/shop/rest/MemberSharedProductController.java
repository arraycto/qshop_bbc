package co.lq.modules.shop.rest;

import java.io.IOException;
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
import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.MemberSharedProduct;
import co.lq.modules.shop.service.MemberSharedProductService;
import co.lq.modules.shop.service.dto.MemberSharedProductDTO;
import co.lq.modules.shop.service.dto.MemberSharedProductQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 店铺分销商品管理
 *
 * @author songbin
 * @since 2020年3月15日 下午7:56:47
 */
@Api(tags = "店铺分销商品管理")
@RestController
@RequestMapping("/api/storeMemberSharedProduct")
public class MemberSharedProductController {
    private final MemberSharedProductService memberSharedProductService;
    private final UserDetailsService         userDetailsService;

    public MemberSharedProductController(MemberSharedProductService memberSharedProductService,
                                         UserDetailsService userDetailsService) {
        this.memberSharedProductService = memberSharedProductService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('StoreMemberSharedProduct:list')")
    public void download(HttpServletResponse response, MemberSharedProductQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        memberSharedProductService.download(memberSharedProductService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询分销商品")
    @ApiOperation("查询分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:list')")
    public ResponseEntity<Object> getMemberSharedProducts(MemberSharedProductQueryCriteria criteria,
                                                          Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(memberSharedProductService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增分销商品")
    @ApiOperation("新增分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MemberSharedProduct resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(memberSharedProductService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改分销商品")
    @ApiOperation("修改分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MemberSharedProduct resources) {
        MemberSharedProductQueryCriteria criteria = new MemberSharedProductQueryCriteria();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        criteria.setMerId(resources.getMerId());
        List<MemberSharedProductDTO> categoryDTOList = memberSharedProductService.queryAll(criteria);
        if (categoryDTOList.size() <= 0) {
            throw new BadRequestException("您无权修改此分销商品");
        }
        memberSharedProductService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除分销商品")
    @ApiOperation("删除分销商品")
    @PreAuthorize("@el.check('memberSharedProduct:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        MemberSharedProductDTO categoryDTO = memberSharedProductService.findById(id);
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        if (jwtUser.getStoreId() == null || !jwtUser.getStoreId().equals(categoryDTO.getStoreId())) {
            throw new BadRequestException("您无权修改此分销商品");
        }
        memberSharedProductService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
