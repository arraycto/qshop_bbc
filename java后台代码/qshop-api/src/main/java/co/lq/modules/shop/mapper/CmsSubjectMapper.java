package co.lq.modules.shop.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.CmsSubject;
import co.lq.modules.shop.web.param.CmsSubjectQueryParam;
import co.lq.modules.shop.web.vo.CmsSubjectQueryVo;

/**
 * <p>
 * 专题表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2020-04-19
 */
@Repository
public interface CmsSubjectMapper extends BaseMapper<CmsSubject> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CmsSubjectQueryVo getCmsSubjectById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param cmsSubjectQueryParam
     * @return
     */
    IPage<CmsSubjectQueryVo> getCmsSubjectPageList(@Param("page") Page page,
                                                   @Param("param") CmsSubjectQueryParam cmsSubjectQueryParam);

    int incReadCount(@Param("id") Long id);
}
