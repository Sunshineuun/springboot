package com.qiushengming.service;

import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/4/22
 */
public interface BookService
    extends BaseService {

    /**
     * 获取借书记录的表数据
     * @return <code>List<Map<String, Object>></code>
     */
    List<Map<String, Object>> getHistoryBook();
}

