package com.qiushengming.configuration;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 提供mapper接口扫描
 * @author MinMin
 * AutoConfigureAfter注解因为MyBatisMapperScannerConfig的扫描，
 * 需要在MyBatisConfig的后面注入，所以加上下面的注解
 */
@Configuration
@AutoConfigureAfter(MybatisTransactionConfig.class)
public class MyBatisMapperScannerConfig {

    @Value("${minnie.mybatis.basePackage}")
    private String basePackage;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer sca = new MapperScannerConfigurer();
        //获取之前注入的beanName为sqlSessionFactory的对象
        sca.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //mapper接口的路径
        sca.setBasePackage("com.qiushengming.mapper");
        return sca;
    }
}
