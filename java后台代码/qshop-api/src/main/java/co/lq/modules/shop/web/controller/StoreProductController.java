package co.lq.modules.shop.web.controller;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.aop.log.Log;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.enums.AppFromEnum;
import co.lq.enums.ProductEnum;
import co.lq.modules.shop.service.ApiStoreProductRelationService;
import co.lq.modules.shop.service.ApiStoreProductReplyService;
import co.lq.modules.shop.service.ApiStoreProductService;
import co.lq.modules.shop.service.ApiStoreServie;
import co.lq.modules.shop.service.ApiSystemConfigService;
import co.lq.modules.shop.web.dto.ProductDTO;
import co.lq.modules.shop.web.param.StoreProductQueryParam;
import co.lq.modules.shop.web.param.StoreProductRelationQueryParam;
import co.lq.modules.shop.web.vo.StoreProductQueryVo;
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
 * 商品控制器
 * </p>
 *
 * @author billy
 * @since 2019-10-19
 */
@Slf4j
@RestController
@Api(value = "产品模块", tags = "商城:产品模块", description = "产品模块")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreProductController extends BaseController {

    private final ApiStoreProductService         storeProductService;
    private final ApiStoreProductRelationService productRelationService;
    private final ApiStoreProductReplyService    replyService;
    private final ApiSystemConfigService         systemConfigService;
    private final SystemAttachmentService        systemAttachmentService;
    private final UserService                    userService;
    private final ApiStoreServie                 apiStoreServie;

    @Value("${file.path}")
    private String                               path;

    /**
     * 获取首页更多产品
     */
    @AnonymousAccess
    @GetMapping("/groom/list/{type}")
    @ApiOperation(value = "获取首页更多产品", notes = "获取首页更多产品")
    public ApiResult<Map<String, Object>> moreGoodsList(@PathVariable Integer type) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (type.equals(ProductEnum.TYPE_1.getValue())) {//TODO 精品推荐
            map.put("list", storeProductService.getList(1, 20, 1));
        } else if (type.equals(ProductEnum.TYPE_2.getValue())) {//TODO  热门榜单
            map.put("list", storeProductService.getList(1, 20, 4));
        } else if (type.equals(ProductEnum.TYPE_3.getValue())) {//TODO 首发新品
            map.put("list", storeProductService.getList(1, 20, 2));
        } else if (type.equals(ProductEnum.TYPE_4.getValue())) {//TODO 促销单品
            map.put("list", storeProductService.getList(1, 20, 3));
        }

        return ApiResult.ok(map);
    }

    /**
     * 获取首页更多产品
     */
    @AnonymousAccess
    @GetMapping("/products")
    @ApiOperation(value = "商品列表", notes = "商品列表")
    public ApiResult<List<StoreProductQueryVo>> goodsList(StoreProductQueryParam productQueryParam) {
        return ApiResult.ok(storeProductService.getGoodsList(productQueryParam));
    }

    /**
     * 为你推荐
     */
    @AnonymousAccess
    @GetMapping("/product/hot")
    @ApiOperation(value = "为你推荐", notes = "为你推荐")
    public ApiResult productRecommend(StoreProductQueryParam queryParam) {
        return ApiResult.ok(storeProductService.getList(queryParam.getPage(), queryParam.getLimit(), 1));
    }

    /**
     * 为你推荐
     */
    @AnonymousAccess
    @GetMapping("/storeProduct/new")
    @ApiOperation(value = "为你推荐", notes = "为你推荐")
    public ApiResult storeProductRecommend(StoreProductQueryParam queryParam) {
        return ApiResult.ok(storeProductService.getList(queryParam, queryParam.getPage(), queryParam.getLimit(), 2));
    }

    /**
     * 普通商品详情
     */
    @AnonymousAccess
    @Log(value = "查看商品详情", type = 1)
    @GetMapping("/product/detail/{id}")
    @ApiOperation(value = "普通商品详情", notes = "普通商品详情")
    public ApiResult<ProductDTO> detail(@PathVariable Long id) {
        Long uid = 0L;
        try {
            uid = SecurityUtils.getUserId();
        } catch (Exception e) {
        }
        ProductDTO productDTO = storeProductService.goodsDetail(id, 0, uid);

        // 海报
        String siteUrl = systemConfigService.getData("site_url");
        if (StrUtil.isEmpty(siteUrl)) {
            return ApiResult.fail("未配置h5地址");
        }
        String apiUrl = systemConfigService.getData("api_url");
        if (StrUtil.isEmpty(apiUrl)) {
            return ApiResult.fail("未配置api地址");
        }

        String name;
        if (uid > 0) {
            UserQueryVo userInfo = userService.getUserById(uid);
            String userType = userInfo.getUserType();
            if (!userType.equals(AppFromEnum.ROUNTINE.getValue())) {
                userType = AppFromEnum.H5.getValue();
            }
            name = id + "_" + uid + "_" + userType + "_product_detail_wap.jpg";
        } else {
            name = id + "_product_detail_wap.jpg";
        }
        SystemAttachment attachment = systemAttachmentService.getInfo(name);
        String fileDir = path + "qrcode" + File.separator;
        //        String qrcodeUrl = "";
        String routineQrcodeUrl = "";
        siteUrl = siteUrl + "/pages/goods/GoodsCon/index";
        if (ObjectUtil.isNull(attachment)) {
            //生成二维码
            File file = FileUtil.mkdir(new File(fileDir));
            if (uid > 0) {
                QrCodeUtil.generate(siteUrl + "?id=" + id + "&spread=" + uid, 180, 180, FileUtil.file(fileDir + name));
            } else {
                QrCodeUtil.generate(siteUrl + "?id=" + id, 180, 180, FileUtil.file(fileDir + name));
            }

            systemAttachmentService.attachmentAdd(name, String.valueOf(FileUtil.size(file)), fileDir + name,
                    "qrcode/" + name);

            //            qrcodeUrl = fileDir + name;
            routineQrcodeUrl = apiUrl + "/api/file/qrcode/" + name;

        } else {
            //            qrcodeUrl = attachment.getAttDir();
            routineQrcodeUrl = apiUrl + "/api/file/" + attachment.getSattDir();
        }

        productDTO.getStoreInfo().setCodeBase(routineQrcodeUrl);
        //        if (userType.equals(AppFromEnum.ROUNTINE.getValue())) {
        //            productDTO.getStoreInfo().setCodeBase(routineQrcodeUrl);
        //        } else {
        //            try {
        //                String base64CodeImg = co.lq.utils.FileUtil.fileToBase64(new File(qrcodeUrl));
        //                productDTO.getStoreInfo().setCodeBase("data:image/jpeg;base64," + base64CodeImg);
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //            }
        //        }

        return ApiResult.ok(productDTO);
    }

    /**
     * 添加收藏
     */
    @Log(value = "添加收藏", type = 1)
    @PostMapping("/collect/add")
    @ApiOperation(value = "添加收藏", notes = "添加收藏")
    public ApiResult<Object> collectAdd(@Validated @RequestBody StoreProductRelationQueryParam param) {
        int uid = SecurityUtils.getUserId().intValue();
        productRelationService.addRroductRelation(param, uid, "collect");
        return ApiResult.ok("success");
    }

    /**
     * 取消收藏
     */
    @Log(value = "取消收藏", type = 1)
    @PostMapping("/collect/del")
    @ApiOperation(value = "取消收藏", notes = "取消收藏")
    public ApiResult<Object> collectDel(@Validated @RequestBody StoreProductRelationQueryParam param) {
        int uid = SecurityUtils.getUserId().intValue();
        productRelationService.delRroductRelation(param, uid, "collect");
        return ApiResult.ok("success");
    }

    /**
     * 收藏全部
     */
    @Log(value = "收藏全部", type = 1)
    @PostMapping("/collect/all")
    @ApiOperation(value = "取消收藏", notes = "取消收藏")
    public ApiResult collectAll(@Validated @RequestBody StoreProductRelationQueryParam param) {
        int uid = SecurityUtils.getUserId().intValue();
        if (param.getIds() != null && param.getIds().length > 0) {
            StoreProductRelationQueryParam param1;
            for (int i = 0; i < param.getIds().length; i++) {
                param1 = new StoreProductRelationQueryParam();
                param1.setId(param.getIds()[i]);
                param1.setCategory(param.getCategory());
                productRelationService.addRroductRelation(param1, uid, "collect");
            }
        }

        return ApiResult.ok("success");
    }

    /**
     * 获取产品评论
     */
    @GetMapping("/reply/list/{id}")
    @ApiOperation(value = "获取产品评论", notes = "获取产品评论")
    public ApiResult<Object> replyList(@PathVariable Integer id, StoreProductQueryParam queryParam) {
        return ApiResult.ok(replyService.getReplyList(id, queryParam.getType(), queryParam.getPage().intValue(),
                queryParam.getLimit().intValue()));
    }

    /**
     * 获取产品评论数据
     */
    @GetMapping("/reply/config/{id}")
    @ApiOperation(value = "获取产品评论数据", notes = "获取产品评论数据")
    public ApiResult<Object> replyCount(@PathVariable Integer id) {
        return ApiResult.ok(replyService.getReplyCount(id));
    }

}
