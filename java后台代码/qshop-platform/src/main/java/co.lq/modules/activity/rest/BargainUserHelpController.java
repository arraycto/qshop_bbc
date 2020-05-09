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
import co.lq.modules.activity.service.StoreBargainUserHelpService;
import co.lq.modules.activity.service.dto.StoreBargainUserHelpQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2019-12-22
 */
@Api(tags = "商城:帮助砍价记录")
@RestController
@RequestMapping("api")
public class BargainUserHelpController {

    private final StoreBargainUserHelpService storeBargainUserHelpService;

    public BargainUserHelpController(StoreBargainUserHelpService storeBargainUserHelpService,
                                     UserDetailsService userDetailsService) {
        this.storeBargainUserHelpService = storeBargainUserHelpService;
    }

    @Log("查询砍价")
    @ApiOperation(value = "查询砍价")
    @GetMapping(value = "/storeBargainUserHelp")
    @PreAuthorize("@el.check('admin','STOREBARGAINUSERHELP_ALL','STOREBARGAINUSERHELP_CREATE')")
    public ResponseEntity getStoreBargains(StoreBargainUserHelpQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(storeBargainUserHelpService.queryAll(criteria, pageable), HttpStatus.OK);
    }
}
