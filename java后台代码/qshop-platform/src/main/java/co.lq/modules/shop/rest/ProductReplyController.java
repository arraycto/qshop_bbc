package co.lq.modules.shop.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.StoreProductReply;
import co.lq.modules.shop.service.StoreProductReplyService;
import co.lq.modules.shop.service.dto.StoreProductReplyQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-11-03
 */
@Api(tags = "商城:评论管理")
@RestController
@RequestMapping("api")
public class ProductReplyController {

    private final StoreProductReplyService storeProductReplyService;

    public ProductReplyController(StoreProductReplyService storeProductReplyService) {
        this.storeProductReplyService = storeProductReplyService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/storeProductReply")
    @PreAuthorize("@el.check('admin','STOREPRODUCTREPLY_ALL','STOREPRODUCTREPLY_SELECT')")
    public ResponseEntity getStoreProductReplys(StoreProductReplyQueryCriteria criteria, Pageable pageable) {
        criteria.setIsDel(0);
        return new ResponseEntity<>(storeProductReplyService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("修改")
    @ApiOperation(value = "修改")
    @PutMapping(value = "/storeProductReply")
    @PreAuthorize("@el.check('admin','STOREPRODUCTREPLY_ALL','STOREPRODUCTREPLY_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StoreProductReply resources) {
        storeProductReplyService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/storeProductReply/{id}")
    @PreAuthorize("@el.check('admin','STOREPRODUCTREPLY_ALL','STOREPRODUCTREPLY_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        StoreProductReply reply = new StoreProductReply();
        reply.setIsDel(1);
        reply.setId(id);
        storeProductReplyService.update(reply);
        return new ResponseEntity(HttpStatus.OK);
    }
}
