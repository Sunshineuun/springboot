package com.qiushengming.core.controller;

import com.qiushengming.core.service.DictionaryService;
import com.qiushengming.entity.code.MinNieResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/6/1.
 */
@RestController
public class DictionaryController extends BaseController {

    @Resource(name = "dictionaryService")
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/getDictionaryByTypeCode/{typeCode}", method = RequestMethod.GET)
    public String getDictionaryByTypeCode(@PathVariable String typeCode,
                                          @RequestParam(value = "hasEmpty", required = false) boolean hasEmpty) {
        return wrap(dictionaryService.getDictionaryByTypeCode(typeCode), hasEmpty);
    }

    @RequestMapping("/searchDictionaryOnQueryId/{queryId}")
    public MinNieResponse searchDictionaryOnQueryId(
            @PathVariable String queryId,
            @RequestParam("limit") int limit,
            @RequestParam(value = "hasEmpty", required = false) boolean hasEmpty) {
        Map<String, String> map = dictionaryService
                .searchDictionaryOnQueryId(queryId, limit,
                        this.getRequestParameters());
        List<String[]> list = new ArrayList<>(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new String[]{entry.getKey(), entry.getValue()});
        }

        if (hasEmpty) {
            list.add(new String[]{"", ""});
        }

        return new MinNieResponse(true, "", list);
    }

    @ResponseBody
    @RequestMapping("/getDictionaryOnQueryId/{queryId}")
    public String getDictionaryOnQueryId(
            @PathVariable String queryId,
            @RequestParam(value = "hasEmpty", required = false) boolean hasEmpty) {
        return wrap(dictionaryService.getDictionaryOnQueryId(queryId), hasEmpty);
    }


    @RequestMapping("/loadDictionary")
    public Map<String, String> loadDictionary(
            @RequestParam(value = "typeCodeStr",
                    required = false) String typeCodeStr,
            @RequestParam(value = "queryIdStr",
                    required = false) String queryIdStr) {
        Map<String, String> result = new HashMap<>();

        if (StringUtils.isNotEmpty(queryIdStr)) {
            String[] queryIds = queryIdStr.split(",");
            for (String queryId : queryIds) {
                result.put(queryId, this.getDictionaryOnQueryId(queryId, false));
            }
        }

        if (StringUtils.isNotEmpty(typeCodeStr)) {
            String[] typeCodes = typeCodeStr.split(",");
            for (String typeCode : typeCodes) {
                result.put(typeCode,
                        this.getDictionaryByTypeCode(typeCode, false));
            }
        }

        return result;
    }

    /**
     * @param map      Map
     * @param hasEmpty 是否增加空白选项
     * @return String
     */
    private String wrap(Map<String, String> map, boolean hasEmpty) {
        if (map.isEmpty() && !hasEmpty) {
            return "[]";
        } else if (map.isEmpty() && hasEmpty) {
            return "[[\"\",\"\"]]";
        }

        StringBuilder sb = new StringBuilder();
        String str = null;
        sb.append("[");
        if (hasEmpty) {
            sb.append("[\"\",\"\"],");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("[\"").append(entry.getKey()).append("\",\"")
                    .append(entry.getValue()).append("\"],");
        }
        if (sb.length() > 1) {
            str = sb.substring(0, sb.length() - 1);
        }
        return str + "]";
    }
}
