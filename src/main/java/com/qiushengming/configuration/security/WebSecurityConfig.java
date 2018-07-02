package com.qiushengming.configuration.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/6/29
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig
    extends WebSecurityConfigurerAdapter {

    @Resource(name = "filterSecurityInterceptor")
    private CustomFilterSecurityInterceptor filterSecurityInterceptor;

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(
        @Qualifier(value = "userDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 授权信息配置
     *
     * @param http {@link HttpSecurity}
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 访问穿入参数的匹配规则，匹配上的地址，无需登录认证权限，都能访问
            .antMatchers("/", "/forward/index", "/loadDictionary").permitAll()
            //其他所有资源都需要认证，登陆后访问
            .anyRequest().authenticated()
            // 构建登陆 登陆页面 允许任何权限访问
            .and().formLogin().loginPage("/login").permitAll()
            //
            .successForwardUrl("/forward/index")
            // 登陆成功跳转的地址
            .defaultSuccessUrl("/forward/index")
            // 登陆成功后的操作，传入方法
            //.successHandler()

            // 构建登出界面，允许任何权限访问
            .and().logout().permitAll().and()
            // 登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
            .rememberMe()
            // 令牌的有效时间，单位秒
            .tokenValiditySeconds(100000);
        http.addFilterBefore(filterSecurityInterceptor,
            FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        /*super.configure(auth);*/
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 全局用户配置，配置内容存储在内存中，硬编码
     *
     * @param auth 认证信息管理构建
     * @throws Exception 异常
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        // 在内存中创建一个用户
        /*auth.inMemoryAuthentication()
            .withUser("qiushengming")
            .password("snshine")
            .roles("USER");*/
        auth.inMemoryAuthentication()
            // 密码加密方式,Spring Security必须要有一个密码加密方式，否则报错。
            .passwordEncoder(new BCryptPasswordEncoder())
            // 配置用户
            .withUser("qiushengming")
            // 配置用户的密码
            .password(new BCryptPasswordEncoder().encode("sunshine"))
            .roles("USER");
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
