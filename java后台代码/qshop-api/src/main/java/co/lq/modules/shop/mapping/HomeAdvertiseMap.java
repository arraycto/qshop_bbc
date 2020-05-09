package co.lq.modules.shop.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.entity.HomeAdvertise;
import co.lq.modules.shop.web.vo.HomeAdvertiseQueryVo;

/**
 * 广告
 *
 * @author songbin
 * @since 2020年3月13日 下午10:36:50
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HomeAdvertiseMap extends EntityMapper<HomeAdvertiseQueryVo, HomeAdvertise> {

}
