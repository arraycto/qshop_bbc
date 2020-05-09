package co.lq.mp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.mp.domain.WechatReply;
import co.lq.mp.service.dto.WechatReplyDTO;

/**
 * @author billy
 * @date 2019-10-10
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WechatReplyMapper extends EntityMapper<WechatReplyDTO, WechatReply> {

}
