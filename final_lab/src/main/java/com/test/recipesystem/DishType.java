package com.test.recipesystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DishType {
    private SimpleIntegerProperty id;
    private SimpleStringProperty dishType;
    public DishType(int id, String dishType) {
        this.id = new SimpleIntegerProperty(id);
        this.dishType = new SimpleStringProperty(dishType);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int idin) {
        id.set(idin);
    }

    public String getDishType() {
        return dishType.get();
    }

    public void setDishType(String dishType) {
        this.dishType.set(dishType);
    }

}
