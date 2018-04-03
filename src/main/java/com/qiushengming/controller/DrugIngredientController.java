package com.qiushengming.controller;

import com.qiushengming.entity.DrugIngredient;
import com.qiushengming.service.DrugIngredientService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018年4月3日
 */
@RestController
@RequestMapping("/durgingredient")
public class DrugIngredientController {

    @Resource(name = "drugIngredientService")
    private DrugIngredientService service;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public List<DrugIngredient> findDrugIngredientByLikeName(
        @PathVariable String name) {
        return service.findDrugIngredientByLikeName(name);
    }
}
