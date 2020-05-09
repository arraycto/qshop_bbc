package co.lq.mp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import co.lq.mapper.EntityMapper;
import co.lq.mp.domain.WechatTemplate;
import co.lq.mp.service.dto.WechatTemplateDTO;

/**
 * @author billy
 * @date 2019-12-10
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WechatTemplateMapper extends EntityMapper<WechatTemplateDTO, WechatTemplate> {

}
