package com.qiushengming.entity;

public class DrugGenericName extends BaseEntity {
    private String name;

    private String ingredientId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId == null ? null : ingredientId.trim();
    }
}
