package com.test.recipesystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    static Connection con;

    static void init(){
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:sqlite:recipes.sqlite");
                String sql="""
                            CREATE TABLE IF NOT EXISTS 'dish_type' 
                            ('dish_type_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'dish_type_name' TEXT NOT NULL UNIQUE);
                            """;
                completeQuery(sql, "Created table dish_type");

                sql="""
                            CREATE TABLE IF NOT EXISTS 'cuisine_type' 
                            ('cuisine_type_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'cuisine_type_name' TEXT NOT NULL UNIQUE);
                            """;
                completeQuery(sql, "Created table cuisine_type");

                sql="""
                            CREATE TABLE IF NOT EXISTS 'source' 
                            ('source_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'source_name' TEXT NOT NULL UNIQUE);
                            """;
                completeQuery(sql, "Created table source");

                sql="""
                            CREATE TABLE IF NOT EXISTS 'difficulty' 
                            ('difficulty_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'difficulty_name' TEXT NOT NULL UNIQUE);
                            """;
                completeQuery(sql, "Created table difficulty");

                sql="""
                            CREATE TABLE IF NOT EXISTS 'contraindication' 
                            ('contraindication_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'contraindication_name' TEXT NOT NULL UNIQUE);
                            """;
                completeQuery(sql, "Created table contraindication");

                sql="""
                            CREATE TABLE IF NOT EXISTS 'contraindication_recipe' 
                            ('contraindication_id' INTEGER NOT NULL, 'recipe_id' INTEGER NOT NULL);
                            """;
                completeQuery(sql, "Created table contraindication_recipe");
                
                sql = """
                        CREATE TABLE IF NOT EXISTS 'recipes' 
                        ('recipe_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        'recipe_name' TEXT NOT NULL,
                        'dish_type_id' INT,
                        'cuisine_type_id' INT,
                        'source_id' INT,
                        'difficulty_id' INT,
                        'calories_per100g' DECIMAL(6, 2),
                        'recipe_text' TEXT
                        );
                    """;
                completeQuery(sql, "Created table recipes");
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    static void insertSetting(String name, String settingType) throws SQLException {
        String sql = "INSERT INTO " + settingType + "(" + settingType + "_name) VALUES('"+name+"')";
        completeQuery(sql, "Inserted data");
    }

    
    static void updateSetting(Integer id, String name, String settingType) throws SQLException {
        String sql = "UPDATE " + settingType + " SET " + settingType + "_name='"+name+"' WHERE " + settingType + "_id='"+id+"'";
        completeQuery(sql, "Updated data");
    }
    
    static void deleteSetting(Integer id, String settingType) throws SQLException {
        String sql;
        if (settingType != "contraindication") {
            sql = "SELECT * FROM recipes WHERE " + settingType + "_id=" + id;
        } else {
            sql = "SELECT * FROM contraindication_recipe WHERE " + settingType + "_id=" + id;
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            throw new SQLException("Foreign key exists");
        }
        sql = "DELETE FROM " + settingType + " WHERE " + settingType + "_id ='"+id+"'";
        completeQuery(sql, "Deleted data");
    }

    static ArrayList<Setting> selectSettings(String settingType) throws SQLException {
        ArrayList<Setting> settings = new ArrayList<Setting>();

        String sql = "SELECT * FROM " + settingType;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt(settingType + "_id");
            String name = rs.getString(settingType + "_name");
            settings.add(new Setting(id, name));
        }
        return settings;
    }

    static void insertRecipe(String recipeName, String dishType, String cuisineType, String source, String difficulty,
                             Double caloriesPer100g, String recipeText) throws SQLException {
        int dishTypeId = getIdByName(dishType, "dish_type");
        int cuisineTypeId = getIdByName(cuisineType, "cuisine_type");
        int sourceId = getIdByName(source, "source");
        int difficultyId = getIdByName(difficulty, "difficulty");
        String sql = ("INSERT INTO recipes(recipe_name, dish_type_id, cuisine_type_id, source_id, difficulty_id, calories_per100g, recipe_text) VALUES('"
                        +recipeName+"','"+dishTypeId+"','"+cuisineTypeId+"','"+sourceId+"','"+difficultyId+"','"+caloriesPer100g+"','"+recipeText+"')");
        completeQuery(sql, "Inserted data");
    }

    static int getIdByName(String name, String settingType) throws SQLException {
        String sql = "SELECT " + settingType + "_id FROM " + settingType + " WHERE " + settingType + "_name='" + name + "'";
        System.out.println(sql);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(settingType + "_id");
        } else {
            return -1;
        }
    }

    static ArrayList<Recipe> selectRecipes() throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        String sql = """
                         SELECT recipes.recipe_id, recipes.recipe_name, recipes.calories_per100g, dish_type.dish_type_name,
                         cuisine_type.cuisine_type_name, source.source_name, difficulty.difficulty_name,
                         recipes.recipe_text FROM recipes
                         JOIN dish_type ON recipes.dish_type_id = dish_type.dish_type_id
                         JOIN cuisine_type ON recipes.cuisine_type_id = cuisine_type.cuisine_type_id
                         JOIN source ON recipes.source_id = source.source_id
                         JOIN difficulty ON recipes.difficulty_id = difficulty.difficulty_id
                    """;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt("recipe_id");
            String name = rs.getString("recipe_name");
            String dishType = rs.getString("dish_type_name");
            String cuisineType = rs.getString("cuisine_type_name");
            String source = rs.getString("source_name");
            String difficulty = rs.getString("difficulty_name");
            Double caloriesPer100g = rs.getDouble("calories_per100g");
            String recipeText = rs.getString("recipe_text");
            recipes.add(new Recipe(id, name, dishType, cuisineType, source, difficulty, caloriesPer100g, recipeText));
        }
        return recipes;
    }

    static void deleteRecipe(Integer id) throws SQLException {
        String sql = "DELETE FROM recipes WHERE recipe_id='"+id+"'";
        completeQuery(sql, "Deleted data");
    }

    static ArrayList<Setting> selectContraindications(int recipeId) throws SQLException {
        ArrayList<Setting> settings = new ArrayList<Setting>();

        String sql = "SELECT contraindication.contraindication_id, contraindication.contraindication_name FROM contraindication_recipe JOIN contraindication ON contraindication.contraindication_id = contraindication_recipe.contraindication_id WHERE recipe_id=" + recipeId;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt("contraindication_id");
            String name = rs.getString("contraindication_name");
            settings.add(new Setting(id, name));
        }
        return settings;
    }

    static void insertContraindication(String contraindicationName, int recipeId) throws SQLException {
        int contraindicationId = getIdByName(contraindicationName, "contraindication");
        String sql = "INSERT INTO contraindication_recipe(contraindication_id, recipe_id) VALUES(" + contraindicationId + ", " + recipeId +")";
        completeQuery(sql, "Inserted data");
    }
    
    static void deleteContraindication(int contraindicationId, int recipeId) throws SQLException {
        String sql = "DELETE FROM contraindication_recipe WHERE contraindication_id=" + contraindicationId + " AND recipe_id=" + recipeId;
        completeQuery(sql, "Deleted data");
    }
 
    static void completeQuery(String sql, String logMsg) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.execute(sql);
        stmt.close();
        System.out.println(logMsg);
    }
}
