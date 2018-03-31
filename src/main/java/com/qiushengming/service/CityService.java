package com.qiushengming.service;

import com.qiushengming.entity.City;

import java.util.List;

/**
 * Created by qiushengming on 2018/3/30.
 */
public interface CityService {
    City findCityById(Long id);

    List<City> findAllCity();

    int saveCity(City city);

    int updateCity(City city);

    int deleteCity(Long id);
}
