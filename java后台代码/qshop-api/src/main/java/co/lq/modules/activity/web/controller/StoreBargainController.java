package co.lq.modules.activity.web.controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.HttpUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.enums.AppFromEnum;
import co.lq.modules.activity.entity.StoreBargainUser;
import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.service.ApiStoreBargainService;
import co.lq.modules.activity.service.ApiStoreBargainUserHelpService;
import co.lq.modules.activity.service.ApiStoreBargainUserService;
import co.lq.modules.activity.web.param.StoreBargainQueryParam;
import co.lq.modules.activity.web.param.StoreBargainUserQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainQueryVo;
import co.lq.modules.activity.web.vo.StoreBargainUserQueryVo;
import co.lq.modules.shop.service.ApiSystemConfigService;
import co.lq.modules.user.entity.SystemAttachment;
import co.lq.modules.user.service.SystemAttachmentService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 砍价 前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Slf4j
@RestController
@RequestMapping
@Api(value = "砍价商品", tags = "营销:砍价商品", description = "砍价商品")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("unchecked")
public class StoreBargainController extends BaseController {

    private final ApiStoreBargainService         apiStoreBargainService;
    private final ApiStoreBargainUserService     apiStoreBargainUserService;
    private final ApiStoreBargainUserHelpService apiStoreBargainUserHelpService;
    private final UserService                    userService;
    private final SystemAttachmentService        systemAttachmentService;
    private final ApiSystemConfigService         systemConfigService;

    @Value("${file.path}")
    private String                               path;

    private static Lock                          lock = new ReentrantLock(false);

    /**
     * 砍价产品列表
     */
    @AnonymousAccess
    @GetMapping("/bargain/list")
    @ApiOperation(value = "砍价产品列表", notes = "砍价产品列表", response = StoreBargainQueryVo.class)
    public ApiResult<Object> getStoreBargainPageList(StoreBargainQueryParam queryParam) {
        return ApiResult.ok(apiStoreBargainService.getList(queryParam.getPage(), queryParam.getLimit()));
    }

    /**
     * 砍价详情
     */
    @Log(value = "查看砍价产品", type = 1)
    @GetMapping("/bargain/detail/{id}")
    @ApiOperation(value = "砍价详情", notes = "砍价详情", response = StoreBargainQueryVo.class)
    public ApiResult<StoreBargainQueryVo> getStoreBargain(@PathVariable Integer id) {
        if (ObjectUtil.isNull(id)) {
            return ApiResult.fail("参数错误");
        }
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(apiStoreBargainService.getDetail(id, uid));
    }

    /**
     * 砍价详情统计
     */
    @PostMapping("/bargain/help/count")
    @ApiOperation(value = "砍价详情统计", notes = "砍价详情统计")
    public ApiResult<Object> helpCount(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer bargainId = jsonObject.getInteger("bargainId");
        Integer bargainUserUid = jsonObject.getInteger("bargainUserUid");
        if (ObjectUtil.isNull(bargainId) || ObjectUtil.isNull(bargainUserUid)) {
            return ApiResult.fail("参数错误");
        }

        return ApiResult.ok(apiStoreBargainService.helpCount(bargainId, bargainUserUid));
    }

    /**
     * 砍价顶部统计
     */
    @PostMapping("/bargain/share")
    @ApiOperation(value = "砍价顶部统计", notes = "砍价顶部统计")
    public ApiResult<Object> topCount(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer bargainId = jsonObject.getInteger("bargainId");

        if (ObjectUtil.isNull(bargainId)) {
            bargainId = 0;
        }

        return ApiResult.ok(apiStoreBargainService.topCount(bargainId));
    }

    /**
     * 砍价开启
     */
    @PostMapping("/bargain/start")
    @ApiOperation(value = "砍价开启", notes = "砍价开启")
    public ApiResult<Object> start(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Long bargainId = jsonObject.getLong("bargainId");

        if (ObjectUtil.isNull(bargainId)) {
            return ApiResult.fail("参数错误");
        }
        Long uid = SecurityUtils.getUserId();
        try {
            lock.lock();
            apiStoreBargainUserService.setBargain(bargainId, uid);
        } finally {
            lock.unlock();
        }

        return ApiResult.ok("ok");
    }

