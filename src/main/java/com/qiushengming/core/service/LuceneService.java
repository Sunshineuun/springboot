package com.qiushengming.core.service;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/7/6
 */
public interface LuceneService {
    /**
     * 增加一个document
     *
     * @param o 实体
     * @return 插入成功的第几位
     * @throws IOException               IO异常
     * @throws IntrospectionException    反射引发的异常
     * @throws InvocationTargetException 反射引发的异常
     * @throws IllegalAccessException    反射引发的异常
     */
    long add(Object o) throws IOException, IntrospectionException, InvocationTargetException, IllegalAccessException;

    /**
     * 删除所有
     */
    void delAll();

    /**
     * 查询
     *
     * @param field 查询的字段
     * @param value 查询的值
     * @return {@link Document}
     * @throws ParseException 解析异常
     * @throws IOException    IO异常
     */
    List<Document> search(String field, String value) throws
            ParseException, IOException;
}

