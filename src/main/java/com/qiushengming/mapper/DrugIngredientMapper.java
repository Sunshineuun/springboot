package com.qiushengming.mapper;

import com.qiushengming.entity.DrugIngredient;
import com.qiushengming.entity.DrugIngredientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DrugIngredientMapper {
    int countByExample(DrugIngredientExample example);

    int deleteByExample(DrugIngredientExample example);

    int insert(DrugIngredient record);

    int insertSelective(DrugIngredient record);

    List<DrugIngredient> selectByExample(DrugIngredientExample example);

    int updateByExampleSelective(@Param("record") DrugIngredient record, @Param("example") DrugIngredientExample example);

    int updateByExample(@Param("record") DrugIngredient record, @Param("example") DrugIngredientExample example);
}