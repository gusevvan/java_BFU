package com.test.recipesystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Recipe {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty dishType;
    private SimpleDoubleProperty caloriesPer100g;
    private SimpleStringProperty recipeText;

    public Recipe(int id, String name, String dishType, Double caloriesPer100g, String recipeText) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.dishType = new SimpleStringProperty(dishType);
        this.caloriesPer100g = new SimpleDoubleProperty(caloriesPer100g);
        this.recipeText = new SimpleStringProperty(recipeText);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int idin) {
        id.set(idin);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDishType() {
        return dishType.get();
    }

    public void setDishType(String dishType) {
        this.dishType.set(dishType);
    }

    public Double getCaloriesPer100g() {
        return caloriesPer100g.get();
    }

    public void setCaloriesPer100g(Double caloriesPer100g) {
        this.caloriesPer100g.set(caloriesPer100g);
    }
    
    public String getRecipeText() {
        return recipeText.get();
    }

    public void setRecipeText(String recipeText) {
        this.recipeText.set(recipeText);
    }
}
