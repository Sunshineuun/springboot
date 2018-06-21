package com.qiushengming.utils;


import com.qiushengming.entity.code.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/6/15.
 */
public class GridSQLBuilder {
    /**
     * eq : 等于 ne ：不等于 lt ：小于 le ：小于等于 gt ：大于 ge ：大于等于 nu ：is null nn : is not
     * null in : 属于 ni ：不属于 bw : 开始于 bn : 不开始于 ew : 结束于 en ：不结束于 cn : 包含 nc :
     * 不包含
     */
    private static Map<String, String> OPERATORS = new HashMap<>();

    /**
     * 查询操作符
     */
    private static final String SEARCHOPER = "searchOper";

    /**
     * 查询关键字
     */
    private static final String SEARCHSTRING = "searchString";

    /**
     * 查询字段
     */
    private static final String SEARCHFIELD = "searchField";

    /**
     * 组合查询参数名
     */
    private static final String FILTERS = "filters";

    /**
     * SQL片段
     */
    public static String SQLFRAGMENT = "SqlFragment";

    private static final String CN = "cn";
    private static final String NC = "nc";
    private static final String BN = "nc";
    private static final String EN = "nc";

    static {
        OPERATORS.put("eq", "=");
        OPERATORS.put("ne", "<>");
        OPERATORS.put("lt", "<");
        OPERATORS.put("le", "<=");
        OPERATORS.put("gt", ">");
        OPERATORS.put("ge", ">=");
        OPERATORS.put("nu", "is null");
        OPERATORS.put("nn", "is not null");
        OPERATORS.put("in", "in");
        OPERATORS.put("ni", "not in");
        OPERATORS.put("bw", "");
        OPERATORS.put("bn", "like");
        OPERATORS.put("ew", "");
        OPERATORS.put("en", "like");
        OPERATORS.put("cn", "like");
        OPERATORS.put("nc", "not like");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> buildSqlFragmentAndParams(Map map) {
        StringBuilder sb = new StringBuilder();
        if (map.containsKey(SEARCHFIELD)
            && StringUtils.isNotEmpty((String) map.get(SEARCHFIELD))
            && map.containsKey(SEARCHOPER)
            && StringUtils.isNotEmpty((String) map.get(SEARCHOPER))
            && map.containsKey(SEARCHSTRING)
            && StringUtils.isNotEmpty((String) map.get(SEARCHSTRING))) {
            // 简单查询条件
            sb.append(map.get(SEARCHFIELD))
                .append(" ")
                .append(OPERATORS.get(map.get(SEARCHOPER)))
                .append(" #{")
                .append(SEARCHSTRING)
                .append("}");

            map.put(SQLFRAGMENT, sb.toString());
            map.put(SEARCHSTRING,
                wrapParam((String) map.get(SEARCHOPER), map.get(SEARCHSTRING)));
        } else {
            // 复合查询
            /*
             * {"groupOp":"OR", "rules":[
             * {"field":"b.pro_code","op":"eq","data":"ddd"},
             * {"field":"b.pro_name","op":"cn","data":"uuuu"},
             * {"field":"a.display_name","op":"eq","data":"sss"} ]}
             */
            if (map.containsKey(FILTERS)
                && StringUtils.isNotEmpty((String) map.get(FILTERS))) {
                Map<String, Object> jsonMap =
                    JackJson.fromJsonToObject((String) map.get(FILTERS),
                        Map.class);
                List<Map<String, String>> rules =
                    (List<Map<String, String>>) jsonMap.get("rules");
                for (int i = 0; i < rules.size(); i++) {
                    Map<String, String> rule = rules.get(i);

                    // 拼接SQL
                    sb.append(rule.get("field"))
                        .append(" ")
                        .append(OPERATORS.get(rule.get("op")))
                        .append(" #{field")
                        .append(i)
                        .append("} ");
                    if (i + 1 < rules.size()) {
                        sb.append(jsonMap.get("groupOp")).append(" ");
                    }

                    // 组装参数
                    map.put("field" + i,
                        wrapParam(rule.get("op"), rule.get("data")));
                }

                map.put(SQLFRAGMENT, sb.toString());
            }
        }
        return map;
    }

    public static String buildSqlFragmentAndParams(Page page) {
        List<Map<String, Object>> filters =
            (List<Map<String, Object>>) page.getFilter();
        if (CollectionUtils.isEmpty(filters)) {
            return "";
        }
        for (Map<String, Object> map : filters) {
            System.out.println(map);
        }
        return "";
    }

    /**
     * 包装值对象
     *
     * @param op    like\not like\begin like\end like
     * @param value value
     * @return 包装后的值
     */
    protected static Object wrapParam(String op, Object value) {
        if (StringUtils.equals(CN, op) || NC.equals(op)) {
            value = "%" + value + "%";
        } else if (BN.equals(op)) {
            value = value + "%";
        } else if (EN.equals(op)) {
            value = "%" + value;
        }
        return value;
    }

    // public static void main(String[] args){
    // String json =
    // "{\"groupOp\":\"OR\", \"rules\":[{\"field\":\"b.pro_code\",\"op\":\"eq\",\"data\":\"ddd\"},{\"field\":\"b.pro_name\",\"op\":\"cn\",\"data\":\"uuuu\"},{\"field\":\"a.display_name\",\"op\":\"eq\",\"data\":\"sss\"} ]}";
    // }

}
