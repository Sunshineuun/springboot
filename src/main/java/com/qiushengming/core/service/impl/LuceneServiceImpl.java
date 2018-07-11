package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.LuceneService;
import com.qiushengming.lucene.analyzer.IKAnalyzer4Lucene7;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 疾病资料库
 *
 * @author qiushengming
 * @date 2018/7/6
 */
@Slf4j
@Service("luceneService")
public class LuceneServiceImpl
        implements LuceneService, InitializingBean {
    /**
     * 文件的路径
     */
    @Value("${minnie.lucene.filepath}")
    private String filePath;
    private FSDirectory directory;
    /**
     * 分词器
     */
    private Analyzer analyzer = new IKAnalyzer4Lucene7();

    /**
     * 写入配置
     */
    private IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
    /**
     * 写入器
     */
    private IndexWriter writer;
    /**
     * 读取器
     */
    private IndexReader reader;
    /**
     * 查询器
     */
    private IndexSearcher searcher;

    public LuceneServiceImpl() throws IOException {
    }

    /**
     * 增加
     *
     * @param bean 实体
     * @throws IOException IO异常
     */
    @Override
    public long add(Object bean) throws IOException, IntrospectionException,
            InvocationTargetException, IllegalAccessException {
        Document document = new Document();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!StringUtils.equals(propertyName, "class")) {
                Method readMethod = descriptor.getReadMethod();
                if (!readMethod.getReturnType().equals(String.class)) {
                    log.debug("{}方法的返回值不为String类型，我能期待是String类型，或者定义好了toString方法", readMethod.getName());
                    continue;
                }
                Object result = readMethod.invoke(bean);

                if (null == result) {
                    result = "";
                }

                document.add(new TextField(propertyName, result.toString(), Field.Store.YES));
            }
        }

        long result = getWriter().addDocument(document);
        getWriter().flush();
        getWriter().commit();
        /*log.debug("插入成功的数量: {}", );*/
        return result;
    }

    @Override
    public void delAll() {
        try {
            getWriter().deleteAll();
            getWriter().flush();
            getWriter().commit();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }

    /**
     * @param field    字段
     * @param value    旧的值
     * @param newValue 新的值
     */
    private void update(String field, String value, String newValue) {
        try {
            Document document = new Document();
            document.add(new TextField(field, newValue, Field.Store.YES));
            log.info("更新的返回结果：{}", getWriter().updateDocument(new Term(field, value), document));
            getWriter().commit();
        } catch (IOException e) {
            log.error("{}", e);
        }
    }

    /**
     * 删除
     *
     * @param field 字段
     * @param value 值
     */
    private void del(String field, String value) {
        try {
            QueryParser queryParser = new QueryParser(field, analyzer);
            Query query = queryParser.parse(value);
            log.info("删除的返回结果：{}", getWriter().deleteDocuments(query));
            getWriter().commit();
        } catch (IOException e) {
            log.error("{}", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询功能
     *
     * @param field 字段
     * @param value 值
     * @throws ParseException 异常
     * @throws IOException    异常
     */
    @Override
    public List<Document> search(String field, String value) throws
            ParseException, IOException {
        // 多参数查询
        //Query q = MultiFieldQueryParser.parse(new String[]{},new String[]{},new StandardAnalyzer());

        //单条件
        QueryParser queryParser = new QueryParser(field, analyzer);
        Query query = queryParser.parse(value);

        TopDocs topDocs = getSearcher().search(query, 100);

        long conut = topDocs.totalHits;
        /*System.out.println("检索总条数：" + conut);*/
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        List<Document> result = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // TODO 暂时把相关度低于5的给过滤掉
            if (scoreDoc.score < 5) {
                log.info("相关度：{},过滤当前查询结果。", scoreDoc.score);
                continue;
            }
            Document doc = getSearcher().doc(scoreDoc.doc);
            result.add(doc);
            /*log.info(document.get(field));*/
        }
        return result;
    }

    private IndexWriter getWriter() throws IOException {
        if (writer == null) {
            writer = new IndexWriter(directory, indexWriterConfig);
        }
        return writer;
    }

    private IndexReader getReader() throws IOException {
        if (reader == null) {
            reader = DirectoryReader.open(directory);
        }
        return reader;
    }

    private IndexSearcher getSearcher() throws IOException {
        if (searcher == null) {
            searcher = new IndexSearcher(getReader());
        }
        return searcher;
    }

    private void testPath() {
        Path path = Paths.get(filePath);
        log.info("根路径:{}", path.getRoot());
        log.info("path中包含的路径数量:{}", path.getNameCount());

        Path absolutePath = path.toAbsolutePath();
        log.info("绝对路径:{}", absolutePath.toString());
        log.info("绝对路径下包含的路径数量:{}", absolutePath.getNameCount());

        System.out.println(path);
    }

    private void check() {
        try {
            log.info("有效的索引文档:" + getReader().numDocs());
            // 总共的索引文档
            log.info("总共的索引文档:" + getReader().maxDoc());
            // 删掉的索引文档，其实不恰当，应该是在回收站里的索引文档
            log.info("删掉的索引文档:" + getReader().numDeletedDocs());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            // 初始化目录
            directory = FSDirectory.open(Paths.get(filePath));
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        } catch (IOException e) {
            log.error("{}", e);
        }
    }
}
