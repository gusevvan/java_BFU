package com.test.recipesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class RecipeCreateController implements Stageable {
    private Stage stage;
    private Stageable parent;

    @FXML
    private TextField txtName;
    
    @FXML
    private ComboBox<String> dishTypesComboBox;

    @FXML
    private TextField txtCalories;

    @FXML
    private TextArea recipeTextArea;

    @FXML
    private Button okButton;

    @Override
    public void setStage(Stage stage, Stageable parent) {
		this.stage = stage;
        this.parent = parent;
	}

    public void onOkButtonClick() throws IOException, SQLException {
        if (!txtName.getText().isEmpty() && dishTypesComboBox.getValue() != null &&
                !txtCalories.getText().isEmpty() && !recipeTextArea.getText().isEmpty()) {
            DBAdapter.insertRecipe(txtName.getText(), Integer.valueOf(dishTypesComboBox.getValue().split("\\.")[0]), 
                                    Double.valueOf(txtCalories.getText()), recipeTextArea.getText());
            Stage stage = (Stage)okButton.getScene().getWindow();
            stage.close();
            parent.updateTable();
        }
    }

    private void fillComboBoxes() throws IOException, SQLException {
        ObservableList<String> dishTypes = FXCollections.observableArrayList();
        for (DishType dishType : DBAdapter.selectDishTypes()) {
            dishTypes.add(dishType.getId() + ". " + dishType.getDishType());
        }
        dishTypesComboBox.setItems(dishTypes);
    }

    @Override 
    public void updateTable() throws IOException, SQLException {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        TextFormatter<Double> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*([.,]\\d*)?")) { 
                return change;
            }
            return null;
        });
        txtCalories.setTextFormatter(formatter);
        try {
            fillComboBoxes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
