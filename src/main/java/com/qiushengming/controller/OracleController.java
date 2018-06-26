package com.qiushengming.controller;

import com.qiushengming.core.controller.BaseManagementController;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.OracleTable;
import com.qiushengming.service.OracleTableService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/6/26
 */
@RestController
@RequestMapping("oracle")
public class OracleController extends BaseManagementController<OracleTable> {

    @Resource(name = "oracleTableService")
    private OracleTableService oracleTableService;
    /**
     * 返回service，提供DAO层数据操作
     *
     * @return service服务
     */
    @Override
    protected ManagementService<OracleTable> getManagementService() {
        return oracleTableService;
    }
}
