package com.qiushengming.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 数据新增修改的日志记录
 * @author qiushengming
 * @date 2018/4/16
 */
@Aspect
@Component
public class DataLogAspect {
    @Pointcut("execution(public * com.qiushengming.dao.MinnieDaoImpl.*(..))")
    public void add(){}

    @Pointcut("execution(public * com.qiushengming.dao.MinnieDaoImpl.update(..))")
    public void update(){}

    @Pointcut("execution(public * com.qiushengming.dao.MinnieDaoImpl.delete(..))")
    public void delete(){}

    /**
     * 前置通知
     * @param jp jp
     */
    @Before("add()||update()||delete()")
    public void doBefore(JoinPoint jp) {
        Object target = jp.getArgs();
        // 存储target
        String targetStr = target.toString();


        System.out.println("前置通知");
    }
    /**
     * 后置异常通知
     * @param jp jp
     */
    @AfterThrowing("add()||update()||delete()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }
}
