package co.lq.modules.activity.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.activity.entity.StorePink;
import co.lq.modules.activity.web.dto.PinkDTO;
import co.lq.modules.activity.web.param.StorePinkQueryParam;
import co.lq.modules.activity.web.vo.StorePinkQueryVo;

/**
 * <p>
 * 拼团表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-11-19
 */
@Repository
public interface StorePinkMapper extends BaseMapper<StorePink> {

    @Select("SELECT p.id,p.uid,p.people,p.price,p.stop_time as stopTime,u.nickname,u.avatar"
            + " FROM store_pink p INNER JOIN `user` u ON u.uid=p.uid"
            + " WHERE stop_time > unix_timestamp(now()) AND p.cid = #{cid} AND p.k_id = 0 "
            + "AND p.is_refund = 0 ORDER BY p.add_time DESC")
    List<PinkDTO> getPinks(long cid);

    //<![CDATA[ >= ]]>
    @Select("SELECT p.id,u.nickname,u.avatar" + " FROM store_pink p RIGHT  JOIN `user` u ON u.uid=p.uid"
            + " where p.status= 2 AND p.uid <> ${uid} " + "AND p.is_refund = 0")
    List<PinkDTO> getPinkOkList(long uid);

    @Select("SELECT p.id,p.uid,p.people,p.price,p.stop_time as stopTime,u.nickname,u.avatar"
            + " FROM store_pink p LEFT JOIN `user` u ON u.uid=p.uid" + " where p.k_id= ${kid} " + "AND p.is_refund = 0")
    List<PinkDTO> getPinkMember(long kid);

    @Select("SELECT p.id,p.uid,p.people,p.price,p.stop_time as stopTime,u.nickname,u.avatar"
            + " FROM store_pink p LEFT JOIN `user` u ON u.uid=p.uid" + " where p.id= ${id} ")
    PinkDTO getPinkUserOne(long id);

    @Select("select IFNULL(sum(total_num),0) from store_pink " + "where status=2 and is_refund=0")
    int sumNum();

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StorePinkQueryVo getStorePinkById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storePinkQueryParam
     * @return
     */
    IPage<StorePinkQueryVo> getStorePinkPageList(@Param("page") Page page,
                                                 @Param("param") StorePinkQueryParam storePinkQueryParam);

}
