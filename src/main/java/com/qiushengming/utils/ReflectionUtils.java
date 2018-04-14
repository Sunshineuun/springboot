package com.qiushengming.utils;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>反射方法工具类</h1><br>
 * <p>
 * <b>NOTE : 功能概述。</b>
 * <p>
 * 提供与类相关的反射处理方法
 * </p>
 */
public class ReflectionUtils
    extends org.springframework.util.ReflectionUtils {

    /**
     * 通过反射、判定指定对象(target)中是否存在指定字段(fieldName)
     *
     * @param target    判定对象
     * @param fieldName 判定字段名
     * @param type      判定对象类型
     * @return <br>
     * true = 存在<br>
     * false = 不存在<br>
     */
    public static boolean hasField(Object target, String fieldName,
        Class<?> type) {
        return findField(target.getClass(), fieldName, type) == null;
    }


    /**
     * 通过反射、取得指定对象的字段值
     *
     * @param target    指定对象
     * @param fieldName 指定字段名
     * @return <br>
     * 指定对象的指定字段值
     */
    public static Object getFieldValue(Object target, String fieldName) {
        Field field = findField(target.getClass(), fieldName);
        makeAccessible(field);
        return getField(field, target);
    }


    /**
     * @param target 对象
     * @param fieldName 属性名称
     * @param value 值
     */
    public static void setFieldValue(Object target, String fieldName,
        Object value) {
        Field field = findField(target.getClass(), fieldName);
        makeAccessible(field);
        setField(field, target, value);
    }


    /**
     * @param clazz class
     * @return field list
     */
    public static List<Field> getFields(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Class<?> searchType = clazz;
        List<Field> fieldList = new ArrayList<>();
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            searchType = searchType.getSuperclass();
        }
        return fieldList;
    }


    /**
     * @param clazz class
     * @return 返回所有属性名称
     */
    public static List<String> getFieldNames(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Class<?> searchType = clazz;
        List<String> fieldNameList = new ArrayList<String>();
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                fieldNameList.add(field.getName());
            }
            searchType = searchType.getSuperclass();
        }
        return fieldNameList;
    }

    /**
     * 获取List属性的field的范型
     *
     * @param field
     * @return
     */
    public static Class<?> getListFiledPattern(Field field) {
        if (field == null) {
            return null;
        }
    /* field类型 */
        Class<?> fieldClass = field.getType();
    /* field的类型的接口必须是List */
        if (!List.class.isAssignableFrom(fieldClass)) {
            return null;
        }
    /* 得到其Generic的类型 */
        Type fc = field.getGenericType();
        if (fc == null) {
            return null;
        }
    /* 如果是泛型参数的类型 */
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
      /* 得到泛型里的class类型对象 */
            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
            if (genericClazz != null) {
                return genericClazz;
            }
        }
        return null;
    }
}
