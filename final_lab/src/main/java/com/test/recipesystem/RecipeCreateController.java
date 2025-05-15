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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class RecipeCreateController implements Initializable {
    private RecipesController parent;
    private Map<String, ComboBox<String>> comboBoxes;

    @FXML
    private TextField txtName;
    
    @FXML
    private ComboBox<String> dishTypesComboBox;

    @FXML
    private ComboBox<String> cuisineTypesComboBox;

    @FXML
    private ComboBox<String> sourcesComboBox;

    @FXML
    private ComboBox<String> difficultiesComboBox;

    @FXML
    private TextField txtCalories;

    @FXML
    private TextArea recipeTextArea;

    @FXML
    private Button okButton;

    public void setParent(RecipesController parent) {
        this.parent = parent;
	}

    public void onOkButtonClick() throws IOException, SQLException {
        if (!txtName.getText().isEmpty() && dishTypesComboBox.getValue() != null && cuisineTypesComboBox.getValue() != null && difficultiesComboBox.getValue() != null &&
                sourcesComboBox.getValue() != null && !txtCalories.getText().isEmpty() && !recipeTextArea.getText().isEmpty()) {
            DBAdapter.insertRecipe(txtName.getText(), dishTypesComboBox.getValue(), cuisineTypesComboBox.getValue(), sourcesComboBox.getValue(), 
                                    difficultiesComboBox.getValue(), Double.valueOf(txtCalories.getText()), recipeTextArea.getText());
            Stage stage = (Stage)okButton.getScene().getWindow();
            stage.close();
            parent.updateTable();
        }
    }

    private void fillComboBoxes() throws IOException, SQLException {
        for (String settingType: new ArrayList<String>(Arrays.asList("dish_type", "cuisine_type", "source", "difficulty"))) {
            ObservableList<String> settings = FXCollections.observableArrayList();
            for (Setting setting : DBAdapter.selectSettings(settingType)) {
                settings.add(setting.getName());
            }
            comboBoxes.get(settingType).setItems(settings);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        comboBoxes = Map.of("dish_type", dishTypesComboBox, "cuisine_type", cuisineTypesComboBox, "difficulty", difficultiesComboBox,
                            "source", sourcesComboBox);

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
