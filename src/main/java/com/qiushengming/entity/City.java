package com.qiushengming.entity;

import com.qiushengming.annotation.Column;
import com.qiushengming.annotation.Id;
import com.qiushengming.annotation.Table;


/**
 * @author MinMin
 */
@Table(value = "QSM_CITY", resultMapId = "City")
public class City {
    private String id;
    private String cityName;

    @Id(value = "ID")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(value = "CITY_NAME")
    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
