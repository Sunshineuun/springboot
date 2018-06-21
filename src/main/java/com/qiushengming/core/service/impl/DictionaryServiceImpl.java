package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.DictionaryService;
import com.qiushengming.entity.code.Dictionary;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/6/1.
 */
@Service("dictionaryService")
public class DictionaryServiceImpl
        extends AbstractManagementService<Dictionary>
        implements DictionaryService {

    private static final String LABEL = "LABEL";
    private static final String VALUE = "VALUE";

    @Override
    public Map<String, String> getDictionaryByTypeCode(String typeCode) {
        return warp(getDao().<Map>query("getDictionaryByTypeCode", typeCode));
    }

    @Override
    public Map<String, String> searchDictionaryOnQueryId(String queryId, int limit, Map<String, Object> params) {
        Map<String, String> result = new LinkedHashMap<>();
        List<Map<String, String>> selectList =
                getDao().query(queryId, params, 0, limit);
        for (Map<String, String> map : selectList) {
            if (map == null) {
                continue;
            }

            result.put(map.get(VALUE), map.get(LABEL));
        }
        return result;
    }

    @Override
    public Map<String, String> getDictionaryOnQueryId(String queryId) {
        return warp(getDao().<Map>query(queryId));
    }

    private Map<String, String> warp(List<Map> selectList) {
        Map<String, String> result = new LinkedHashMap<>();
        for (Map map : selectList) {
            // Map map = (Map)obj;
            if (map == null) {
                continue;
            }

            String value = map.get(VALUE) == null ? "" : map.get(VALUE) + "";
            String label = map.get(LABEL) == null ? "" : map.get(LABEL) + "";
            result.put(value, label);
        }
        return result;
    }
}
