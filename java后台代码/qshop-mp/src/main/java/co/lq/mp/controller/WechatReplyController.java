package co.lq.mp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.ObjectUtil;
import co.lq.mp.domain.WechatReply;
import co.lq.mp.service.WechatReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-10-10
 */
@Api(tags = "商城:微信回復管理")
@RestController
@RequestMapping("api")
public class WechatReplyController {

    private final WechatReplyService wechatReplyService;

    public WechatReplyController(WechatReplyService wechatReplyService) {
        this.wechatReplyService = wechatReplyService;
    }

    @ApiOperation(value = "查询")
    @GetMapping(value = "/wechatReply")
    @PreAuthorize("@el.check('admin','WECHATREPLY_ALL','WECHATREPLY_SELECT')")
    public ResponseEntity getWechatReplys() {
        return new ResponseEntity(wechatReplyService.isExist("subscribe"), HttpStatus.OK);
    }

    @ApiOperation(value = "新增自动回复")
    @PostMapping(value = "/wechatReply")
    @PreAuthorize("@el.check('admin','WECHATREPLY_ALL','WECHATREPLY_CREATE')")
    public ResponseEntity create(@RequestBody String jsonStr) {
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        WechatReply wechatReply = new WechatReply();
        WechatReply isExist = wechatReplyService.isExist(jsonObject.get("key").toString());
        wechatReply.setKey(jsonObject.get("key").toString());
        wechatReply.setStatus(Integer.valueOf(jsonObject.get("status").toString()));
        wechatReply.setData(jsonObject.get("data").toString());
        wechatReply.setType(jsonObject.get("type").toString());
        if (ObjectUtil.isNull(isExist)) {
            wechatReplyService.create(wechatReply);
        } else {
            wechatReply.setId(isExist.getId());
            wechatReplyService.update(wechatReply);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
