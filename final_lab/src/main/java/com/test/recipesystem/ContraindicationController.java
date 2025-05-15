package com.test.recipesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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

public class ContraindicationController implements Initializable {
    private int recipeId;
    private Map<String, String> settingTypeLocalizations;

    @FXML
    private ComboBox<String> contraindicationComboBox;

    @FXML
    private TableView<Setting> tableContraindication;

    @FXML
    private TableColumn<String, String> contraindicationCol;

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
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
        if (contraindicationComboBox.getValue() != null) {
            DBAdapter.insertContraindication(contraindicationComboBox.getValue(), recipeId);
            updateTable();
        }
    }

    @FXML
    public void onDeleteButtonClick() throws IOException, SQLException {
        Setting setting = tableContraindication.getSelectionModel().getSelectedItem();
        if (setting != null) {
            DBAdapter.deleteContraindication(setting.getId(), recipeId);
            updateTable();
        }
    }

    public void updateTable() throws IOException, SQLException {
        ArrayList<Setting> data = DBAdapter.selectContraindications(recipeId);
        ObservableList<Setting> data_new = FXCollections.observableArrayList(data);
        tableContraindication.setItems(data_new);
        fillComboBoxes();
    }

    private void fillComboBoxes() throws SQLException {
        contraindicationComboBox.getItems().clear();
        ObservableList<String> contraindications = FXCollections.observableArrayList();
        ArrayList<String> recipeContraindications = new ArrayList<String>();
        for (Setting setting: DBAdapter.selectContraindications(recipeId)) {
            recipeContraindications.add(setting.getName());
        }
        ArrayList<String> allContraindications = new ArrayList<String>();
        for (Setting setting: DBAdapter.selectSettings("contraindication")) {
            allContraindications.add(setting.getName());
        }
        for (String contraindication: allContraindications) {
            if (!recipeContraindications.contains(contraindication)) {
                contraindications.add(contraindication);
            }
        }
        contraindicationComboBox.setItems(contraindications);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contraindicationCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    

}