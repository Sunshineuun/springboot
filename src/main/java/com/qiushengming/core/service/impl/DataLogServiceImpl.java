package com.qiushengming.core.service.impl;

import com.qiushengming.core.service.DataLogService;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.entity.DataLog;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/6/12
 */
@Service("dataLogService")
public class DataLogServiceImpl
    extends AbstractManagementService<DataLog>
    implements DataLogService {

    /**
     * 从{@link JoinPoint}提取参数
     *
     * @param jp   {@link JoinPoint}
     * @param type 类型，在那个切面节点触发的
     * @param ip   ip地址
     * @return true|false
     */
    @Override
    public int add(JoinPoint jp, String type, String ip) {
        Object[] target = jp.getArgs();
        // 存储被处理的对象
        BaseEntity entity = (BaseEntity) target[0];

        if (entity.getClass().equals(DataLog.class)) {
            return 3;
        }

        DataLog log = new DataLog(entity.toString(),
            entity.getId(),
            entity.getClass(),
            "admin",
            type,
            ip,
            jp.getSignature().getName());
        return add(log);
    }
}
