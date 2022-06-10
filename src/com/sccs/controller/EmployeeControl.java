package com.sccs.controller;

import com.sccs.model.dao.EmployeeDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Employee;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeControl implements Initializable {
    
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, Integer> columnId;
    @FXML
    private TableColumn<Employee, String> columnCpf;
    @FXML
    private TableColumn<Employee, String> columnName;
    @FXML
    private TextField cpfText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField loginText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button addButton;
    
    private ObservableList<Employee> observableList;
    private List<Employee> employeeList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        EmployeeDAO.setConnection(connection);
        loadTableView();
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectEmployee(newValue));
    }

    @FXML
    public void loadTableView() {
        
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        employeeList = EmployeeDAO.list();
        observableList = FXCollections.observableArrayList(employeeList);
        tableView.setItems(observableList);
    }
    
    @FXML
    public void selectEmployee(Employee employee) {
        
        if (employee != null) {
            cpfText.setText(employee.getCpf());
            nameText.setText(employee.getName());
            loginText.setText(employee.getLogin());
            passwordText.setText("");
        }
        else {
            cpfText.setText("");
            nameText.setText("");
            loginText.setText("");
            passwordText.setText("");
        }
    }
    
    @FXML
    public void noSelectedItemAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Employee not selected!");
        alert.setContentText("Choose a valid employee from the list...");
        alert.showAndWait();
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (
            nameText.getText().isEmpty() || 
            nameText.getText() == null || 
            nameText.getText().length() > 45
        ) {
            errorMessage += "Invalid Name\n";
        }
        if (
            cpfText.getText().isEmpty() ||
            cpfText.getText() == null ||
            !Pattern.matches("^[0-9]{11}$", cpfText.getText())
        ) {
            errorMessage += "Invalid CPF\n";
        }
        if (
            loginText.getText().isEmpty() || 
            loginText.getText() == null ||
            loginText.getText().length() > 45 
        ) {
            errorMessage += "Invalid Login\n";
        }
        if (passwordText.getText().isEmpty() || passwordText.getText() == null) {
            errorMessage += "Invalid Password\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding");
            alert.setHeaderText("Invalid Fields!");
            alert.setContentText("Enter all required fields to add an employee...\n" + errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void hadleClickAdd() {
        
        if (isValidInput()) {
            Employee employee = new Employee(
                cpfText.getText(),
                nameText.getText(),
                loginText.getText(),
                passwordText.getText()
            );
            EmployeeDAO.insert(employee);
            loadTableView();
        }
    }
    
    @FXML
    public void handleClickUpdate() {
        
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (isValidInput()) {
            if (selectedEmployee != null) {
                Employee employee = new Employee(
                    selectedEmployee.getId(),
                    cpfText.getText(),
                    nameText.getText(),
                    loginText.getText(),
                    passwordText.getText()
                );
                EmployeeDAO.update(employee);
                loadTableView();
            }
            else {
                
            }
        }
    }
    
    @FXML 
    public void handleClickDelete() {
        
        Employee employee = tableView.getSelectionModel().getSelectedItem();
        if (employee != null) {
            try {
                EmployeeDAO.delete(employee);
                loadTableView();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Deleting");
                alert.setHeaderText("Employee with records!");
                alert.setContentText("Delete enrollments made by this employee"
                    + "\nbefore removing him...\n");
                alert.showAndWait();
            }
        }
        else {
           noSelectedItemAlert();
        }
    }
}
