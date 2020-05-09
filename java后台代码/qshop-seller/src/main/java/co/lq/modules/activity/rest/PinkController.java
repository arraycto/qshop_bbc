package co.lq.modules.activity.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.aop.log.Log;
import co.lq.modules.activity.service.StorePinkService;
import co.lq.modules.activity.service.dto.StorePinkQueryCriteria;
import co.lq.modules.security.security.vo.JwtUser;
import co.lq.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-11-18
 */
@Api(tags = "商城:拼团记录管理")
@RestController
@RequestMapping("api")
public class PinkController {

    private final StorePinkService   storePinkService;
    private final UserDetailsService userDetailsService;

    public PinkController(StorePinkService storePinkService, UserDetailsService userDetailsService) {
        this.storePinkService = storePinkService;
        this.userDetailsService = userDetailsService;
    }

    @Log("查询记录")
    @ApiOperation(value = "查询记录")
    @GetMapping(value = "/storePink")
    @PreAuthorize("@el.check('admin','STOREPINK_ALL','STOREPINK_SELECT')")
    public ResponseEntity getStorePinks(StorePinkQueryCriteria criteria, Pageable pageable) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        criteria.setStoreId(jwtUser.getStoreId());
        return new ResponseEntity<>(storePinkService.queryAll(criteria, pageable), HttpStatus.OK);
    }

}
