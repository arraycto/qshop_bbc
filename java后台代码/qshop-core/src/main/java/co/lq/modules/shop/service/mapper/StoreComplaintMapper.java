package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import co.lq.base.BaseMapper;
import co.lq.modules.shop.domain.StoreComplaint;
import co.lq.modules.shop.service.dto.StoreComplaintDTO;

/**
 * @author billy
 * @date 2020-03-11
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreComplaintMapper extends BaseMapper<StoreComplaintDTO, StoreComplaint> {

    /**
     * 转换
     *
     * @param storeComplaint 原始数据
     * @return /
     */
    @Override
    @Mappings({ @Mapping(source = "storeComplaint.shop.name", target = "shopName"),
            @Mapping(source = "storeComplaint.user.nickname", target = "nickname") })
    StoreComplaintDTO toDto(StoreComplaint storeComplaint);
}
