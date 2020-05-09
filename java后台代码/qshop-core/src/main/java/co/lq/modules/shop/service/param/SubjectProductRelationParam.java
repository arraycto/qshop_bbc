package co.lq.modules.shop.service.param;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品关联文章
 *
 * @author songbin
 * @since 2020年4月10日 下午4:52:56
 */
@Data
public class SubjectProductRelationParam implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 文章id
     */
    private Long              subjectId;
}
