package co.lq.modules.activity.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.lq.annotation.AnonymousAccess;
import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.modules.activity.service.ApiStoreSeckillService;
import co.lq.modules.activity.web.dto.SeckillConfigDTO;
import co.lq.modules.activity.web.dto.SeckillTimeDTO;
import co.lq.modules.activity.web.param.StoreSeckillQueryParam;
import co.lq.modules.activity.web.vo.StoreSeckillQueryVo;
import co.lq.modules.shop.entity.SystemGroupData;
import co.lq.modules.shop.service.ApiSystemGroupDataService;
import co.lq.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 商品秒杀产品 前端控制器
 * </p>
 *
 * @author billy
 * @since 2019-12-14
 */
@Slf4j
@RestController
@RequestMapping
@Api(value = "商品秒杀", tags = "营销:商品秒杀", description = "商品秒杀")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StoreSeckillController extends BaseController {

    private final ApiStoreSeckillService    apiStoreSeckillService;
    private final ApiSystemGroupDataService apiSystemGroupDataService;

    /**
     * 秒杀产品列表
     */
    @AnonymousAccess
    @GetMapping("/seckill/list/{time}")
    @ApiOperation(value = "秒杀产品列表", notes = "秒杀产品列表", response = StoreSeckillQueryVo.class)
    public ApiResult getStoreSeckillPageList(@PathVariable String time, StoreSeckillQueryParam queryParam) {
        if (StrUtil.isBlank(time)) {
            return ApiResult.fail("参数错误");
        }
        SystemGroupData systemGroupData = apiSystemGroupDataService.findData(Integer.valueOf(time));
        if (ObjectUtil.isNull(systemGroupData)) {
            return ApiResult.fail("参数错误");
        }
        //今天开始的时间戳
        int today = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(new Date()));
        JSONObject jsonObject = JSONObject.parseObject(systemGroupData.getValue());
        int startTime = today + (jsonObject.getInteger("time") * 3600);
        int endTime = today + ((jsonObject.getInteger("time") + jsonObject.getInteger("continued")) * 3600);

        return ApiResult
                .ok(apiStoreSeckillService.getList(queryParam.getPage(), queryParam.getLimit(), startTime, endTime));
    }

    /**
     * 根据id获取商品秒杀产品详情
     */
    @AnonymousAccess
    @GetMapping("/seckill/detail/{id}")
    @ApiOperation(value = "秒杀产品详情", notes = "秒杀产品详情", response = StoreSeckillQueryVo.class)
    public ApiResult getStoreSeckill(@PathVariable Integer id) throws Exception {
        return ApiResult.ok(apiStoreSeckillService.getDetail(id));
    }

    /**
     * 秒杀产品时间区间
     */
    @AnonymousAccess
    @GetMapping("/seckill/index")
    @ApiOperation(value = "秒杀产品时间区间", notes = "秒杀产品时间区间", response = StoreSeckillQueryVo.class)
    public ApiResult getStoreSeckillIndex() throws Exception {
        //获取秒杀配置
        AtomicInteger seckillTimeIndex = new AtomicInteger();
        SeckillConfigDTO seckillConfigDTO = new SeckillConfigDTO();
        List<SystemGroupData> systemGroupDataList = apiSystemGroupDataService
                .list(new QueryWrapper<SystemGroupData>().eq("group_name", "routine_seckill_time"));
        List<SeckillTimeDTO> list = new ArrayList<>();
        int today = OrderUtil.dateToTimestampT(DateUtil.beginOfDay(new Date()));
        systemGroupDataList.forEach(i -> {
            String jsonStr = i.getValue();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            int time = Integer.valueOf(jsonObject.get("time").toString());//时间 5
            int continued = Integer.valueOf(jsonObject.get("continued").toString());//活动持续事件  3
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            String nowTime = sdf.format(new Date());
            String index = nowTime.substring(0, 1);
            int currentHour = "0".equals(index) ? Integer.valueOf(nowTime.substring(1, 2)) : Integer.valueOf(nowTime);
            SeckillTimeDTO seckillseckillTimeDTO = new SeckillTimeDTO();
            seckillseckillTimeDTO.setId(i.getId());
            //活动结束时间
            int activityEndHour = time + continued;
            if (activityEndHour > 24) {
                seckillseckillTimeDTO.setState("即将开始");
                seckillseckillTimeDTO.setTime(
                        jsonObject.get("time").toString().length() > 1 ? jsonObject.get("time").toString() + ":00"
                                : "0" + jsonObject.get("time").toString() + ":00");
                seckillseckillTimeDTO.setStatus(2);
                seckillseckillTimeDTO.setStop(today + activityEndHour * 3600);
            } else {
                if (currentHour >= time && currentHour < activityEndHour) {
                    seckillseckillTimeDTO.setState("抢购中");
                    seckillseckillTimeDTO.setTime(
                            jsonObject.get("time").toString().length() > 1 ? jsonObject.get("time").toString() + ":00"
                                    : "0" + jsonObject.get("time").toString() + ":00");
                    seckillseckillTimeDTO.setStatus(1);
                    seckillseckillTimeDTO.setStop(today + activityEndHour * 3600);
                    seckillTimeIndex.set(systemGroupDataList.indexOf(i));

                } else if (currentHour < time) {
                    seckillseckillTimeDTO.setState("即将开始");
                    seckillseckillTimeDTO.setTime(
                            jsonObject.get("time").toString().length() > 1 ? jsonObject.get("time").toString() + ":00"
                                    : "0" + jsonObject.get("time").toString() + ":00");
                    seckillseckillTimeDTO.setStatus(2);
                    seckillseckillTimeDTO.setStop(OrderUtil.dateToTimestamp(new Date()) + activityEndHour * 3600);
                } else {
                    seckillseckillTimeDTO.setState("已结束");
                    seckillseckillTimeDTO.setTime(
                            jsonObject.get("time").toString().length() > 1 ? jsonObject.get("time").toString() + ":00"
                                    : "0" + jsonObject.get("time").toString() + ":00");
                    seckillseckillTimeDTO.setStatus(0);
                    seckillseckillTimeDTO.setStop(today + activityEndHour * 3600);
                }
            }
            list.add(seckillseckillTimeDTO);
        });
        seckillConfigDTO.setSeckillTimeIndex(seckillTimeIndex.get());
        seckillConfigDTO.setSeckillTime(list);
        return ApiResult.ok(seckillConfigDTO);
    }
}
