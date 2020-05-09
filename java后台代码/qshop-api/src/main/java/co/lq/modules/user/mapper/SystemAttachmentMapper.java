package co.lq.modules.user.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.user.entity.SystemAttachment;
import co.lq.modules.user.web.param.SystemAttachmentQueryParam;
import co.lq.modules.user.web.vo.SystemAttachmentQueryVo;

/**
 * <p>
 * 附件管理表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-11-11
 */
@Repository
public interface SystemAttachmentMapper extends BaseMapper<SystemAttachment> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SystemAttachmentQueryVo getSystemAttachmentById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param systemAttachmentQueryParam
     * @return
     */
    IPage<SystemAttachmentQueryVo> getSystemAttachmentPageList(@Param("page") Page page,
                                                               @Param("param") SystemAttachmentQueryParam systemAttachmentQueryParam);

}
