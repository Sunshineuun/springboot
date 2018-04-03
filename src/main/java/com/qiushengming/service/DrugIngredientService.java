package com.qiushengming.service;

import com.qiushengming.entity.DrugIngredient;

import java.util.List;

/**
 * @author qiushengming
 * @date 2018年4月3日
 */
public interface DrugIngredientService {
    DrugIngredient findDrugIngredientByName(String name);

    List<DrugIngredient> findDrugIngredientByLikeName(String likeName);
}
