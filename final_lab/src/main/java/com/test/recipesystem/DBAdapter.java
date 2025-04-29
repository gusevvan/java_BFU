package com.test.recipesystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    static Connection con;

    static void init(){
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:sqlite:phonebook.sqlite");
                Statement stmt = con.createStatement();
                String sql="""
                            CREATE TABLE IF NOT EXISTS 'dish_types' 
                            ('dish_type_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'dish_name' TEXT NOT NULL);
                            CREATE TABLE IF NOT EXISTS 'recipes' 
                                ('recipe_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                                'recipe_name' TEXT NOT NULL,
                                'dish_type_id' INT,
                                'calories_per100g' DECIMAL(6, 2),
                                'recipe_text' TEXT
                                );
                            """;
                stmt.execute(sql);
                System.out.println("Table created");
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    static void insertDishType(String dishType) throws SQLException {
        if (con == null) {
            // throw;
        }
        String sql = "INSERT INTO dish_types(dish_name) VALUES('"+dishType+"')";
        Statement stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        System.out.println("Inserted data");
    }

    static ArrayList<DishType> selectDishTypes() throws SQLException {
        ArrayList<DishType> dishTypes = new ArrayList<DishType>();

        String sql = "SELECT *  FROM dish_types";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt("dish_type_id");
            String dishType = rs.getString("dish_name");
            dishTypes.add(new DishType(id, dishType));
        }
        return dishTypes;
    }

    static void updateDishType(Integer id, String dishType) throws SQLException {
        String sql = "UPDATE dish_types SET dish_name='"+dishType+"' WHERE dish_type_id='"+id+"'";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Updated data");
    }

    static void deleteDishType(Integer id) throws SQLException {
        String sql = "DELETE FROM dish_types WHERE dish_type_id='"+id+"'";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Deleted data");
    }

    static void insertRecipe(String recipeName, int dishTypeId, Double caloriesPer100g, String recipeText) throws SQLException {
        if (con == null) {
            // throw;
        }
        String sql = "INSERT INTO recipes(recipe_name, dish_type_id, calories_per100g, recipe_text) VALUES('"+recipeName+"','"+
                        dishTypeId+"','"+caloriesPer100g+"','"+recipeText+"')";
        Statement stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        System.out.println("Inserted data");
    }

    static ArrayList<Recipe> selectRecipes() throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        String sql = """
                         SELECT recipes.recipe_id, recipes.recipe_name, recipes.calories_per100g, dish_types.dish_name,
                         recipes.recipe_text FROM recipes
                         JOIN dish_types ON recipes.dish_type_id = dish_types.dish_type_id
                    """;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt("recipe_id");
            String name = rs.getString("recipe_name");
            String dishType = rs.getString("dish_name");
            Double caloriesPer100g = rs.getDouble("calories_per100g");
            String recipeText = rs.getString("recipe_text");
            recipes.add(new Recipe(id, name, dishType, caloriesPer100g, recipeText));
        }
        return recipes;
    }

    static void deleteRecipe(Integer id) throws SQLException {
        String sql = "DELETE FROM recipes WHERE recipe_id='"+id+"'";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        System.out.println("Deleted data");
    }
}
