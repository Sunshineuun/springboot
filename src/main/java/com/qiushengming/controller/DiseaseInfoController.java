package com.qiushengming.controller;

import com.qiushengming.core.controller.BaseManagementController;
import com.qiushengming.core.service.ManagementService;
import com.qiushengming.entity.DiseaseInfo;
import com.qiushengming.service.DiseaseInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/search/{field}/{value}")
    public List<Map<String, String>> serarch(
            @PathVariable String field,
            @PathVariable String value) {
        return diseaseInfoService.searchLucene(field, value);
    }

    @RequestMapping(value = "/delLuceneAll")
    public void delLuceneAll() {
        diseaseInfoService.deleteLuceneAll();
    }
}
