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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class DishTypesController implements Stageable {
    private Stage stage;
    private Stageable parent;
    @FXML
    private TextField txtDishType;
    @FXML
    private TableView<DishType> tableDishTypes;

    @FXML
    private TableColumn<Recipe, Integer> idCol;
    
    @FXML
    private TableColumn<Recipe, String> dishTypeCol;

    @Override
    public void setStage(Stage stage, Stageable parant) {
		this.stage = stage;
        this.parent = parent;
	}

    @FXML
    public void onAddButtonClick() throws IOException, SQLException {
        String dishType = txtDishType.getText();
        if (!dishType.isEmpty()) {
            DBAdapter.insertDishType(dishType);
        }
        updateTable();
    }

    @FXML
    public void onUpdateButtonClick() throws IOException, SQLException {
        DishType dishType = tableDishTypes.getSelectionModel().getSelectedItem();
        if (dishType != null && !txtDishType.getText().isEmpty()) {
            DBAdapter.updateDishType(dishType.getId(), txtDishType.getText());
            updateTable();
        }
    }

    @FXML
    public void onDeleteButtonClick() throws IOException, SQLException {
        DishType dishType = tableDishTypes.getSelectionModel().getSelectedItem();
        if (dishType != null) {
            DBAdapter.deleteDishType(dishType.getId());
            updateTable();
        }
    }

    @Override
    public void updateTable() throws IOException, SQLException {
        ArrayList<DishType> data = DBAdapter.selectDishTypes();
        ObservableList<DishType> data_new = FXCollections.observableArrayList(data);
        tableDishTypes.setItems(data_new);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dishTypeCol.setCellValueFactory(new PropertyValueFactory<>("dishType"));

        try {
            updateTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    

}