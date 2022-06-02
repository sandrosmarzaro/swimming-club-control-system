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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableColumn<Classroom, DayOfTheWeek> dayColumn;
    @FXML
    private TextField nameField;
    @FXML
    private CheckBox openEnrollment;
    @FXML
    private ComboBox<Teacher> teacherCombo;
    @FXML
    private ComboBox<SwimmingPool> poolCombo;
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
    }
    
    @FXML
    public void loadTable() {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        openColumn.setCellValueFactory(new PropertyValueFactory<>("enrollmentOpen"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfTheWeek"));
        
        classList = ClassroomDAO.list();
        classObservableList = FXCollections.observableArrayList(classList);
        table.setItems(classObservableList);
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
}
