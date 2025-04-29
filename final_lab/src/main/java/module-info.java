module com.test.recipesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.test.recipesystem to javafx.fxml;
    exports com.test.recipesystem;
}