package co.lq.modules.shop.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * TODO 类实现描述
 *
 * @author songbin
 * @since 2020年3月19日 下午6:13:04
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
}
