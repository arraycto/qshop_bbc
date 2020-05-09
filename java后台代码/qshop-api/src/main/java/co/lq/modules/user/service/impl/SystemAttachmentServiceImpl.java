package co.lq.modules.user.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.common.service.impl.BaseServiceImpl;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.SystemAttachment;
import co.lq.modules.user.mapper.SystemAttachmentMapper;
import co.lq.modules.user.service.SystemAttachmentService;
import co.lq.modules.user.web.param.SystemAttachmentQueryParam;
import co.lq.modules.user.web.vo.SystemAttachmentQueryVo;
import co.lq.utils.OrderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 附件管理表 服务实现类
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SystemAttachmentServiceImpl extends BaseServiceImpl<SystemAttachmentMapper, SystemAttachment>
        implements SystemAttachmentService {

    private final SystemAttachmentMapper systemAttachmentMapper;

    @Override
    public SystemAttachment getInfo(String name) {
        QueryWrapper<SystemAttachment> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return systemAttachmentMapper.selectOne(wrapper);
    }

    @Override
    public void attachmentAdd(String name, String attSize, String attDir, String sattDir) {
        SystemAttachment attachment = new SystemAttachment();
        attachment.setName(name);
        attachment.setAttSize(attSize);
        attachment.setAttDir(attDir);
        attachment.setAttType("image/jpeg");
        attachment.setSattDir(sattDir);
        attachment.setTime(OrderUtil.getSecondTimestampTwo());
        attachment.setImageType(1);
        attachment.setModuleType(2);
        attachment.setPid(1);
        systemAttachmentMapper.insert(attachment);
    }

    @Override
    public SystemAttachmentQueryVo getSystemAttachmentById(Serializable id) throws Exception {
        return systemAttachmentMapper.getSystemAttachmentById(id);
    }

    @Override
    public Paging<SystemAttachmentQueryVo> getSystemAttachmentPageList(SystemAttachmentQueryParam systemAttachmentQueryParam)
            throws Exception {
        Page page = setPageParam(systemAttachmentQueryParam, OrderItem.desc("create_time"));
        IPage<SystemAttachmentQueryVo> iPage = systemAttachmentMapper.getSystemAttachmentPageList(page,
                systemAttachmentQueryParam);
        return new Paging(iPage);
    }

}
