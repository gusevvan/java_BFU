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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class RecipesController implements Initializable {
    private Stage stage;
    private Map<String, String> settingTypeLocalizations;

    @FXML
    private TableView<Recipe> tableRecipes;

    @FXML
    private TableColumn<Recipe, Integer> idCol;
    
    @FXML
    private TableColumn<Recipe, String> nameCol;
    
    @FXML
    private TableColumn<Recipe, String> dishTypeCol;

    @FXML
    private TableColumn<Recipe, String> cuisineTypeCol;

    @FXML
    private TableColumn<Recipe, String> sourceCol;

    @FXML
    private TableColumn<Recipe, String> difficultyCol;
    
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
    public void handleContraindication() throws IOException, SQLException {
        Recipe recipe = tableRecipes.getSelectionModel().getSelectedItem();
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RecipeSystem.class.getResource("contraindication-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        ContraindicationController controller = fxmlLoader.getController();
        controller.setRecipeId(recipe.getId());
        newStage.setTitle("Противопоказания " + recipe.getName());
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    public void onRecipeCreateButtonClick() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RecipeSystem.class.getResource("recipe_create-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        RecipeCreateController controller = fxmlLoader.getController();
        controller.setParent(this);
        newStage.setTitle("Создание рецепта");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    public void onDishTypesButtonClick() throws IOException {
        onSettingButtonClick("dish_type");
    }

    @FXML
    public void onCuisineTypesButtonClick() throws IOException {
        onSettingButtonClick("cuisine_type");
    }

    @FXML
    public void onSourcesButtonClick() throws IOException {
        onSettingButtonClick("source");
    }

    @FXML
    public void onDifficultiesButtonClick() throws IOException {
        onSettingButtonClick("difficulty");
    }

    @FXML
    public void onContraindicationsButtonClick() throws IOException {
        onSettingButtonClick("contraindication");
    }

    public void onSettingButtonClick(String settingType) throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(RecipeSystem.class.getResource("setting-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        newStage.setTitle(settingTypeLocalizations.get(settingType));
        newStage.setScene(scene);
        newStage.show();
        SettingController controller = fxmlLoader.getController();
        controller.setType(settingType);
        controller.setParent(this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        tableRecipes.prefWidthProperty().bind(this.stage.widthProperty());
        tableRecipes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void updateTable() throws IOException, SQLException {
        ArrayList<Recipe> data = DBAdapter.selectRecipes();
        ObservableList<Recipe> data_new = FXCollections.observableArrayList(data);
        tableRecipes.setItems(data_new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingTypeLocalizations = Map.of("dish_type", "Типы блюд", "cuisine_type", "Типы кухонь", "source", "Источники поступления",
                                          "difficulty", "Сложности", "contraindication", "Противопоказания");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dishTypeCol.setCellValueFactory(new PropertyValueFactory<>("dishType"));
        cuisineTypeCol.setCellValueFactory(new PropertyValueFactory<>("cuisineType"));
        sourceCol.setCellValueFactory(new PropertyValueFactory<>("source"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
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