package com.qiushengming.controller;

import com.qiushengming.service.DrugGenericNameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiushengming
 * @date 2018/4/3
 */
@RestController
@RequestMapping(value = "drugGenericName")
public class DrugGenericNameController {

    @Resource(name = "drugGenericNameService")
    private DrugGenericNameService service;
}
