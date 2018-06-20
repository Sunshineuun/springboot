package com.qiushengming.core.service;

import com.qiushengming.entity.code.Page;
import com.qiushengming.exception.SystemException;

import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/6/15.
 */
public interface PagingService {
    /**
     * 分页查询
     * @param params sql拼接的参数
     * @param page 分页的实体，包含排序，查询等参数
     * @return {@link Page}
     */
    Page findOnPage(Map<String, Object> params, final Page<?> page) throws SystemException;
}
