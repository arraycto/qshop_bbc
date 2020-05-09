package co.lq.modules.shop.service;

import java.io.Serializable;
import java.util.List;

import co.lq.common.service.BaseService;
import co.lq.common.web.vo.Paging;
import co.lq.modules.shop.entity.StoreProductReply;
import co.lq.modules.shop.web.dto.ReplyCountDTO;
import co.lq.modules.shop.web.param.StoreProductReplyQueryParam;
import co.lq.modules.shop.web.vo.StoreProductReplyQueryVo;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author billy
 * @since 2019-10-23
 */
public interface ApiStoreProductReplyService extends BaseService<StoreProductReply> {

    ReplyCountDTO getReplyCount(long productId);

    StoreProductReplyQueryVo handleReply(StoreProductReplyQueryVo replyQueryVo);

    StoreProductReplyQueryVo getReply(long productId);

    List<StoreProductReplyQueryVo> getReplyList(long productId, int type, int page, int limit);

    int getInfoCount(Long oid, String unique);

    int replyCount(String unique);

    int productReplyCount(long productId);

    String doReply(long productId, int count);

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    StoreProductReplyQueryVo getStoreProductReplyById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param storeProductReplyQueryParam
     * @return
     */
    Paging<StoreProductReplyQueryVo> getStoreProductReplyPageList(StoreProductReplyQueryParam storeProductReplyQueryParam)
            throws Exception;

}
