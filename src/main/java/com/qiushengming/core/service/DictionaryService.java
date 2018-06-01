package com.qiushengming.core.service;

import com.qiushengming.entity.code.Dictionary;

import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/6/1.
 */
public interface DictionaryService extends ManagementService<Dictionary> {
    /**
     *
     * @param typeCode 字典编码
     * @return Map
     */
    Map<String,String> getDictionaryByTypeCode(String typeCode);

    /**
     *
     * @param queryId queryId
     * @param limit 限定条数
     * @param params 查询参数
     * @return Map
     */
    Map<String,String> searchDictionaryOnQueryId(String queryId, int limit, Map<String, Object> params);

    /**
     *
     * @param queryId queryId
     * @return Map
     */
    Map<String,String> getDictionaryOnQueryId(String queryId);
}
