package com.qiushengming.service.impl;

import com.qiushengming.annotation.Column;
import com.qiushengming.core.service.LuceneService;
import com.qiushengming.core.service.impl.AbstractImportService;
import com.qiushengming.entity.DiseaseInfo;
import com.qiushengming.exception.SystemException;
import com.qiushengming.service.DiseaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/7/6
 */
@Slf4j
@Service(value = "diseaseInfoService")
public class DiseaseInfoServiceImpl
        extends AbstractImportService<DiseaseInfo>
        implements DiseaseInfoService {

    @Resource(name = "luceneService")
    private LuceneService luceneService;

    @Override
    protected String getSheetName() {
        return "疾病资料库";
    }

    @Override
    protected int getMinColumns() {
        return 22;
    }

    @Override
    public synchronized void importExcel(InputStream is, Map<String, Object> params) {
        List<Map<String, Object>> records =
                readImportRecordsFromExcel(is, params);

        // 将数据装换为实体
        for (Map<String, Object> map : records) {
            try {
                // 进行lucene存储
                luceneService.add(toObject(map));
            } catch (NoSuchMethodException | InvocationTargetException
                    | IllegalAccessException | IntrospectionException | IOException e) {
                e.printStackTrace();
            }
        }

        // 成功
        log.info("存储完毕");
    }

    @Override
    public List<Map<String, String>> searchLucene(String field, String value) {
        /*List<DiseaseInfo> diseaseInfos = new ArrayList<>();*/
        List<Map<String, String>> maps = new ArrayList<>();
        try {
            List<Document> docs = luceneService.search(field, value);
            for (Document doc : docs) {
                Map<String, String> map = new LinkedHashMap<>();
                for (IndexableField f : doc.getFields()) {
                    map.put(f.name(), doc.get(f.name()));
                }
                maps.add(map);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        log.debug("共计：{}条", maps.size());
        return maps;
    }

    @Override
    public void deleteLuceneAll() {
        luceneService.delAll();
    }

    /**
     * 将map转换为实体
     *
     * @param map {@link Map}
     * @return {@link DiseaseInfo}
     */
    private DiseaseInfo toObject(Map<String, Object> map) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Class entityClass = getEntityClass();
        Method[] methods = entityClass.getMethods();
        DiseaseInfo diseaseInfo = new DiseaseInfo();

        for (Method m : methods) {
            // 跳过不是get的方法
            if (!(StringUtils.startsWith(m.getName(), "get")
                    || StringUtils.startsWith(m.getName(), "is"))) {
                continue;
            }

            if (StringUtils.equals(m.getName(), "getId")
                    || StringUtils.equals(m.getName(), "getClass")) {
                continue;
            }

            Column column = m.getAnnotation(Column.class);
            if (null == column) {
                String erroinfo = String.format("{%s}的{%s}方法缺失{%s}注解！",
                        entityClass.getName(), m.getName(), Column.class.getName());
                log.error(erroinfo);
                throw new SystemException(erroinfo);
            }

            // 获取set方法
            String setMethodName = "set" + m.getName().substring(3);
            Method setMehod = entityClass.getMethod(setMethodName, m.getReturnType());

            /*log.debug("方法：{}， 值：{}", setMehod, column.chineseName());*/
            setMehod.invoke(diseaseInfo, map.get(column.chineseName()));

        }

        return diseaseInfo;
    }
}
