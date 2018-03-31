package com.qiushengming.entity;

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

    public Integer getAge() {
        return Integer.valueOf(age);
    }

    public void setAge(Integer age) {
        this.age = age.toString();
    }
}
