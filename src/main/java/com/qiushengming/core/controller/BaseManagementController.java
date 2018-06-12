package com.qiushengming.core.controller;

import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.BaseEntity;
import com.qiushengming.entity.code.MinNieResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * @author qiushengming
 * @date 2018/6/8
 */
public abstract class BaseManagementController<T extends BaseEntity>
    extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 返回service，提供DAO层数据操作
     *
     * @return service服务
     */
    protected abstract ManagementService<T> getManagementService();

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public MinNieResponse edit(T object) {
        logger.debug("{}", object);
        int states = getManagementService().update(object);
        return new MinNieResponse(states == 1, "编辑", states);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public MinNieResponse add(T object) {
        logger.debug("{}", object);
        object.setId(generateId());

        int states = getManagementService().add(object);
        return new MinNieResponse(states == 1, "新增", states);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public MinNieResponse del(T object) {
        logger.debug("{}", object);
        int states = getManagementService().delete(object);
        return new MinNieResponse(states == 1, "删除", states);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public MinNieResponse list() {
        return new MinNieResponse(true,
            "success",
            getManagementService().getAll());
    }

    protected String generateId() {
        return UUID.randomUUID().toString();
    }
}
