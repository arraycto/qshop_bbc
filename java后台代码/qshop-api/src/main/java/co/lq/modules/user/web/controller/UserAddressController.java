package co.lq.modules.user.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.util.StrUtil;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.common.web.param.IdParam;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.UserAddress;
import co.lq.modules.user.service.UserAddressService;
import co.lq.modules.user.web.param.AddressParam;
import co.lq.modules.user.web.param.UserAddressQueryParam;
import co.lq.modules.user.web.vo.UserAddressQueryVo;
import co.lq.utils.OrderUtil;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户地前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-10-28
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户地址", tags = "用户:用户地址", description = "用户地址")
public class UserAddressController extends BaseController {

    private final UserAddressService userAddressService;

    /**
     * 添加或修改地址
     */
    @Log(value = "编辑地址", type = 1)
    @PostMapping("/address/edit")
    @ApiOperation(value = "添加或修改地址", notes = "添加或修改地址", response = ApiResult.class)
    public ApiResult<Map<String, Object>> addUserAddress(@Valid @RequestBody AddressParam param) {
        long uid = SecurityUtils.getUserId();

        UserAddress userAddress = new UserAddress();
        userAddress.setCity(param.getAddress().getCity());
        userAddress.setDistrict(param.getAddress().getDistrict());
        userAddress.setProvince(param.getAddress().getProvince());
        if (param.getIs_default().equals("true")) {
            userAddress.setIsDefault(1);
        } else {
            userAddress.setIsDefault(0);
        }
        userAddress.setDetail(param.getDetail());
        userAddress.setUid(uid);
        userAddress.setPhone(param.getPhone());
        userAddress.setPostCode(param.getPost_code());
        userAddress.setRealName(param.getReal_name());
        if (StrUtil.isEmpty(param.getId())) {
            userAddress.setAddTime(OrderUtil.getSecondTimestampTwo());
            userAddressService.save(userAddress);
        } else {
            userAddress.setId(Long.valueOf(param.getId()));
            userAddressService.updateById(userAddress);
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", userAddress.getId());
        return ApiResult.ok(map);
    }

    /**
     * 设置默认地址
     */
    @PostMapping("/address/default/set")
    @ApiOperation(value = "设置默认地址", notes = "设置默认地址", response = ApiResult.class)
    public ApiResult<Boolean> setDefault(@Valid @RequestBody IdParam idParam) {
        int uid = SecurityUtils.getUserId().intValue();
        UserAddress address = new UserAddress();
        address.setIsDefault(0);
        userAddressService.update(address, new QueryWrapper<UserAddress>().lambda().eq(UserAddress::getUid, uid));
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(1);
        userAddress.setId(Long.valueOf(idParam.getId()));
        boolean flag = userAddressService.updateById(userAddress);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户地址
     */
    @Log(value = "删除地址", type = 1)
    @PostMapping("/address/del")
    @ApiOperation(value = "删除用户地址", notes = "删除用户地址", response = ApiResult.class)
    public ApiResult<Boolean> deleteUserAddress(@Valid @RequestBody IdParam idParam) {
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDel(1);
        userAddress.setId(Long.valueOf(idParam.getId()));
        boolean flag = userAddressService.updateById(userAddress);
        return ApiResult.result(flag);
    }

    /**
     * 用户地址列表
     */
    @Log(value = "查看地址", type = 1)
    @GetMapping("/address/list")
    @ApiOperation(value = "用户地址列表", notes = "用户地址列表", response = UserAddressQueryVo.class)
    public ApiResult<Paging<UserAddressQueryVo>> getUserAddressPageList(UserAddressQueryParam queryParam) {
        int uid = SecurityUtils.getUserId().intValue();
        queryParam.setUid(uid);
        queryParam.setIsDel(0);
        Paging<UserAddressQueryVo> paging = userAddressService.getUserAddressPageList(queryParam);
        return ApiResult.ok(paging.getRecords());
    }

    /**
     * 地址详情
     */
    @GetMapping("/address/detail/{id}")
    @ApiOperation(value = "地址详情", notes = "地址详情", response = ApiResult.class)
    public ApiResult<UserAddressQueryVo> addressDetail(@PathVariable Integer id) {
        return ApiResult.ok(userAddressService.getUserAddressById(id));
    }

}
