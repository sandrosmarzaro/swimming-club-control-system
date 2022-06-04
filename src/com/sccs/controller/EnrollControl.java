package com.sccs.controller;

import com.sccs.model.dao.ClassroomDAO;
import com.sccs.model.dao.EmployeeDAO;
import com.sccs.model.dao.EnrollDAO;
import com.sccs.model.dao.StudentDAO;
import com.sccs.model.dao.SwimmingPoolDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Classroom;
import com.sccs.model.domain.Employee;
import com.sccs.model.domain.Enroll;
import com.sccs.model.domain.Student;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class EnrollControl implements Initializable {

    @FXML
    private ComboBox<Student> studentCombo;
    @FXML
    private ComboBox<Classroom> classCombo;
    @FXML
    private ComboBox<Employee> employeeCombo;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    
    List<Student> studentList;
    List<Classroom> classList;
    List<Employee> employeeList;
    
    ObservableList<Student> studentObservableList;
    ObservableList<Classroom> classObservableList;
    ObservableList<Employee> employeeObservableList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        StudentDAO.setConnection(connection);
        ClassroomDAO.setConnection(connection);
        EmployeeDAO.setConnection(connection);
        loadStudentCombo();
        loadClassCombo();
        loadEmployeeCombo();
    }    
    
    @FXML
    public void loadStudentCombo() {
        
        studentList = StudentDAO.list();
        studentObservableList = FXCollections.observableArrayList(studentList);
        studentCombo.setItems(studentObservableList);
    }
    
    @FXML
    public void loadClassCombo() {
        
        classList = ClassroomDAO.list();
        classObservableList = FXCollections.observableArrayList(classList);
        classCombo.setItems(classObservableList);
    }
    
    @FXML
    public void loadEmployeeCombo() {
        
        employeeList = EmployeeDAO.list();
        employeeObservableList = FXCollections.observableArrayList(employeeList);
        employeeCombo.setItems(employeeObservableList);
    }
    
    @FXML
    public void noSelectedItemAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Student not selected!");
        alert.setContentText("Choose a valid enroll from the list...");
        alert.showAndWait();
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (studentCombo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Invalid Student\n";
        }
        if (classCombo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Invalid Class\n";
        }
        if (employeeCombo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Invalid Employee\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding");
            alert.setHeaderText("Invalid Fields!");
            alert.setContentText("Enter all required fields to add an enroll...\n" + errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void handleClickAdd() {
        
        if(isValidInput()) {
            Enroll enroll = new Enroll(
                classCombo.getValue().getId(),
                employeeCombo.getValue().getId(),
                studentCombo.getValue().getId()
            );
            Boolean isNotAboveAverage = EnrollDAO.isntAboveAverage(
                enroll.getClassId(), 
                studentCombo.getValue().getAge(),
                SwimmingPoolDAO.search (
                    classCombo
                        .getValue()
                        .getPoolId()
                )
                .getAverageAge()
            );
            if (isNotAboveAverage) {
                EnrollDAO.insert(enroll);
                ClassroomDAO.updateVacancies(enroll.getClassId());
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Adding");
                alert.setHeaderText("Selected student age is not allowed!");
                String contentMessage = "The age exceeds the limits of the selected class\n" +
                    "select another class...";
                alert.setContentText(contentMessage);
                alert.showAndWait();
            }
        }
    }
}
