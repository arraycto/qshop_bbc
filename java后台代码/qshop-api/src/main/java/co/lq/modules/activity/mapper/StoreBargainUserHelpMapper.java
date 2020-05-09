package co.lq.modules.activity.mapper;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.activity.entity.StoreBargainUserHelp;
import co.lq.modules.activity.web.param.StoreBargainUserHelpQueryParam;
import co.lq.modules.activity.web.vo.StoreBargainUserHelpQueryVo;

/**
 * <p>
 * 砍价用户帮助表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-12-21
 */
@Repository
public interface StoreBargainUserHelpMapper extends BaseMapper<StoreBargainUserHelp> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreBargainUserHelpQueryVo getStoreBargainUserHelpById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeBargainUserHelpQueryParam
     * @return
     */
    IPage<StoreBargainUserHelpQueryVo> getStoreBargainUserHelpPageList(@Param("page") Page page,
                                                                       @Param("param") StoreBargainUserHelpQueryParam storeBargainUserHelpQueryParam);

}
