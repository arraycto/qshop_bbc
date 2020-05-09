package co.lq.modules.shop.service.mapper;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.HomeNewProduct;
import co.lq.modules.shop.service.dto.HomeNewProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author billy
* @date 2020-03-27
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HomeNewProductMapper extends BaseMapper<HomeNewProductDTO, HomeNewProduct> {

}