package co.lq.modules.shop.rest;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.SellerUserService;
import co.lq.modules.shop.service.SystemConfigService;
import co.lq.modules.shop.service.dto.UserMoneyDTO;
import co.lq.modules.shop.service.dto.UserQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-06
 */
@Api(tags = "店铺:会员管理")
@RestController
@RequestMapping("api")
public class MemberController {

    private final SellerUserService   sellerUserService;
    private final SystemConfigService systemConfigService;
    private final UserDetailsService  userDetailsService;

    public MemberController(SellerUserService sellerUserService, SystemConfigService systemConfigService,
                            UserDetailsService userDetailsService) {
        this.sellerUserService = sellerUserService;
        this.systemConfigService = systemConfigService;
        this.userDetailsService = userDetailsService;
    }

    @Log("查询用户")
    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/user")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserQueryCriteria criteria, @RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "sort") String sort) {
        if (ObjectUtil.isNotNull(criteria.getIsPromoter())) {
            if (criteria.getIsPromoter() == 1) {
                String key = systemConfigService.findByKey("store_brokerage_statu").getValue();
                if (Integer.valueOf(key) == 2) {
                    return new ResponseEntity(null, HttpStatus.OK);
                }
            }
        }
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        Page page1 = new Page();
        page1.setCurrent(page);
        page1.setSize(size);
        OrderItem orderItem = new OrderItem();
        String[] arrSort = sort.split(",");
        orderItem.setColumn(arrSort[0]);
        orderItem.setAsc("desc".equals(arrSort[1]) ? false : true);
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        page1.setOrders(orderItemList);
        return new ResponseEntity<>(sellerUserService.queryAll(criteria, page1), HttpStatus.OK);
    }

    @Log("新增用户")
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/user")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody User resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return new ResponseEntity<>(sellerUserService.create(resources, jwtUser.getStoreId()), HttpStatus.CREATED);
    }

    @Log("修改用户")
    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/user")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody User resources) {
        sellerUserService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/user/{uid}")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long uid) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        sellerUserService.delete(uid, jwtUser.getStoreId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("用户禁用启用")
    @ApiOperation(value = "用户禁用启用")
    @PostMapping(value = "/User/onStatus/{id}")
    public ResponseEntity onStatus(@PathVariable Long id, @RequestBody String jsonStr) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int status = Integer.valueOf(jsonObject.get("status").toString());
        sellerUserService.onStatus(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("修改余额")
    @ApiOperation(value = "修改余额")
    @PostMapping(value = "/user/money")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_EDIT')")
    public ResponseEntity updatePrice(@Validated @RequestBody UserMoneyDTO param) {
        sellerUserService.updateMoney(param);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
