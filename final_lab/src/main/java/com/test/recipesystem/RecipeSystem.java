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
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Recipes book!");
        stage.setScene(scene);
        stage.show();
        Stageable controller = fxmlLoader.getController();
        controller.setStage(stage, null);
    }

    public static void main(String[] args) {
        launch();
    }
}