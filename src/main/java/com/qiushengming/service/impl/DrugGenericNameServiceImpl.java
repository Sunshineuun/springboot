package com.qiushengming.service.impl;

import com.qiushengming.mapper.DrugIngredientMapper;
import com.qiushengming.service.DrugGenericNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qiushengming
 * @date 2018/4/3
 */
@Service(value = "drugGenericNameService")
public class DrugGenericNameServiceImpl
    implements DrugGenericNameService {

    @Autowired
    private DrugIngredientMapper mapper;
}
