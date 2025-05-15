package com.test.recipesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class RecipeSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DBAdapter.init();
        FXMLLoader fxmlLoader = new FXMLLoader(RecipeSystem.class.getResource("recipes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 500);
        stage.setTitle("Книга рецептов!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        RecipesController controller = fxmlLoader.getController();
        controller.setStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}