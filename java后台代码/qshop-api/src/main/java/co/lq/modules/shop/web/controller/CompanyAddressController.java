package co.lq.modules.shop.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.lq.common.api.ApiResult;
import co.lq.common.web.controller.BaseController;
import co.lq.common.web.param.IdParam;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.CompanyAddress;
import co.lq.modules.shop.service.CompanyAddressService;
import co.lq.modules.shop.web.param.CompanyAddressQueryParam;
import co.lq.modules.shop.web.vo.CompanyAddressQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 公司收发货地址表 前端控制器
 * </p>
 *
 * @author billy
 * @since 2020-05-05
 */
@Slf4j
@RestController
@RequestMapping("/companyAddress")
@Api("公司收发货地址表 API")
public class CompanyAddressController extends BaseController {

    @Autowired
    private CompanyAddressService companyAddressService;

    /**
     * 添加公司收发货地址表
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加CompanyAddress对象", notes = "添加公司收发货地址表", response = ApiResult.class)
    public ApiResult<Boolean> addCompanyAddress(@Valid @RequestBody CompanyAddress companyAddress) throws Exception {
        boolean flag = companyAddressService.save(companyAddress);
        return ApiResult.result(flag);
    }

    /**
     * 修改公司收发货地址表
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改CompanyAddress对象", notes = "修改公司收发货地址表", response = ApiResult.class)
    public ApiResult<Boolean> updateCompanyAddress(@Valid @RequestBody CompanyAddress companyAddress) throws Exception {
        boolean flag = companyAddressService.updateById(companyAddress);
        return ApiResult.result(flag);
    }

    /**
     * 删除公司收发货地址表
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除CompanyAddress对象", notes = "删除公司收发货地址表", response = ApiResult.class)
    public ApiResult<Boolean> deleteCompanyAddress(@Valid @RequestBody IdParam idParam) throws Exception {
        boolean flag = companyAddressService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
     * 获取公司收发货地址表
     */
    @PostMapping("/info")
    @ApiOperation(value = "获取CompanyAddress对象详情", notes = "查看公司收发货地址表", response = CompanyAddressQueryVo.class)
    public ApiResult<CompanyAddressQueryVo> getCompanyAddress(@Valid @RequestBody IdParam idParam) throws Exception {
        CompanyAddressQueryVo companyAddressQueryVo = companyAddressService.getCompanyAddressById(idParam.getId());
        return ApiResult.ok(companyAddressQueryVo);
    }

    /**
     * 公司收发货地址表分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取CompanyAddress分页列表", notes = "公司收发货地址表分页列表", response = CompanyAddressQueryVo.class)
    public ApiResult<Paging<CompanyAddressQueryVo>> getCompanyAddressPageList(@Valid @RequestBody(required = false) CompanyAddressQueryParam companyAddressQueryParam)
            throws Exception {
        Paging<CompanyAddressQueryVo> paging = companyAddressService
                .getCompanyAddressPageList(companyAddressQueryParam);
        return ApiResult.ok(paging);
    }

}
