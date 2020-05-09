package co.lq.modules.shop.rest;

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

import cn.hutool.core.util.ObjectUtil;
import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.User;
import co.lq.modules.shop.service.SystemConfigService;
import co.lq.modules.shop.service.UserService;
import co.lq.modules.shop.service.dto.UserMoneyDTO;
import co.lq.modules.shop.service.dto.UserQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-06
 */
@Api(tags = "商城:会员管理")
@RestController
@RequestMapping("api")
public class MemberController {

    private final UserService         userService;
    private final SystemConfigService systemConfigService;

    public MemberController(UserService userService, SystemConfigService systemConfigService) {
        this.userService = userService;
        this.systemConfigService = systemConfigService;
    }

    @Log("查询用户")
    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/user")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserQueryCriteria criteria, Pageable pageable) {
        if (ObjectUtil.isNotNull(criteria.getIsPromoter())) {
            if (criteria.getIsPromoter() == 1) {
                String key = systemConfigService.findByKey("store_brokerage_statu").getValue();
                if (Integer.valueOf(key) == 2) {
                    return new ResponseEntity(null, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增用户")
    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/user")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody User resources) {
        return new ResponseEntity<>(userService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改用户")
    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/user")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody User resources) {
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "/user/{uid}")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long uid) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        userService.delete(uid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "用户禁用启用")
    @PostMapping(value = "/user/onStatus/{id}")
    public ResponseEntity onStatus(@PathVariable Long id, @RequestBody String jsonStr) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int status = Integer.valueOf(jsonObject.get("status").toString());
        userService.onStatus(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "修改余额")
    @PostMapping(value = "/user/money")
    @PreAuthorize("@el.check('admin','USER_ALL','USER_EDIT')")
    public ResponseEntity updatePrice(@Validated @RequestBody UserMoneyDTO param) {
        userService.updateMoney(param);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
