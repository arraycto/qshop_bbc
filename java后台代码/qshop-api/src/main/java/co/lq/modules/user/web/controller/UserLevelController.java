package co.lq.modules.user.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.user.service.UserLevelRelationService;
import co.lq.modules.user.service.UserLevelService;
import co.lq.modules.user.service.UserTaskService;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户等级 前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-12-06
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户等级", tags = "用户:用户等级", description = "用户等级")
public class UserLevelController extends BaseController {

    private final UserLevelRelationService userLevelRelationService;
    private final UserLevelService         userLevelService;
    private final UserTaskService          userTaskService;

    /**
     * 会员等级列表
     */
    @Log(value = "进入会员中心", type = 1)
    @GetMapping("/user/level/grade")
    @ApiOperation(value = "会员等级列表", notes = "会员等级列表")
    public ApiResult<Object> getLevelInfo() {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(userLevelService.getLevelInfo(uid, true));
    }

    /**
     * 获取等级任务
     */
    @GetMapping("/user/level/task/{id}")
    @ApiOperation(value = "获取等级任务", notes = "获取等级任务")
    public ApiResult<Object> getTask(@PathVariable Integer id) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(userTaskService.getTaskList(id, uid, null));
    }

    /**
     * 检测用户是否可以成为会员
     */
    @GetMapping("/user/level/detection")
    @ApiOperation(value = "检测用户是否可以成为会员", notes = "检测用户是否可以成为会员")
    public ApiResult<Object> detection() {
        int uid = SecurityUtils.getUserId().intValue();
        userLevelRelationService.setLevelComplete(uid);
        return ApiResult.ok("ok");
    }

}
