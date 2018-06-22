package com.qiushengming.utils;


import com.qiushengming.annotation.Column;
import com.qiushengming.entity.code.Page;
import com.qiushengming.exception.SystemException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配合前端Extjs过滤进行数据查询，组装SQL片段
 * 目前支持的： <br>
 *
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
    private static final String LIKE = "like";

    private static final Logger logger =
        LoggerFactory.getLogger(GridSQLBuilder.class);

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
        OPERATORS.put("like", "like");
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

    @SuppressWarnings({
        "SuspiciousMethodCalls", "unchecked", "UnusedReturnValue"})
    public static Map buildSqlFragmentAndParams(Map params, Page page,
        Class clazz) {
        List<Map<String, Object>> filters =
            (List<Map<String, Object>>) page.getFilter();

        if (CollectionUtils.isEmpty(filters)) {
            return params;
        }

        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (Map<String, Object> map : filters) {

            String field = "f" + String.valueOf(i);

            switch (map.get("value").getClass().getName()) {
                case "java.util.ArrayList":
                case "java.util.List":
                    sb.append(appendList(params, map, clazz, field));
                    break;
                case "java.lang.String":
                    sb.append(appendString(params, map, clazz, field));
                    break;
                default:
                    logger.warn(
                        "注意：在条件查询时，sql片段条件组装未命中。" + "" + map.get("value")
                            .getClass()
                            .getName() + "条件详情：" + map.toString());
                    break;
            }

            // 多条件下用或来查询，后续需要增加参数进行 [OR\AND]的切换，现在默认设置为OR
            if (i + 1 < filters.size()) {
                sb.append(" OR ");
            }

            i += 1;
        }

        params.put(SQLFRAGMENT, sb.toString());

        return params;
    }

    /**
     * 拼接那些值为List类型的属性
     *
     * @param params 参数，用于将sql查询的数据存储在里面map.get("value")
     * @param map    当前查询条件的相关属性`{operator=in, value=[1], property=occupy}`
     * @param clazz  当前对象
     * @param field  `#`占位符
     * @return sql组装的片段
     */
    @SuppressWarnings("unchecked")
    private static String appendList(Map params, Map map, Class clazz,
        String field) {

        StringBuilder sb = new StringBuilder();

        String property = (String) map.get("property");
        String operator = (String) map.get("operator");
        List value = (List) map.get("value");

        // 查询列
        sb.append(wrapProperty(property, clazz)).append(" ")
            // 运算符
            .append(OPERATORS.get(operator)).append(" ( ");

        int i = 0;
        for (Object o : value) {
            // 值
            sb.append(" #{ ")
                .append(field)
                .append(i)
                .append(" , jdbcType=VARCHAR} ");
            if (i + 1 < value.size()) {
                sb.append(",");
            }
            params.put(field + String.valueOf(i), o);
            i += 1;
        }
        sb.append(" ) ");

        // 参数存储

        return sb.toString();
    }

    /**
     * 拼接那些值为String类型的属性
     *
     * @param params 参数，用于将sql查询的数据存储在里面map.get("value")
     * @param map    当前查询条件的相关属性`{"operator":"like","value":"12","property":"name"}`
     * @param clazz  当前对象
     * @param field  `#`占位符
     * @return sql组装的片段
     */
    @SuppressWarnings("unchecked")
    private static String appendString(Map params, Map map, Class clazz,
        String field) {

        StringBuilder sb = new StringBuilder();
        String property = (String) map.get("property");
        String operator = (String) map.get("operator");
        String value = (String) map.get("value");

        // 查询列
        sb.append(wrapProperty(property, clazz)).append(" ")
            // 运算符
            .append(OPERATORS.get(operator))
            // 值
            .append(" #{ ").append(field).append(" , jdbcType=VARCHAR} ");

        // 参数存储
        params.put(field, wrapParam(operator, value));

        return sb.toString();
    }

    /**
     * 请传入字符串，否则会出错
     *
     * @param property 属性名称
     * @return 映射数据库的值
     */
    @SuppressWarnings("unchecked")
    private static String wrapProperty(Object property, Class clazz) {
        if (!property.getClass().equals(String.class)) {
            throw new SystemException("属性名称错误：" + property.toString());
        }
        String s = "get" + CustomStringUtils.captureName1((String) property);

        try {
            Method m = clazz.getMethod(s);
            Column c = m.getAnnotation(Column.class);
            if (c == null) {
                return ((String) property).toUpperCase();
            } else {
                return c.value().toUpperCase();
            }
        } catch (NoSuchMethodException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 包装值对象
     *
     * @param op    like\not like\begin like\end like
     * @param value value
     * @return 包装后的值
     */
    protected static Object wrapParam(String op, Object value) {
        if (StringUtils.equals(CN, op) || StringUtils.equals(NC, op)
            || StringUtils.equals(LIKE, op)) {
            value = "%" + value + "%";
        } else if (BN.equals(op)) {
            value = value + "%";
        } else if (EN.equals(op)) {
            value = "%" + value;
        }
        return value;
    }

    /**
     * 在in or not in 的查询中，将List转换为`('1','2','3')`的情况
     *
     * @param operator sql运算符 in or not in
     * @param value    值
     * @return String 例如：`('1','2','3')`
     */
    private static String wrapListParam(String operator, List value) {
        StringBuilder sb = new StringBuilder();
        //        sb.append("'");
        sb.append(StringUtils.join(value, ","));
        //        sb.append("'");
        return sb.toString();
    }

    // public static void main(String[] args){
    // String json =
    // "{\"groupOp\":\"OR\", \"rules\":[{\"field\":\"b.pro_code\",\"op\":\"eq\",\"data\":\"ddd\"},{\"field\":\"b.pro_name\",\"op\":\"cn\",\"data\":\"uuuu\"},{\"field\":\"a.display_name\",\"op\":\"eq\",\"data\":\"sss\"} ]}";
    // }

}
