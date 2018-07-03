package com.qiushengming.core.service;

import java.io.InputStream;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/7/3
 */
public interface ImportService {
    /**
     * 导入Excel
     * @param is 输入流
     * @param params 相关参数
     */
    void importExcel(InputStream is, Map<String, Object> params);

    /**
     * 导入CSV
     * @param is 输入流
     * @param params 相关参数
     */
    void importCsv(InputStream is, Map<String, Object> params);
}

