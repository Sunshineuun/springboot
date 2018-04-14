package com.qiushengming.mybatis.annotation.support;

import com.qiushengming.annotation.Table;
import com.qiushengming.exception.MappingException;
import com.qiushengming.mybatis.builder.AnnoatationMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @author qiushengming
 */
public class AnnotationSqlSessionFactoryBean
    extends SqlSessionFactoryBean
    implements ResourceLoaderAware {

    private static final String RESOURCE_PATTERN = "/**/*.class";

    /**
     * 要被扫描包的路径
     */
    private String[] packagesToScan;
    /**
     * 资源解析器
     */
    private ResourcePatternResolver resourcePatternResolver =
        new PathMatchingResourcePatternResolver();
    /**
     * 自定义注解配置类
     */
    private AnnotationConfiguration annotationConfiguration;

    /**
     * 实体过滤器
     */
    private TypeFilter[] entityTypeFilters =
        new TypeFilter[] {new AnnotationTypeFilter(Table.class, false)};

    public String[] getPackagesToScan() {
        return packagesToScan;
    }

    public void setPackagesToScan(String packagesToScan) {
        // 依据",; \t\n"进行切割
        this.packagesToScan = tokenizeToStringArray(packagesToScan,
            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
    }

    /**
     * 构建sqlsession工厂的过程
     * 1. 构建configuration
     * 2. 设置对象工厂
     * 3. 设置对象包装工厂
     * 4. 设置虚拟文件系统
     * 5. 设置typeAliasesPackage进行切割
     * 6. 设置拦截器，及插件
     * 7. 设置typeHandlersPackage
     * 8. 设置typeHandlers
     * 9. 设置数据库id提供方（databaseIdProvider）
     * 10. 设置缓存
     * 11. 设置事务管理
     * 12. 设置环境
     * 13. 扫描xml，构建xml（mapperLocations）
     * @return sqlsession工厂
     * @throws IOException IO异常，资源读取失败
     */
    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
        annotationConfiguration =
            new AnnotationConfiguration(sqlSessionFactory.getConfiguration());

        // 扫描包
        scanPackages(annotationConfiguration);

        try {
            AnnoatationMapperBuilder annoatationMapperBuilder =
                new AnnoatationMapperBuilder(annotationConfiguration);
            annoatationMapperBuilder.parse();
        } catch (Exception e) {
            throw new NestedIOException("error", e);
        } finally {
            ErrorContext.instance().reset();
        }

        return sqlSessionFactory;
    }

    /**
     * 扫描<code>packageToScan</code>包中的所有的实体
     * @param config AnnotationConfiguration
     */
    private void scanPackages(AnnotationConfiguration config) {
        if (this.packagesToScan != null) {
            try {
                for (String pkg : this.packagesToScan) {
                    // 包路径 classpath*: + 包名 + /**/*.class
                    String pattern =
                        ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                            + ClassUtils.convertClassNameToResourcePath(pkg)
                            + RESOURCE_PATTERN;

                    Resource[] resources =
                        this.resourcePatternResolver.getResources(pattern);
                    MetadataReaderFactory readerFactory =
                        new CachingMetadataReaderFactory(this.resourcePatternResolver);
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader reader =
                                readerFactory.getMetadataReader(resource);
                            String className =
                                reader.getClassMetadata().getClassName();
                            if (matchesFilter(reader, readerFactory)) {
                                // 添加class，并且会进行解析的，解析会得到classMaps
                                config.addAnnotatedClass(this.resourcePatternResolver
                                    .getClassLoader()
                                    .loadClass(className));
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                throw new MappingException(
                    "Failed to scan classpath for unlisted classes",
                    ex);
            } catch (ClassNotFoundException ex) {
                throw new MappingException(
                    "Failed to load annotated classes from classpath",
                    ex);
            } catch (Exception ex) {
                throw new MappingException("Failed to addAnnotatedClass", ex);
            }
        }
    }

    public AnnotationConfiguration getAnnotationConfiguration() {
        return annotationConfiguration;
    }

    private boolean matchesFilter(MetadataReader reader,
        MetadataReaderFactory readerFactory) throws IOException {
        if (this.entityTypeFilters != null) {
            for (TypeFilter filter : this.entityTypeFilters) {
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver =
            ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }

}
