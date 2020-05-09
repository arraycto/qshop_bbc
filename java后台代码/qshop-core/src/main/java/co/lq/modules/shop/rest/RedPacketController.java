package co.lq.modules.shop.rest;

import co.lq.aop.log.Log;
import co.lq.modules.shop.domain.RedPacket;
import co.lq.modules.shop.service.RedPacketService;
import co.lq.modules.shop.service.dto.RedPacketQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author billy
* @date 2020-03-27
*/
@Api(tags = "红包管理")
@RestController
@RequestMapping("/api/redPacket")
public class RedPacketController {

    private final RedPacketService redPacketService;

    public RedPacketController(RedPacketService redPacketService) {
        this.redPacketService = redPacketService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('redPacket:list')")
    public void download(HttpServletResponse response, RedPacketQueryCriteria criteria) throws IOException {
        redPacketService.download(redPacketService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询红包")
    @ApiOperation("查询红包")
    @PreAuthorize("@el.check('redPacket:list')")
    public ResponseEntity<Object> getRedPackets(RedPacketQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(redPacketService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增红包")
    @ApiOperation("新增红包")
    @PreAuthorize("@el.check('redPacket:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody RedPacket resources){
        return new ResponseEntity<>(redPacketService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改红包")
    @ApiOperation("修改红包")
    @PreAuthorize("@el.check('redPacket:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody RedPacket resources){
        redPacketService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除红包")
    @ApiOperation("删除红包")
    @PreAuthorize("@el.check('redPacket:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        redPacketService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}