package co.lq.modules.shop.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.modules.shop.domain.StoreProductReply;
import co.lq.modules.shop.service.dto.StoreProductReplyDTO;

/**
 * @author billy
 * @date 2019-11-03
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreProductReplyMapper extends EntityMapper<StoreProductReplyDTO, StoreProductReply> {

}
