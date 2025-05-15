package com.test.recipesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map;

public class SettingController implements Initializable {
    private String settingType;
    private RecipesController parent;
    private Map<String, String> settingTypeLocalizations;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField txtName;
    @FXML
    private TableView<Setting> tableSetting;

    @FXML
    private TableColumn<Recipe, Integer> idCol;
    
    @FXML
    private TableColumn<Recipe, String> nameCol;

    public void setParent(RecipesController parent) {
        this.parent = parent;
    }

    public void setType(String type) {
        this.settingType = type;
        nameCol.setText(settingTypeLocalizations.get(type));
        nameLabel.setText(settingTypeLocalizations.get(type));
        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onAddButtonClick() throws IOException, SQLException {
        String name = txtName.getText();
        if (!name.isEmpty()) {
            try {
                DBAdapter.insertSetting(name, settingType);
                updateTable();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(settingTypeLocalizations.get(settingType) + " с таким названием уже существует");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onUpdateButtonClick() throws IOException, SQLException {
        Setting setting = tableSetting.getSelectionModel().getSelectedItem();
        if (setting != null && !txtName.getText().isEmpty()) {
            try {
                DBAdapter.updateSetting(setting.getId(), txtName.getText(), settingType);
                updateTable();
                parent.updateTable();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(settingTypeLocalizations.get(settingType) + " с таким названием уже существует");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onDeleteButtonClick() throws IOException, SQLException {
        Setting setting = tableSetting.getSelectionModel().getSelectedItem();
        if (setting != null) {
            try {
                DBAdapter.deleteSetting(setting.getId(), settingType);
                updateTable();
            } catch (SQLException e) {
                if (e.getMessage().equals("Foreign key exists")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Нельзя удалить свойство, так как оно используется в рецептах.");
                    alert.showAndWait();
                } else {
                    throw e;
                }
            }
        }
    }

    public void updateTable() throws IOException, SQLException {
        ArrayList<Setting> data = DBAdapter.selectSettings(settingType);
        ObservableList<Setting> data_new = FXCollections.observableArrayList(data);
        tableSetting.setItems(data_new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingTypeLocalizations = Map.of("dish_type", "Тип блюда", "cuisine_type", "Тип кухни", "source", "Источник поступления",
                                          "difficulty", "Сложность", "contraindication", "Противопоказание");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    

}