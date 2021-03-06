package com.qiushengming.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 1.@PropertySource(value = "classpath:druid.properties")
 * 1. propertysource可以将配置文件注入进来
 * 多个数据源在这里创建
 * @author qiushengming
 * @date 2018/3/30
 */
@Configuration
public class DruidConfiguration {

    private static final Logger logger =
            LoggerFactory.getLogger(DruidConfiguration.class);

    private static final String DB_PREFIX = "spring.datasource";
    private static final String ORACLE_PREFIX = "oralce.datasource";

    /**
     * druid白名单
     */
    @Value("${minnie.druid.allow}")
    private String allow = "127.0.0.1";

    /**
     * druid黑名单
     */
    @Value("${minnie.druid.deny}")
    private String deny = "";

    /**
     * druid登陆用户
     */
    @Value("${minnie.druid.loginUserName}")
    private String loginUsername = "admin";

    /**
     * druid登陆密码
     */
    @Value("${minnie.druid.loginPassword}")
    private String loginPassword = "admin";

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        logger.info("初始化Druid Servlet配置！");

        ServletRegistrationBean<StatViewServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", allow);
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", deny);
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername",
                loginUsername);
        servletRegistrationBean.addInitParameter("loginPassword",
                loginPassword);
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean =
                new FilterRegistrationBean<>(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions",
                "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
     */
    @ConfigurationProperties(prefix = DB_PREFIX)
    class CustomDataSourceProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String filters;
        private String connectionProperties;

        @Primary  //@Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑
        @Bean(destroyMethod = "close", initMethod = "init")     //声明其为Bean实例
        public DataSource dataSource() {
            return createDataSource(this);
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public int getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(int initialSize) {
            this.initialSize = initialSize;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }

        public int getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public void setTimeBetweenEvictionRunsMillis(
                int timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        public int getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(
                int minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public String getValidationQuery() {
            return validationQuery;
        }

        public void setValidationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
        }

        public boolean isTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public boolean isPoolPreparedStatements() {
            return poolPreparedStatements;
        }

        public void setPoolPreparedStatements(boolean poolPreparedStatements) {
            this.poolPreparedStatements = poolPreparedStatements;
        }

        public int getMaxPoolPreparedStatementPerConnectionSize() {
            return maxPoolPreparedStatementPerConnectionSize;
        }

        public void setMaxPoolPreparedStatementPerConnectionSize(
                int maxPoolPreparedStatementPerConnectionSize) {
            this.maxPoolPreparedStatementPerConnectionSize =
                    maxPoolPreparedStatementPerConnectionSize;
        }

        public String getFilters() {
            return filters;
        }

        public void setFilters(String filters) {
            this.filters = filters;
        }

        public String getConnectionProperties() {
            return connectionProperties;
        }

        public void setConnectionProperties(String connectionProperties) {
            this.connectionProperties = connectionProperties;
        }
    }

    @ConfigurationProperties(prefix = ORACLE_PREFIX)
    class OracleDataSourceProperties extends CustomDataSourceProperties{

        @Bean(destroyMethod = "close", initMethod = "init")
        public DataSource oracleDB() {
            return createDataSource(this);
        }
    }

    /**
     * 根据配置类创建数据源
     * @param properties 配置
     * @return DataSource
     */
    private static DataSource createDataSource(CustomDataSourceProperties properties){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(properties.url);
        datasource.setUsername(properties.username);
        datasource.setPassword(properties.password);
        datasource.setDriverClassName(properties.driverClassName);

        //configuration
        datasource.setInitialSize(properties.initialSize);
        datasource.setMinIdle(properties.minIdle);
        datasource.setMaxActive(properties.maxActive);
        datasource.setMaxWait(properties.maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(
            properties.timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(properties.minEvictableIdleTimeMillis);
        datasource.setValidationQuery(properties.validationQuery);
        datasource.setTestWhileIdle(properties.testWhileIdle);
        datasource.setTestOnBorrow(properties.testOnBorrow);
        datasource.setTestOnReturn(properties.testOnReturn);
        datasource.setPoolPreparedStatements(properties.poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(
            properties.maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(properties.filters);
        } catch (SQLException e) {
            System.err.println(
                "druid configuration initialization filter: " + e);
        }
        datasource.setConnectionProperties(properties.connectionProperties);
        return datasource;
    }

}
