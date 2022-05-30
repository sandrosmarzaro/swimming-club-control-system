package com.sccs.controller;

import com.sccs.App;
import com.sccs.model.dao.EmployeeDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginControl implements Initializable {
    
    @FXML
    TextField loginLabel;
    @FXML
    PasswordField passwordField;
    @FXML
    Button signInButton;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmployeeDAO.setConnection(connection);
    }
    
    @FXML
    public void handleClickSignIn() throws IOException {
        String login = loginLabel.getText();
        String password = passwordField.getText();
        Boolean isCorrectSignIn = employeeDAO.signIn(login, password);
        if (isCorrectSignIn) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Logging In");
            alert.setHeaderText("Wrong Login or Password!");
            alert.setContentText("Check the texts entered in the fields and try again...");
            alert.showAndWait();
        }
        else {
            App.setRoot("view/AppView");
        }
    }
    
}
