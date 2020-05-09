package co.lq.modules.user.web.controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.enums.AppFromEnum;
import co.lq.modules.shop.service.ApiSystemConfigService;
import co.lq.modules.user.entity.SystemAttachment;
import co.lq.modules.user.service.SystemAttachmentService;
import co.lq.modules.user.service.UserBillService;
import co.lq.modules.user.service.UserExtractService;
import co.lq.modules.user.service.UserService;
import co.lq.modules.user.web.param.PromParam;
import co.lq.modules.user.web.param.UserBillQueryParam;
import co.lq.modules.user.web.vo.UserQueryVo;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UserBillController
 * @Author billy
 * @Date 2019/11/10
 **/
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "用户分销", tags = "用户:用户分销", description = "用户分销")
public class UserBillController extends BaseController {

    private final UserBillService         userBillService;
    private final UserExtractService      extractService;
    private final ApiSystemConfigService  systemConfigService;
    private final UserService             userService;
    private final SystemAttachmentService systemAttachmentService;

    @Value("${file.path}")
    private String                        path;

    /**
     * 推广数据 昨天的佣金 累计提现金额 当前佣金
     */
    @GetMapping("/commission")
    @ApiOperation(value = "推广数据", notes = "推广数据")
    public ApiResult<Object> commission() {
        long uid = SecurityUtils.getUserId();

        //判断分销类型
        String statu = systemConfigService.getData("store_brokerage_statu");
        if (StrUtil.isNotEmpty(statu)) {
            if (Integer.valueOf(statu) == 1) {
                UserQueryVo userQueryVo = userService.getUserById(uid);
                if (userQueryVo.getIsPromoter() == 0) {
                    return ApiResult.fail("你不是推广员哦!");
                }
            }
        }

        //昨天的佣金
        double lastDayCount = userBillService.yesterdayCommissionSum(uid);
        //累计提现金额
        double extractCount = extractService.extractSum(uid);
        //获取总佣金
        double commissionCount = userBillService.getBrokerage(uid);

        //计算当前拥有的佣金
        if (commissionCount > 0) {

        }

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("lastDayCount", lastDayCount);
        map.put("extractCount", extractCount);
        map.put("commissionCount", commissionCount);

        return ApiResult.ok(map);
    }

