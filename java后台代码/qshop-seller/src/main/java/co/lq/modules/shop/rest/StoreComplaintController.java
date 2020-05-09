package co.lq.modules.shop.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.modules.shop.domain.StoreComplaint;
import co.lq.modules.shop.service.StoreComplaintService;
import co.lq.modules.shop.service.dto.StoreComplaintQueryCriteria;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2020-04-15
 */
@Api(tags = "会员投诉管理")
@RestController
@RequestMapping("/api/storeComplaint")
public class StoreComplaintController {

    private final StoreComplaintService storeComplaintService;
    private final UserDetailsService    userDetailsService;

    public StoreComplaintController(StoreComplaintService storeComplaintService,
                                    UserDetailsService userDetailsService) {
        this.storeComplaintService = storeComplaintService;
        this.userDetailsService = userDetailsService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storeComplaint:list')")
    public void download(HttpServletResponse response, StoreComplaintQueryCriteria criteria) throws IOException {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        storeComplaintService.download(storeComplaintService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询会员投诉")
    @ApiOperation("查询会员投诉")
    @PreAuthorize("@el.check('storeComplaint:list')")
    public ResponseEntity<Object> getStoreComplaints(StoreComplaintQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeComplaintService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员投诉")
    @ApiOperation("新增会员投诉")
    @PreAuthorize("@el.check('storeComplaint:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StoreComplaint resources) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        resources.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storeComplaintService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员投诉")
    @ApiOperation("修改会员投诉")
    @PreAuthorize("@el.check('storeComplaint:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StoreComplaint resources) {
        storeComplaintService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员投诉")
    @ApiOperation("删除会员投诉")
    @PreAuthorize("@el.check('storeComplaint:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        storeComplaintService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
