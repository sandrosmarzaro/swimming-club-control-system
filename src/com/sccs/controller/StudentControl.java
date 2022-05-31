package com.sccs.controller;

import com.sccs.model.dao.StudentDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Student;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentControl implements Initializable {
    
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> cpfColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, LocalDate> ageColumn;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker ageDate;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    
    private ObservableList<Student> observableList;
    private List<Student> studentsList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        StudentDAO.setConnection(connection);
        loadTable();
        
        table.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectStudent(newValue));
    }    
    
    @FXML
    public void loadTable() {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        studentsList = StudentDAO.list();
        observableList = FXCollections.observableArrayList(studentsList);
        table.setItems(observableList);
    }
    
    @FXML
    public void selectStudent(Student student) {
        
        if (student != null) {
            cpfField.setText(student.getCpf());
            nameField.setText(student.getName());
            ageDate.setValue(student.getAge());
        }
        else {
            cpfField.setText("");
            nameField.setText("");
            ageDate.setValue(LocalDate.now());
        }
    }
    
    @FXML
    public void noSelectedItemAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Student not selected!");
        alert.setContentText("Choose a valid student from the list...");
        alert.showAndWait();
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (nameField.getText().isEmpty() || nameField.getText() == null) {
            errorMessage += "Invalid Name\n";
        }
        if (cpfField.getText().isEmpty() || cpfField.getText() == null) {
            errorMessage += "Invalid CPF\n";
        }
        
        if (ageDate.getValue() == null) {
            errorMessage += "Invalid Date\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding");
            alert.setHeaderText("Invalid Fields!");
            alert.setContentText("Enter all required fields to add an student...\n" + errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void handleClickAdd() {
        
        if(isValidInput()) {
            Student student = new Student (
                cpfField.getText(),
                nameField.getText(),
                ageDate.getValue()
            );
            StudentDAO.insert(student);
            loadTable();
        }
    }
    
    @FXML
    public void handleClickUpdate() {
        
        Student selectedStudent = table.getSelectionModel().getSelectedItem();
        if (isValidInput()) {
            if (selectedStudent != null) {
                Student student = new Student (
                    selectedStudent.getId(),
                    cpfField.getText(),
                    nameField.getText(),
                    ageDate.getValue()
                );
                StudentDAO.update(student);
                loadTable();
            }
            else {
                noSelectedItemAlert();
            }
        }
    }
    
    @FXML
    public void handleClickDelete() {
        
        Student student = table.getSelectionModel().getSelectedItem();
        if (student != null) {
            StudentDAO.delete(student);
            loadTable();
        }
        else {
            noSelectedItemAlert();
        }
    }
}
