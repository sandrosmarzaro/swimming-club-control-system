package com.sccs.controller;

import com.sccs.model.dao.TeacherDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Teacher;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeacherControl implements Initializable {
    
    @FXML
    private TableView<Teacher> tableView;
    @FXML
    private TableColumn<Teacher, Integer> idColumn;
    @FXML
    private TableColumn<Teacher, String> cpfColumn;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField nameField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button addButton;
    
    private ObservableList<Teacher> observableList;
    private List<Teacher> teacherList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TeacherDAO.setConnection(connection);
        loadTableView();
        
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectTeacher(newValue));
    }

    @FXML
    public void loadTableView() {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        teacherList = TeacherDAO.list();
        observableList = FXCollections.observableArrayList(teacherList);
        tableView.setItems(observableList);
    }
    
    @FXML
    public void selectTeacher(Teacher teacher) {
        
        if (teacher != null) {
            cpfField.setText(teacher.getCpf());
            nameField.setText(teacher.getName());
        }
        else {
            cpfField.setText("");
            nameField.setText("");
        }
    }
    
    @FXML
    public void noSelectedItemAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Teacher not selected!");
        alert.setContentText("Choose a valid teacher from the list...");
        alert.showAndWait();
    }
    
    @FXML
    public void duplicateCPFAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("CPF Error");
        alert.setHeaderText("Duplicate CPF!");
        alert.setContentText("Repeated CPF, enter another...");
        alert.showAndWait();
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (
            nameField.getText().isEmpty() || 
            nameField.getText() == null ||
            nameField.getText().length() > 45
        ) {
            errorMessage += "Invalid Name\n";
        }
        if (
            cpfField.getText().isEmpty() ||
            cpfField.getText() == null ||
            !Pattern.matches("^[0-9]{11}$", cpfField.getText())
        ) {
            errorMessage += "Invalid CPF\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding");
            alert.setHeaderText("Invalid Fields!");
            alert.setContentText("Enter all required fields to add an teacher...\n" + errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void handleClickAdd() {
        
        if (isValidInput()) {
            Teacher teacher = new Teacher(
                cpfField.getText(),
                nameField.getText()
            );
            try {
                TeacherDAO.insert(teacher);
                loadTableView();
            }
            catch (SQLException ex) {
                duplicateCPFAlert();
            }
        }
    }
    
    @FXML
    public void handleClickUpdate() {
        
        Teacher selectedTeacher = tableView.getSelectionModel().getSelectedItem();
        if (isValidInput()) {
            if(selectedTeacher != null) {
                Teacher teacher = new Teacher(
                    selectedTeacher.getId(),
                    cpfField.getText(),
                    nameField.getText()
                );
                try {
                    TeacherDAO.update(teacher);
                    loadTableView();
                }
                catch (SQLException ex) {
                    duplicateCPFAlert();
                }
            }
            else {
                noSelectedItemAlert();
            }
        }
    }
    
    @FXML
    public void handleClickDelete() {
        
        Teacher teacher = tableView.getSelectionModel().getSelectedItem();
        if (teacher != null) {
            try {
                TeacherDAO.delete(teacher);
                loadTableView();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Deleting");
                alert.setHeaderText("Teacher with records!");
                alert.setContentText("Delete enrollments made by this teacher"
                    + "\nbefore removing him...\n");
                alert.showAndWait();
            }
        }
        else {
            noSelectedItemAlert();
        }
    }
}
