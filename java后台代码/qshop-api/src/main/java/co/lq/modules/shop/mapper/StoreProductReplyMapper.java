package co.lq.modules.shop.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import co.lq.modules.shop.entity.StoreProductReply;
import co.lq.modules.shop.web.param.StoreProductReplyQueryParam;
import co.lq.modules.shop.web.vo.StoreProductReplyQueryVo;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
@Repository
public interface StoreProductReplyMapper extends BaseMapper<StoreProductReply> {

    @Select("select A.product_score as productScore,A.service_score as serviceScore,"
            + "A.comment,A.merchant_reply_content as merchantReplyContent,"
            + "A.merchant_reply_time as merchantReplyTime,A.pics as pictures,A.add_time as addTime,"
            + "B.nickname,B.avatar,C.cart_info as cartInfo" + " from store_product_reply A left join `user` B "
            + "on A.uid = B.uid left join store_order_cart_info C on A.unique = C.unique"
            + " where A.product_id=#{productId} and A.is_del=0 and A.reply_type='product' "
            + "order by A.add_time DESC limit 1")
    StoreProductReplyQueryVo getReply(long productId);

    @Select("<script>select A.product_score as productScore,A.service_score as serviceScore,"
            + "A.comment,A.merchant_reply_content as merchantReplyContent,"
            + "A.merchant_reply_time as merchantReplyTime,A.pics as pictures,A.add_time as addTime,"
            + "B.nickname,B.avatar,C.cart_info as cartInfo" + " from store_product_reply A left join `user` B "
            + "on A.uid = B.uid left join store_order_cart_info C on A.unique = C.unique"
            + " where A.product_id=#{productId} and A.is_del=0 and A.reply_type='product' "
            + "<if test='type == 1'>and A.product_score = 5</if>"
            + "<if test='type == 2'>and A.product_score &lt; 5 and A.product_score &gt; 2</if>"
            + "<if test='type == 3'>and A.product_score &lt; 2</if>" + " order by A.add_time DESC</script>")
    List<StoreProductReplyQueryVo> selectReplyList(Page page, @Param("productId") long productId,
                                                   @Param("type") int type);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductReplyQueryVo getStoreProductReplyById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param storeProductReplyQueryParam
     * @return
     */
    IPage<StoreProductReplyQueryVo> getStoreProductReplyPageList(@Param("page") Page page,
                                                                 @Param("param") StoreProductReplyQueryParam storeProductReplyQueryParam);

}
