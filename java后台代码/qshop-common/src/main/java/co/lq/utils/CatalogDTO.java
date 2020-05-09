package co.lq.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 平台类目
 *
 * @author songbin
 * @since 2020年3月11日 下午5:49:40
 */
@Data
public class CatalogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;
    /**
     * 上级分类编号
     */
    private Long              pid;

    /**
     * 商品分类名称
     */
    private String            name;

    /**
     * 类目关键字
     */
    private String            keywords;

    /**
     * 类目广告语介绍
     */
    private String            descs;

    /**
     * 类目图标
     */
    private String            iconUrl;

    /**
     * 类目图片
     */
    private String            picUrl;

    /**
     * 排序
     */
    private Integer           sortOrder;

    private List<CatalogDTO>  children         = new ArrayList<>();

}
