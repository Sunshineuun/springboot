package com.qiushengming.configuration.mybatis;

import com.qiushengming.mybatis.annotation.support.AnnotationSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * 多个sqlsession在这里创建
 * mybatis sqlSessionFactory and sqlSessionTemplate创建 <br>
 * {@link org.apache.ibatis.session.Configuration} 参阅 http://www.mybatis.org/mybatis-3/zh/configuration.html<br>
 *
 * @author qiushengming
 * @date 2018/4/14
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisCustomConfiguration {

    private final MybatisProperties properties;

    private final Interceptor[] interceptors;

    private final ResourceLoader resourceLoader;

    private final DatabaseIdProvider databaseIdProvider;

    private final List<ConfigurationCustomizer> configurationCustomizers;

    public MybatisCustomConfiguration(MybatisProperties properties,
                                      ObjectProvider<Interceptor[]> interceptorsProvider,
                                      ResourceLoader resourceLoader,
                                      ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                      ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        this.properties = properties;
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.resourceLoader = resourceLoader;
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
    }

    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        AnnotationSqlSessionFactoryBean bean = new AnnotationSqlSessionFactoryBean();

        // 设置数据源
        bean.setDataSource(dataSource);
        // 用于访问应用程序服务器中的资源， SpringBootVFC主要通过PathMatchingResourcePatternResolver类来获取资源
        bean.setVfs(SpringBootVFS.class);

        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            bean.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        org.apache.ibatis.session.Configuration configuration = this.properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(this.properties.getConfigLocation())) {
            configuration = new org.apache.ibatis.session.Configuration();
        }
        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
                customizer.customize(configuration);
            }
        }
        bean.setConfiguration(configuration);
        if (this.properties.getConfigurationProperties() != null) {
            bean.setConfigurationProperties(this.properties.getConfigurationProperties());
        }

        // 实体所在的路径
        bean.setPackagesToScan(properties.getTypeAliasesPackage());
        // XML所在的路径
        bean.setMapperLocations(properties.resolveMapperLocations());
        // 对应的类型别名
        bean.setTypeAliasesPackage(properties.getTypeAliasesPackage());

        bean.setPlugins(interceptors);

        return bean.getObject();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(
            @Qualifier("dataSource") DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "oracleSessionFactory")
    public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("oracleDB") DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "oracleSqlSessionTemplate")
    public SqlSessionTemplate oracleSqlSessionTemplate(@Qualifier("oracleSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
