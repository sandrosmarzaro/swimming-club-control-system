package com.sccs.controller;

import com.sccs.model.dao.ClassroomDAO;
import com.sccs.model.dao.EmployeeDAO;
import com.sccs.model.dao.EnrollDAO;
import com.sccs.model.dao.EnrollTableDAO;
import com.sccs.model.dao.StudentDAO;
import com.sccs.model.dao.SwimmingPoolDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Classroom;
import com.sccs.model.domain.Employee;
import com.sccs.model.domain.Enroll;
import com.sccs.model.domain.EnrollTable;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EnrollControl implements Initializable {
    
    @FXML
    TableView<EnrollTable> table;
    @FXML
    TableColumn<EnrollTable, Integer> idColumn;
    @FXML
    TableColumn<EnrollTable, String> studentNameColumn;
    @FXML
    TableColumn<EnrollTable, String> classNameColumn;
    @FXML
    TableColumn<EnrollTable, String> employeeNameColumn;
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
    
    List<EnrollTable> enrollTableList;
    List<Student> studentList;
    List<Classroom> classList;
    List<Employee> employeeList;
    
    ObservableList<EnrollTable> enrollTableObservableList;
    ObservableList<Student> studentObservableList;
    ObservableList<Classroom> classObservableList;
    ObservableList<Employee> employeeObservableList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        EnrollTableDAO.setConnection(connection);
        EnrollDAO.setConnection(connection);
        StudentDAO.setConnection(connection);
        ClassroomDAO.setConnection(connection);
        EmployeeDAO.setConnection(connection);
        SwimmingPoolDAO.setConnection(connection);
        
        loadTable();
        loadStudentCombo();
        loadClassCombo();
        loadEmployeeCombo();
        
        table.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectEnroll(newValue));
    }    
    
    @FXML
    public void loadTable() {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("enrollId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        
        enrollTableList = EnrollTableDAO.list();
        enrollTableObservableList = FXCollections.observableArrayList(enrollTableList);
        table.setItems(enrollTableObservableList);
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
    public void selectEnroll(EnrollTable enrollTable) {
        
        if (enrollTable != null) {
            studentCombo.setValue(StudentDAO.search(EnrollDAO.search(enrollTable.getEnrollId()).getStudentId()));
            classCombo.setValue(ClassroomDAO.search(EnrollDAO.search(enrollTable.getEnrollId()).getClassId()));
            employeeCombo.setValue(EmployeeDAO.idSearch(EnrollDAO.search(enrollTable.getEnrollId()).getEmployeeId()));
        }
        else {
            studentCombo.setValue(null);
            classCombo.setValue(null);
            employeeCombo.setValue(null);
        }
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
    public void noVacanciesAvailableAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Adding");
        alert.setHeaderText("No vacancies!");
        String contentMessage = "There are no vacancies available for this class\n"
            + "choose another one...";
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }
    
    @FXML
    public void ageExceedsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Adding");
        alert.setHeaderText("Selected student age is not allowed!");
        String contentMessage = "The age exceeds the limits of the selected class\n" +
            "select another class...";
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }
    
    @FXML
    public void closeClassAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Adding");
        alert.setHeaderText("Closed class!");
        String contentMessage = "The class is not open for enrollment\n" +
            "select another class...";
        alert.setContentText(contentMessage);
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
                    classCombo.getValue().getPoolId()
                )
                .getAverageAge()
            );
            if (isNotAboveAverage) {
                if (classCombo.getValue().getEnrollmentOpen()) {
                    if (ClassroomDAO.hasVacancy(classCombo.getValue().getId())) {
                        EnrollDAO.insert(enroll);
                        ClassroomDAO.decreaseVacancies(enroll.getClassId());
                        loadTable();
                    }
                    else {
                        noVacanciesAvailableAlert(); 
                    }
                }
                else {
                    closeClassAlert();
                }
            }
            else {
                ageExceedsAlert();
            }
        }
    }
    
    @FXML
    public void handleClickUpdate() {
        
        EnrollTable selectedEnroll = table.getSelectionModel().getSelectedItem();
        if (isValidInput()) {
            if (selectedEnroll != null) {
                Enroll enroll = new Enroll (
                    selectedEnroll.getEnrollId(),
                    classCombo.getValue().getId(),
                    employeeCombo.getValue().getId(),
                    studentCombo.getValue().getId()
                );
                Boolean isNotAboveAverage = EnrollDAO.isntAboveAverage(
                    classCombo.getValue().getId(),
                    studentCombo.getValue().getAge(),
                    SwimmingPoolDAO.search(classCombo.getValue().getPoolId()).getAverageAge()
                );
                if (isNotAboveAverage) {
                    if (enroll.getClassId() != EnrollDAO.search(enroll.getId()).getClassId()) {
                        if (classCombo.getValue().getEnrollmentOpen()) {
                            if (ClassroomDAO.hasVacancy(enroll.getClassId())) {
                                ClassroomDAO.decreaseVacancies(enroll.getClassId());
                                EnrollDAO.update(enroll);
                                loadTable();
                            }
                            else {
                                noVacanciesAvailableAlert();
                            }
                        }
                        else {
                            closeClassAlert();
                        }
                    }
                    else {
                        EnrollDAO.update(enroll);
                        loadTable();
                    }
                }
                else {
                    ageExceedsAlert();
                }
            }
            else {
                noSelectedItemAlert();
            }
        }
    }
    
    @FXML
    public void handleClickDelete() {
        
        EnrollTable selectedEnroll = table.getSelectionModel().getSelectedItem();
        if (selectedEnroll != null) {
            EnrollDAO.delete(EnrollDAO.search(selectedEnroll.getEnrollId()));
            ClassroomDAO.increaseVacancies(classCombo.getValue().getId());
            loadTable();
        }
        else {
            noSelectedItemAlert();
        }
    }
}
