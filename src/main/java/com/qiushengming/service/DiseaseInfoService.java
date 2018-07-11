package com.qiushengming.service;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.DiseaseInfo;

import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/7/6
 */
public interface DiseaseInfoService
        extends ManagementService<DiseaseInfo> {

    /**
     * 从lucene中查询
     *
     * @param field 列名
     * @param value 值
     * @return 查询结果，经过转换将{@link org.apache.lucene.document.Document} 转换为{@link Map}
     */
    List<Map<String, String>> searchLucene(String field, String value);

    void deleteLuceneAll();
}
