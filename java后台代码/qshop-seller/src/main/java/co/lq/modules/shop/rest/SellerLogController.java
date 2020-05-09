package co.lq.modules.shop.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.service.LogService;
import co.lq.service.dto.LogQueryCriteria;
import io.swagger.annotations.Api;

/**
 * @author billy
 * @date 2018-11-24
 */
@RestController
@RequestMapping("/api/viewLogs")
@Api(tags = "监控：日志管理")
public class SellerLogController {

    private final LogService logService;

    public SellerLogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(value = "/mlogs")
    @PreAuthorize("@el.check()")
    public ResponseEntity getApiLogs(LogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType("INFO");
        criteria.setType(1);
        return new ResponseEntity<>(logService.findAllByPageable(criteria.getBlurry(), pageable), HttpStatus.OK);
    }
}
