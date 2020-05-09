package co.lq.modules.user.service;

import java.io.Serializable;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.user.entity.SystemAttachment;
import co.lq.modules.user.web.param.SystemAttachmentQueryParam;
import co.lq.modules.user.web.vo.SystemAttachmentQueryVo;

/**
 * <p>
 * 附件管理表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
public interface SystemAttachmentService extends BaseService<SystemAttachment> {

    SystemAttachment getInfo(String name);

    void attachmentAdd(String name, String attSize, String attDir, String sattDir);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SystemAttachmentQueryVo getSystemAttachmentById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param systemAttachmentQueryParam
     * @return
     */
    Paging<SystemAttachmentQueryVo> getSystemAttachmentPageList(SystemAttachmentQueryParam systemAttachmentQueryParam)
            throws Exception;

}
