package com.qiushengming.aop;

import com.qiushengming.core.controller.BaseController;
import com.qiushengming.core.service.DataLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 数据新增修改的日志记录
 *
 * @author qiushengming
 * @date 2018/4/16
 */
@Aspect
@Component
public class DataLogAspect extends BaseController{

    @Resource(name = "dataLogService")
    private DataLogService dataLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.qiushengming.dao.MinnieDaoImpl.add(..))")
    public void add() {
    }

    @Pointcut(
        "execution(public * com.qiushengming.dao.MinnieDaoImpl.update(..))")
    public void update() {
    }

    @Pointcut(
        "execution(public * com.qiushengming.dao.MinnieDaoImpl.delete(..))")
    public void delete() {
    }

    /**
     * 前置通知
     *
     * @param jp jp
     */
    @Before("add()||update()||delete()")
    public void doBefore(JoinPoint jp) {
        dataLogService.add(jp, "BEFORE", getIpAddr());
    }

    /**
     * 后置异常通知
     *
     * @param jp jp
     */
    @AfterThrowing("add()||update()||delete()")
    public void throwss(JoinPoint jp) {
        dataLogService.add(jp, "THROWS", getIpAddr());
    }
}
