package com.qiushengming.service;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.Book;

import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
public interface BookService
        extends ManagementService<Book> {

    /**
     * 获取借书记录的表数据
     *
     * @return <code>List<Map<String, Object>></code>
     */
    List<Map<String, Object>> getHistoryBook();
}

