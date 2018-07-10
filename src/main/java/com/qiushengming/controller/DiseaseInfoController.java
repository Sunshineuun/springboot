package com.qiushengming.controller;

import com.qiushengming.core.controller.BaseManagementController;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.DiseaseInfo;
import com.qiushengming.service.DiseaseInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/7/6
 */
@RestController
@RequestMapping("/diseaseInfo")
public class DiseaseInfoController
    extends BaseManagementController<DiseaseInfo> {
    @Resource(name = "diseaseInfoService")
    private DiseaseInfoService diseaseInfoService;

    @Override
    protected ManagementService<DiseaseInfo> getManagementService() {
        return diseaseInfoService;
    }
}
