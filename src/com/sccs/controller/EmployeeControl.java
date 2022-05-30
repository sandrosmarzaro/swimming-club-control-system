package com.sccs.controller;

import com.sccs.model.dao.EmployeeDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Employee;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeControl implements Initializable {
    
    @FXML
    TableView<Employee> tableView;
    @FXML
    TableColumn<Employee, Integer> columnId;
    @FXML
    TableColumn<Employee, String> columnCpf;
    @FXML
    TableColumn<Employee, String> columnName;
    @FXML
    TextField cpfText;
    @FXML
    TextField nameText;
    @FXML
    TextField loginText;
    @FXML
    PasswordField passwordText;
    
    ObservableList<Employee> observableList;
    List<Employee> employeeList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    
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
        
        employeeList = employeeDAO.list();
        observableList = FXCollections.observableArrayList(employeeList);
        tableView.setItems(observableList);
    }
    
    @FXML
    public void selectEmployee(Employee employee) {
        if (employee != null) {
            cpfText.setText(employee.getCpf());
            nameText.setText(employee.getName());
            loginText.setText(employee.getLogin());
            passwordText.setText(employee.getPassword());
        }
        else {
            cpfText.setText("");
            nameText.setText("");
            loginText.setText("");
            passwordText.setText("");
        }
    }
}
