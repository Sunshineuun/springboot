package com.qiushengming.mapper;

import com.qiushengming.entity.DrugGenericName;
import com.qiushengming.entity.DrugGenericNameExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrugGenericNameMapper {
    int countByExample(DrugGenericNameExample example);

    int deleteByExample(DrugGenericNameExample example);

    int deleteByPrimaryKey(String id);

    int insert(DrugGenericName record);

    int insertSelective(DrugGenericName record);

    List<DrugGenericName> selectByExample(DrugGenericNameExample example);

    DrugGenericName selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DrugGenericName record, @Param("example") DrugGenericNameExample example);

    int updateByExample(@Param("record") DrugGenericName record, @Param("example") DrugGenericNameExample example);

    int updateByPrimaryKeySelective(DrugGenericName record);

    int updateByPrimaryKey(DrugGenericName record);
}
