package com.qiushengming.core.service;

import com.qiushengming.entity.DataLog;
import org.aspectj.lang.JoinPoint;

/**
 * @author qiushengming
 * @date 2018/6/12
 */
public interface DataLogService
    extends ManagementService<DataLog> {

    /**
     * 从{@link JoinPoint}提取参数
     *
     * @param jp   {@link JoinPoint}
     * @param type 类型，在那个切面节点触发的
     * @param ip ip地址
     * @return true|false
     */
    int add(JoinPoint jp, String type, String ip);
}

