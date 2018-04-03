package com.qiushengming.service.impl;

import com.qiushengming.entity.DrugIngredient;
import com.qiushengming.entity.DrugIngredientExample;
import com.qiushengming.mapper.DrugIngredientMapper;
import com.qiushengming.service.DrugIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018年4月3日
 */
@Service(value = "drugIngredientService")
@CacheConfig(cacheNames = "drugIngredient")
public class DrugIngredientServiceImpl
    implements DrugIngredientService {

    @Autowired
    private DrugIngredientMapper mapper;

    @Override
    @Cacheable(value = "drugIngredient", key = "#name")
    public DrugIngredient findDrugIngredientByName(String name) {
        DrugIngredientExample ex = new DrugIngredientExample();
        ex.createCriteria().andNameEqualTo(name);
        return mapper.selectByExample(ex).get(0);
    }

    @Override
    @Cacheable(value = "drugIngredient", key = "#name")
    public List<DrugIngredient> findDrugIngredientByLikeName(String name) {
        DrugIngredientExample ex = new DrugIngredientExample();
        ex.createCriteria().andNameLike(name + "%");
        return mapper.selectByExample(ex);
    }
}
