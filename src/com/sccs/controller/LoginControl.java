package com.sccs.controller;

import com.sccs.App;
import com.sccs.model.dao.EmployeeDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Employee;
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
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    private static Employee employee = new Employee();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmployeeDAO.setConnection(connection);
    }
    
    private static void setEmployee(Employee searchEmployee) {
        employee = searchEmployee;
    }
    
    public static Employee getLoginEmployee() {
        return employee;
    }
    
    @FXML
    public void handleClickSignIn() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        Boolean isCorrectSignIn = EmployeeDAO.signIn(login, password);
        if (!isCorrectSignIn) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Logging In");
            alert.setHeaderText("Wrong Login or Password!");
            alert.setContentText("Check the texts entered in the fields and try again...");
            alert.showAndWait();
        }
        else {
            setEmployee(EmployeeDAO.loginSearch(login));
            App.setRoot("view/AppView");
        }
    }
    
}
