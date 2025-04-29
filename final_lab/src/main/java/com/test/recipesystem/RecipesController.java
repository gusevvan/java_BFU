package com.test.recipesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class RecipesController implements Stageable {
    private Stage stage;
    private Stageable parent;

    @FXML
    private TableView<Recipe> tableRecipes;

    @FXML
    private TableColumn<Recipe, Integer> idCol;
    
    @FXML
    private TableColumn<Recipe, String> nameCol;
    
    @FXML
    private TableColumn<Recipe, String> dishTypeCol;
    
    @FXML
    private TableColumn<Recipe, Double> caloriesPer100gCol;
    
    @FXML
    private TableColumn<Recipe, String> recipeTextCol;

    @FXML
    public void handleDelete() throws IOException, SQLException {
        Recipe recipe = tableRecipes.getSelectionModel().getSelectedItem();
        DBAdapter.deleteRecipe(recipe.getId());
        updateTable();
    }

    @FXML
    public void onRecipeCreateButtonClick() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RecipeSystem.class.getResource("recipe_create-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        Stageable controller = fxmlLoader.getController();
        controller.setStage(newStage, (Stageable)this);
        newStage.setTitle("Recipe creation");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    public void onDishTypesButtonClick() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RecipeSystem.class.getResource("dish_types-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        newStage.setTitle("Dish types settings");
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void setStage(Stage stage, Stageable parent) {
        this.stage = stage;
        tableRecipes.prefWidthProperty().bind(this.stage.widthProperty());
        tableRecipes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.parent = parent;
    }

    @Override
    public void updateTable() throws IOException, SQLException {
        ArrayList<Recipe> data = DBAdapter.selectRecipes();
        ObservableList<Recipe> data_new = FXCollections.observableArrayList(data);
        tableRecipes.setItems(data_new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dishTypeCol.setCellValueFactory(new PropertyValueFactory<>("dishType"));
        caloriesPer100gCol.setCellValueFactory(new PropertyValueFactory<>("caloriesPer100g"));
        recipeTextCol.setCellValueFactory(new PropertyValueFactory<>("recipeText"));


        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}