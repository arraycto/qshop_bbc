package co.lq.modules.user.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.service.ApiSystemConfigService;
import co.lq.modules.user.service.UserExtractService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.param.UserExtParam;
import co.lq.modules.user.web.param.UserExtractQueryParam;
import co.lq.modules.user.web.vo.UserExtractQueryVo;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户提现 前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户提现", tags = "用户:用户提现", description = "用户提现")
public class UserExtractController extends BaseController {

    private final UserExtractService     userExtractService;
    private final UserService            userService;
    private final ApiSystemConfigService systemConfigService;

    /**
     * 提现参数
     */
    @GetMapping("/extract/bank")
    @ApiOperation(value = "提现参数", notes = "提现参数")
    public ApiResult<Object> bank() {
        long uid = SecurityUtils.getUserId();
        UserQueryVo userInfo = userService.getUserById(uid);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("commissionCount", userInfo.getBrokeragePrice());
        map.put("minPrice", systemConfigService.getData("user_extract_min_price"));
        return ApiResult.ok(map);
    }

    /**
     * 用户提现
     */
    @PostMapping("/extract/cash")
    @ApiOperation(value = "用户提现", notes = "用户提现")
    public ApiResult<Boolean> addUserExtract(@Valid @RequestBody UserExtParam param) throws Exception {
        int uid = SecurityUtils.getUserId().intValue();
        userExtractService.userExtract(uid, param);

        return ApiResult.ok("申请提现成功");
    }

    /**
     * 用户提现表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取UserExtract分页列表", notes = "用户提现表分页列表", response = UserExtractQueryVo.class)
    public ApiResult<Paging<UserExtractQueryVo>> getUserExtractPageList(@Valid @RequestBody(required = false) UserExtractQueryParam userExtractQueryParam)
            throws Exception {
        Paging<UserExtractQueryVo> paging = userExtractService.getUserExtractPageList(userExtractQueryParam);
        return ApiResult.ok(paging);
    }

}