    /**
     * 积分记录
     */
    @Log(value = "查看积分流水", type = 1)
    @GetMapping("/integral/list")
    @ApiOperation(value = "积分记录", notes = "积分记录")
    public ApiResult<Object> userInfo(UserBillQueryParam queryParam) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(userBillService.userBillList(uid, queryParam.getPage().intValue(),
                queryParam.getLimit().intValue(), "integral"));
    }

    /**
     * 分销二维码海报生成
     */
    @GetMapping("/spread/banner")
    @ApiOperation(value = "分销二维码海报生成", notes = "分销二维码海报生成")
    public ApiResult<Object> spreadBanner() {
        long uid = SecurityUtils.getUserId();
        UserQueryVo userInfo = userService.getUserById(uid);
        String siteUrl = systemConfigService.getData("site_url");
        if (StrUtil.isEmpty(siteUrl)) {
            return ApiResult.fail("未配置h5地址");
        }
        String apiUrl = systemConfigService.getData("api_url");
        if (StrUtil.isEmpty(apiUrl)) {
            return ApiResult.fail("未配置api地址");
        }

        String userType = userInfo.getUserType();
        if (!userType.equals(AppFromEnum.ROUNTINE.getValue())) {
            userType = AppFromEnum.H5.getValue();
        }

        String name = uid + "_" + userType + "_user_wap.jpg";

        SystemAttachment attachment = systemAttachmentService.getInfo(name);
        String fileDir = path + "qrcode" + File.separator;
        String qrcodeUrl = "";
        if (ObjectUtil.isNull(attachment)) {
            //生成二维码
            //判断用户是否小程序,注意小程序二维码生成路径要与H5不一样 不然会导致都跳转到小程序问题
            if (userType.equals(AppFromEnum.ROUNTINE.getValue())) {
                siteUrl = siteUrl + "/distribution/";
            }
            File file = FileUtil.mkdir(new File(fileDir));
            QrCodeUtil.generate(siteUrl + "?spread=" + uid, 180, 180, FileUtil.file(fileDir + name));

            systemAttachmentService.attachmentAdd(name, String.valueOf(FileUtil.size(file)), fileDir + name,
                    "qrcode/" + name);

            qrcodeUrl = fileDir + name;
        } else {
            qrcodeUrl = attachment.getAttDir();
        }

        String spreadPicName = uid + "_" + userType + "_user_spread.jpg";
        String spreadPicPath = fileDir + spreadPicName;

        SystemAttachment attachmentT = systemAttachmentService.getInfo(spreadPicName);
        String spreadUrl = "";
        InputStream stream = getClass().getClassLoader().getResourceAsStream("fx.jpg");
        InputStream streamT = getClass().getClassLoader().getResourceAsStream("simsunb.ttf");
        File newFile = new File("fx.jpg");
        File newFileT = new File("simsunb.ttf");
        try {
            FileUtils.copyInputStreamToFile(stream, newFile);
            FileUtils.copyInputStreamToFile(streamT, newFileT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ObjectUtil.isNull(attachmentT)) {
            try {

                // Font font = new Font(newFileT.getAbsolutePath(), Font.BOLD, 20);
                Font font = Font.createFont(Font.TRUETYPE_FONT, newFileT);
                Font f = font.deriveFont(Font.PLAIN, 20);
                //font.
                ImgUtil.pressText(//
                        newFile, FileUtil.file(spreadPicPath), userInfo.getNickname() + "邀您加入", Color.BLACK, f, //字体
                        50, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        300, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                        0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
                );

                ImgUtil.pressImage(FileUtil.file(spreadPicPath), FileUtil.file(spreadPicPath),
                        ImgUtil.read(FileUtil.file(qrcodeUrl)), //水印图片
                        -150, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                        340, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
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

        java.util.List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", 1);
        map.put("pic", "");
        map.put("title", "分享海报");
        map.put("wap_poster", spreadUrl);
        list.add(map);
        return ApiResult.ok(list);
    }

    /**
     * 推荐用户 grade == 0 获取一级推荐人 grade == 其他 获取二级推荐人 keyword 会员名称查询 sort
     * childCount ASC/DESC 团队排序 numberCount ASC/DESC 金额排序 orderCount ASC/DESC
     * 订单排序
     */
    @Log(value = "查看分销人", type = 1)
    @PostMapping("/spread/people")
    @ApiOperation(value = "推荐用户", notes = "推荐用户")
    public ApiResult<Object> spreadPeople(@Valid @RequestBody PromParam param) {
        int uid = SecurityUtils.getUserId().intValue();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("list", userService.getUserSpreadGrade(param, uid));
        map.put("total", userService.getSpreadCount(uid, 1));
        map.put("totalLevel", userService.getSpreadCount(uid, 2));

        return ApiResult.ok(map);
    }

    /**
     * 推广佣金明细 type 0 全部 1 消费 2 充值 3 返佣 4 提现
     *
     * @return mixed
     */
    @Log(value = "查看佣金", type = 1)
    @GetMapping("/spread/commission/{type}")
    @ApiOperation(value = "推广佣金明细", notes = "推广佣金明细")
    public ApiResult<Object> spreadCommission(UserBillQueryParam queryParam, @PathVariable int type) {
        int uid = SecurityUtils.getUserId().intValue();
        return ApiResult.ok(userBillService.getUserBillList(queryParam.getPage().intValue(),
                queryParam.getLimit().intValue(), uid, type));
    }

    /**
     * 推广订单
     */
    @PostMapping("/spread/order")
    @ApiOperation(value = "推广订单", notes = "推广订单")
    public ApiResult<Object> spreadOrder(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (ObjectUtil.isNull(jsonObject.get("page")) || ObjectUtil.isNull(jsonObject.get("limit"))) {
            return ApiResult.fail("参数错误");
        }
        int uid = SecurityUtils.getUserId().intValue();
        Map<String, Object> map = userBillService.spreadOrder(uid, Integer.valueOf(jsonObject.get("page").toString()),
                Integer.valueOf(jsonObject.get("limit").toString()));
        return ApiResult.ok(map);
    }

}
