package co.lq.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import co.lq.modules.shop.vo.Paging;

/**
 * 分页工具
 *
 * @author billy
 * @date 2018-12-10
 */
public class PageUtilCore extends PageUtil {

    /**
     * Page 数据处理，预防redis反序列化报错
     */
    public static Map<String, Object> toPage(Paging page) {
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getRecords());
        map.put("totalElements", page.getTotal());
        return map;
    }
}
