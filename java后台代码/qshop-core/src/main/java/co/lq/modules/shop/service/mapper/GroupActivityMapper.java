package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.GroupActivity;
import co.lq.modules.shop.service.dto.GroupActivityDTO;

/**
 * @author billy
 * @date 2020-04-02
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupActivityMapper extends BaseMapper<GroupActivityDTO, GroupActivity> {

}
