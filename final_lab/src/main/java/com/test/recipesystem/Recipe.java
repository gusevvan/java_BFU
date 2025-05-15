package com.test.recipesystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Recipe {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty dishType;
    private SimpleStringProperty cuisineType;
    private SimpleStringProperty source;
    private SimpleStringProperty difficulty;
    private SimpleDoubleProperty caloriesPer100g;
    private SimpleStringProperty recipeText;

    public Recipe(int id, String name, String dishType, String cuisineType, String source, 
                    String difficulty, Double caloriesPer100g, String recipeText) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.dishType = new SimpleStringProperty(dishType);
        this.cuisineType = new SimpleStringProperty(cuisineType);
        this.source = new SimpleStringProperty(source);
        this.difficulty = new SimpleStringProperty(difficulty);
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

    public String getCuisineType() {
        return cuisineType.get();
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType.set(cuisineType);
    }

    public String getSource() {
        return source.get();
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
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