    /**
     * 帮助好友砍价
     */
    @PostMapping("/bargain/help")
    @ApiOperation(value = "帮助好友砍价", notes = "帮助好友砍价")
    public ApiResult<Object> help(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer bargainId = jsonObject.getInteger("bargainId");
        Integer bargainUserUid = jsonObject.getInteger("bargainUserUid");
        if (ObjectUtil.isNull(bargainId) || ObjectUtil.isNull(bargainUserUid)) {
            return ApiResult.fail("参数错误");
        }
        Map<String, Object> map = new LinkedHashMap<>();
        int uid = SecurityUtils.getUserId().intValue();
        boolean isBargainUserHelp = apiStoreBargainUserService.isBargainUserHelp(bargainId, bargainUserUid, uid);
        if (!isBargainUserHelp) {
            map.put("status", "SUCCESSFUL");
            return ApiResult.ok(map);
        }
        try {
            lock.lock();
            apiStoreBargainService.doHelp(bargainId, bargainUserUid, uid);
        } finally {
            lock.unlock();
        }

        map.put("status", "SUCCESS");

        return ApiResult.ok(map);
    }

    /**
     * 砍掉金额
     */
    @PostMapping("/bargain/help/price")
    @ApiOperation(value = "砍掉金额", notes = "砍掉金额")
    public ApiResult<Object> price(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer bargainId = jsonObject.getInteger("bargainId");
        Integer bargainUserUid = jsonObject.getInteger("bargainUserUid");
        if (ObjectUtil.isNull(bargainId) || ObjectUtil.isNull(bargainUserUid)) {
            return ApiResult.fail("参数错误");
        }
        int uid = SecurityUtils.getUserId().intValue();
        StoreBargainUser storeBargainUser = apiStoreBargainUserService.getBargainUserInfo(bargainId, bargainUserUid);
        Map<String, Object> map = new LinkedHashMap<>();
        if (ObjectUtil.isNull(storeBargainUser)) {
            map.put("price", 0);
            return ApiResult.ok(map);
        }
        StoreBargainUserHelp storeBargainUserHelp = apiStoreBargainUserHelpService
                .getOne(new QueryWrapper<StoreBargainUserHelp>().eq("bargain_id", bargainId)
                        .eq("bargain_user_id", storeBargainUser.getId())
                        .eq("uid", uid)
                        .last("limit 1"));
        if (ObjectUtil.isNull(storeBargainUserHelp)) {
            map.put("price", 0);
        } else {
            map.put("price", storeBargainUserHelp.getPrice());
        }

        return ApiResult.ok(map);
    }

    /**
     * 砍掉金额
     */
    @PostMapping("/bargain/help/list")
    @ApiOperation(value = "帮助好友砍价", notes = "帮助好友砍价")
    public ApiResult<Object> helpList(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        Integer bargainId = jsonObject.getInteger("bargainId");
        Integer bargainUserUid = jsonObject.getInteger("bargainUserUid");
        if (ObjectUtil.isNull(bargainId) || ObjectUtil.isNull(bargainUserUid)) {
            return ApiResult.fail("参数错误");
        }

        Integer page = jsonObject.getInteger("page");
        Integer limit = jsonObject.getInteger("limit");
        if (ObjectUtil.isNull(page)) {
            page = 1;
        }
        if (ObjectUtil.isNull(limit)) {
            limit = 10;
        }

        return ApiResult.ok(apiStoreBargainUserHelpService.getList(bargainId, bargainUserUid, page, limit));
    }

    /**
     * 开启砍价用户信息
     */
    @PostMapping("/bargain/start/user")
    @ApiOperation(value = "开启砍价用户信息", notes = "开启砍价用户信息")
    public ApiResult<Object> startUser(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        //Integer bargainId = jsonObject.getInteger("bargainId");
        Long bargainUserUid = jsonObject.getLong("bargainUserUid");
        if (ObjectUtil.isNull(bargainUserUid)) {
            return ApiResult.fail("参数错误");
        }

        return ApiResult.ok(userService.getUserById(bargainUserUid));
    }

