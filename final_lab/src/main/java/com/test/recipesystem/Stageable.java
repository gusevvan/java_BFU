package com.test.recipesystem;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.Initializable;

public interface Stageable extends Initializable {
	
	void setStage(Stage stage, Stageable parent);

	void updateTable() throws IOException, SQLException;
}