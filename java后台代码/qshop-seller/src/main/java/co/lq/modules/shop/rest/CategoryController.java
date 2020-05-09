package co.lq.modules.shop.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import cn.hutool.core.util.StrUtil;
import co.lq.aop.log.Log;
import co.lq.exception.BadRequestException;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.StoreCategory;
import co.lq.modules.shop.service.StoreCategoryService;
import co.lq.modules.shop.service.dto.StoreCategoryDTO;
import co.lq.modules.shop.service.dto.StoreCategoryQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-03
 */
@Api(tags = "店铺:商品分类管理")
@RestController
@RequestMapping("api")
public class CategoryController {

    private final StoreCategoryService storeCategoryService;
    private final UserDetailsService   userDetailsService;

    public CategoryController(StoreCategoryService storeCategoryService, UserDetailsService userDetailsService) {
        this.storeCategoryService = storeCategoryService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/storeCategory/download")
    @PreAuthorize("@el.check('admin','cate:list')")
    public void download(HttpServletResponse response, StoreCategoryQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeCategoryService.download(storeCategoryService.queryAll(criteria), response);
    }

    @Log("查询商品分类")
    @ApiOperation(value = "查询商品分类")
    @GetMapping(value = "/storeCategory")
    @PreAuthorize("@el.check('admin','STORECATEGORY_ALL','STORECATEGORY_SELECT')")
    public ResponseEntity getStoreCategorys(StoreCategoryQueryCriteria criteria) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        List<StoreCategoryDTO> categoryDTOList = storeCategoryService.queryAll(criteria);
        return new ResponseEntity<>(storeCategoryService.buildTree(categoryDTOList), HttpStatus.OK);
    }

    @Log("新增商品分类")
    @ApiOperation(value = "新增商品分类")
    @PostMapping(value = "/storeCategory")
    @PreAuthorize("@el.check('admin','STORECATEGORY_ALL','STORECATEGORY_CREATE')")
    public ResponseEntity create(@Validated @RequestBody StoreCategory resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        if (resources.getPid() > 0 && StrUtil.isBlank(resources.getPic())) {
            throw new BadRequestException("子分类图片必传");
        }
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        StoreCategoryDTO storeCategoryDTO = storeCategoryService.create(resources);
        return new ResponseEntity<>(storeCategoryDTO, HttpStatus.CREATED);
    }

    @Log("修改商品分类")
    @ApiOperation(value = "修改商品分类")
    @PutMapping(value = "/storeCategory")
    @PreAuthorize("@el.check('admin','STORECATEGORY_ALL','STORECATEGORY_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreCategory resources) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        if (resources.getPid() > 0 && StrUtil.isBlank(resources.getPic())) {
            throw new BadRequestException("子分类图片必传");
        }
        StoreCategoryQueryCriteria criteria = new StoreCategoryQueryCriteria();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        criteria.setId(resources.getId());
        List<StoreCategoryDTO> categoryDTOList = storeCategoryService.queryAll(criteria);
        if (categoryDTOList.size() <= 0) {
            throw new BadRequestException("您无权修改此分类");
        }
        storeCategoryService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品分类")
    @ApiOperation(value = "删除商品分类")
    @DeleteMapping(value = "/storeCategory/{id}")
    @PreAuthorize("@el.check('admin','STORECATEGORY_ALL','STORECATEGORY_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        StoreCategoryDTO categoryDTO = storeCategoryService.findById(id);
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        if (jwtUser.getStoreId() == null || !jwtUser.getStoreId().equals(categoryDTO.getStoreId())) {
            throw new BadRequestException("您无权修改此分类");
        }
        storeCategoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
