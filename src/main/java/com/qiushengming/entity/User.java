package com.qiushengming.entity;

import com.qiushengming.annotation.Table;

/**
 * @author MinMin
 */
@Table(value = "MM_USER", resultMapId = "User")
public class User extends BaseEntity {

    private String name;

    private String sex;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
