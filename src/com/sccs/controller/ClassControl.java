package com.sccs.controller;

import com.sccs.model.dao.ClassroomDAO;
import com.sccs.model.dao.SwimmingPoolDAO;
import com.sccs.model.dao.TeacherDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.Classroom;
import com.sccs.model.domain.DayOfTheWeek;
import com.sccs.model.domain.SwimmingPool;
import com.sccs.model.domain.Teacher;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClassControl implements Initializable {
    
    @FXML
    private TableView<Classroom> table;
    @FXML
    private TableColumn<Classroom, Integer> idColumn;
    @FXML
    private TableColumn<Classroom, String> nameColumn;
    @FXML
    private TableColumn<Classroom, Boolean> openColumn;
    @FXML
    private TableColumn<Classroom, Integer> vacanciesColumn;
    @FXML
    private TableColumn<Classroom, DayOfTheWeek> dayColumn;
    @FXML
    private TextField nameField;
    @FXML
    private ToggleButton openButton;
    @FXML
    private ComboBox<Teacher> teacherCombo;
    @FXML
    private ComboBox<SwimmingPool> poolCombo;
    @FXML 
    private ChoiceBox<DayOfTheWeek> dayChoice;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    
    private ObservableList<Classroom> classObservableList;
    private ObservableList<Teacher> teacherObservableList;
    private ObservableList<SwimmingPool> poolObservableList;
    
    private List<Classroom> classList;
    private List<Teacher> teacherList;
    private List<SwimmingPool> poolList;
    private final DayOfTheWeek[] daysArray = DayOfTheWeek.values();
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ClassroomDAO.setConnection(connection);
        TeacherDAO.setConnection(connection);
        SwimmingPoolDAO.setConnection(connection);
        
        loadTable();
        loadTeacherCombo();
        loadPoolCombo();
        loadDayChoice();
    }
    
    @FXML
    public void loadTable() {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        openColumn.setCellValueFactory(new PropertyValueFactory<>("enrollmentOpen"));
        vacanciesColumn.setCellValueFactory(new PropertyValueFactory<>("vacanciesNumber"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfTheWeek"));
        
        classList = ClassroomDAO.list();
        classObservableList = FXCollections.observableArrayList(classList);
        table.setItems(classObservableList);
        
        table.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectClass(newValue));
    }
    
    @FXML
    public void loadTeacherCombo() {
        
        teacherList = TeacherDAO.list();
        teacherObservableList = FXCollections.observableArrayList(teacherList);
        teacherCombo.setItems(teacherObservableList);
    }
    
    @FXML 
    public void loadPoolCombo() {
        
        poolList = SwimmingPoolDAO.list();
        poolObservableList = FXCollections.observableArrayList(poolList);
        poolCombo.setItems(poolObservableList);
    }
    
    @FXML
    public void loadDayChoice() {
        
        dayChoice.getItems().addAll(daysArray);
        SingleSelectionModel<DayOfTheWeek> singleSelectDay = dayChoice.getSelectionModel();
            singleSelectDay.select(DayOfTheWeek.SUNDAY);
            dayChoice.setSelectionModel(singleSelectDay);
    }
    
    @FXML
    public void selectClass(Classroom classroom) {
        
        if (classroom != null) {
            nameField.setText(classroom.getName());
            openButton.setSelected(classroom.getEnrollmentOpen());
            teacherCombo.setValue(TeacherDAO.search(classroom.getTeacherId()));
            poolCombo.setValue(SwimmingPoolDAO.search(classroom.getPoolId()));
            dayChoice.setValue(classroom.getDayOfTheWeek());
        }
        else {
            nameField.setText("");
            openButton.setSelected(false);
            teacherCombo.setValue(null);
            poolCombo.setValue(null);
            dayChoice.setValue(null);
        }
    }
    
    @FXML
    public void noSelectedItemAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Student not selected!");
        alert.setContentText("Choose a valid classroom from the list...");
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
        if (teacherCombo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Invalid Teacher\n";
        }
        if (poolCombo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Invalid Pool\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding");
            alert.setHeaderText("Invalid Fields!");
            alert.setContentText("Enter all required fields to add an pool...\n" + errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void handleClickAdd() {
        
        if (isValidInput()) {
            Classroom classroom = new Classroom(
                nameField.getText(),
                poolCombo.getSelectionModel().getSelectedItem().getNumber(),
                SwimmingPoolDAO.search(poolCombo
                    .getSelectionModel()
                    .getSelectedItem()
                    .getNumber())
                    .getMaxCapacity(),
                openButton.isSelected(),
                teacherCombo.getSelectionModel().getSelectedItem().getId(),
                dayChoice.getSelectionModel().getSelectedItem()
            );
            ClassroomDAO.insert(classroom);
            loadTable();
        }
    }
    
    @FXML
    public void handleClickUpdate() {
        
        Classroom selectedClassroom = table.getSelectionModel().getSelectedItem();
        if (isValidInput()) {
            if (selectedClassroom != null) {
                Classroom classroom = new Classroom(
                    selectedClassroom.getId(),
                    nameField.getText(),
                    poolCombo.getSelectionModel().getSelectedItem().getNumber(),
                        SwimmingPoolDAO.search(poolCombo
                        .getSelectionModel()
                        .getSelectedItem()
                        .getNumber())
                        .getMaxCapacity(),
                    openButton.isSelected(),
                    teacherCombo.getSelectionModel().getSelectedItem().getId(),
                    dayChoice.getSelectionModel().getSelectedItem()
                );
                ClassroomDAO.update(classroom);
                loadTable();
            }
            else {
                noSelectedItemAlert();
            }
        }
    }
    
    @FXML
    public void handleClickDelete() {
        
        Classroom classroom = table.getSelectionModel().getSelectedItem();
        if (classroom != null) {
            try {
                ClassroomDAO.delete(classroom);
                loadTable();
            }
            catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Deleting");
                alert.setHeaderText("Classroom with records!");
                alert.setContentText("Delete enrollments made by this classroom"
                    + "\nbefore removing him...\n");
                alert.showAndWait();
            }
        }
        else {
            noSelectedItemAlert();
        }
    }
}