    /**
     * 拼团海报
     */
    @PostMapping("/bargain/poster")
    @ApiOperation(value = "拼团海报", notes = "拼团海报")
    public ApiResult<Object> poster(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer bargainId = jsonObject.getInteger("bargainId");
        if (ObjectUtil.isNull(bargainId)) {
            return ApiResult.fail("参数有误");
        }

        String siteUrl = systemConfigService.getData("site_url");
        if (StrUtil.isEmpty(siteUrl)) {
            return ApiResult.fail("未配置h5地址");
        }
        String apiUrl = systemConfigService.getData("api_url");
        if (StrUtil.isEmpty(apiUrl)) {
            return ApiResult.fail("未配置api地址");
        }
        long uid = SecurityUtils.getUserId();
        StoreBargainQueryVo storeBargainQueryVo = apiStoreBargainService.getStoreBargainById(bargainId);
        StoreBargainUser storeBargainUser = apiStoreBargainUserService.getBargainUserInfo(bargainId, uid);

        if (ObjectUtil.isNull(storeBargainQueryVo) || ObjectUtil.isNull(storeBargainUser)) {
            return ApiResult.fail("数据不存在");
        }

        //用户可以砍掉的金额 好友砍价之前获取可以砍价金额
        double coverPrice = NumberUtil.sub(storeBargainUser.getBargainPrice(), storeBargainUser.getBargainPriceMin())
                .doubleValue();
        //用户剩余要砍掉的价格
        double surplusPrice = NumberUtil.sub(coverPrice, storeBargainUser.getPrice()).doubleValue();

        UserQueryVo userInfo = userService.getUserById(uid);
        String userType = userInfo.getUserType();
        if (!userType.equals(AppFromEnum.ROUNTINE.getValue())) {
            userType = AppFromEnum.H5.getValue();
        }
        String name = bargainId + "_" + uid + "_" + userType + "_bargain_share_wap.jpg";
        SystemAttachment attachment = systemAttachmentService.getInfo(name);
        String fileDir = path + "qrcode" + File.separator;
        String qrcodeUrl;
        if (ObjectUtil.isNull(attachment)) {
            //生成二维码
            //判断用户是否小程序,注意小程序二维码生成路径要与H5不一样 不然会导致都跳转到小程序问题
            File file = FileUtil.mkdir(new File(fileDir));
            if (userType.equals(AppFromEnum.ROUNTINE.getValue())) {
                siteUrl = siteUrl + "/bargain/";
                QrCodeUtil.generate(siteUrl + "?bargainId=" + bargainId + "&uid=" + uid + "&spread=" + uid, 180, 180,
                        FileUtil.file(fileDir + name));
            } else {
                QrCodeUtil.generate(siteUrl + "/activity/dargain_detail/" + bargainId + "/" + uid + "?spread=" + uid,
                        180, 180, FileUtil.file(fileDir + name));
            }

            systemAttachmentService.attachmentAdd(name, String.valueOf(FileUtil.size(file)), fileDir + name,
                    "qrcode/" + name);

            qrcodeUrl = fileDir + name;
        } else {
            qrcodeUrl = attachment.getAttDir();
        }

        String spreadPicName = bargainId + "_" + uid + "_" + userType + "_bargain_user_spread.jpg";
        String spreadPicPath = fileDir + spreadPicName;

        SystemAttachment attachmentT = systemAttachmentService.getInfo(spreadPicName);
        String spreadUrl = "";
        InputStream stream = getClass().getClassLoader().getResourceAsStream("poster.jpg");
        InputStream streamT = getClass().getClassLoader().getResourceAsStream("simsunb.ttf");
        File newFile = new File("poster.jpg");
        File newFileT = new File("simsunb.ttf");
        try {
            FileUtils.copyInputStreamToFile(stream, newFile);
            FileUtils.copyInputStreamToFile(streamT, newFileT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ObjectUtil.isNull(attachmentT)) {
            try {

                //第一步标题
                Font font = Font.createFont(Font.TRUETYPE_FONT, newFileT);
                Font f = font.deriveFont(Font.PLAIN, 30);
                //font.
                ImgUtil.pressText(//
                        newFile, FileUtil.file(spreadPicPath), storeBargainQueryVo.getTitle(), Color.BLACK, f, //字体
                        0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -480, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                Font f2 = font.deriveFont(Font.PLAIN, 45);
                //第2步价格
                ImgUtil.pressText(//
                        FileUtil.file(spreadPicPath), FileUtil.file(spreadPicPath),
                        NumberUtil.sub(storeBargainQueryVo.getPrice(), storeBargainUser.getPrice()).toString(),
                        Color.RED, f2, //字体
                        -160, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -380, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                Font f3 = font.deriveFont(Font.PLAIN, 30);
                //第3步几人团
                ImgUtil.pressText(//
                        FileUtil.file(spreadPicPath), FileUtil.file(spreadPicPath), "已砍至", Color.WHITE, f3, //字体
                        90, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -385, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                //第4步介绍
                String pro = "还差还差" + surplusPrice + "即可砍价成功";
                ImgUtil.pressText(//
                        FileUtil.file(spreadPicPath), FileUtil.file(spreadPicPath), pro, Color.BLACK, f3, //字体
                        -50, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -300, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                //第5步商品图片
                //下载图片
                String picImage = fileDir + bargainId + "_bargain_image.jpg";
                HttpUtil.downloadFile(storeBargainQueryVo.getImage(), FileUtil.file(picImage));

                ImgUtil.scale(FileUtil.file(picImage), FileUtil.file(picImage), 0.5f//缩放比例
                );

                ImgUtil.pressImage(FileUtil.file(spreadPicPath), FileUtil.file(spreadPicPath),
                        ImgUtil.read(FileUtil.file(picImage)), //水印图片
                        0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        -80, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f);

                ImgUtil.pressImage(FileUtil.file(spreadPicPath), FileUtil.file(spreadPicPath),
                        ImgUtil.read(FileUtil.file(qrcodeUrl)), //水印图片
                        0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        390, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f);

                systemAttachmentService.attachmentAdd(spreadPicName,
                        String.valueOf(FileUtil.size(new File(spreadPicPath))), spreadPicPath,
                        "qrcode/" + spreadPicName);

                spreadUrl = apiUrl + "/api/file/qrcode/" + spreadPicName;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            spreadUrl = apiUrl + "/api/file/" + attachmentT.getSattDir();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("url", spreadUrl);
        return ApiResult.ok(map);
    }

    /**
     * 砍价列表(已参与)
     */
    @GetMapping("/bargain/user/list")
    @ApiOperation(value = "砍价列表(已参与)", notes = "砍价列表(已参与)")
    public ApiResult<Object> bargainUserList(StoreBargainUserQueryParam queryParam) {
        int uid = SecurityUtils.getUserId().intValue();
        List<StoreBargainUserQueryVo> storeBargainUserQueryVos = apiStoreBargainUserService.bargainUserList(uid,
                queryParam.getPage(), queryParam.getLimit());
        if (storeBargainUserQueryVos.isEmpty()) {
            return ApiResult.fail("暂无参与砍价");
        }
        return ApiResult.ok(storeBargainUserQueryVos);
    }

    /**
     * 砍价取消
     */
    @Log(value = "取消砍价产品", type = 1)
    @PostMapping("/bargain/user/cancel")
    @ApiOperation(value = "砍价取消", notes = "砍价取消")
    public ApiResult<Object> bargainCancel(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Integer bargainId = jsonObject.getInteger("bargainId");
        if (ObjectUtil.isNull(bargainId)) {
            return ApiResult.fail("参数有误");
        }
        int uid = SecurityUtils.getUserId().intValue();
        apiStoreBargainUserService.bargainCancel(bargainId, uid);
        return ApiResult.ok("ok");
    }

}
