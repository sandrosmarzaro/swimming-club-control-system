package com.sccs.controller;

import com.sccs.model.dao.SwimmingPoolDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.SwimmingPool;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PoolControl implements Initializable {
    
    @FXML
    private TableView<SwimmingPool> table;
    @FXML
    private TableColumn<SwimmingPool, Integer> idColumn;
    @FXML
    private TableColumn<SwimmingPool, String> nameColumn;
    @FXML
    private TableColumn<SwimmingPool, Integer> ageColumn;
    @FXML
    private TableColumn<SwimmingPool, Integer> maxColumn;
    @FXML
    private TableColumn<SwimmingPool, Double> widthColumn;
    @FXML
    private TableColumn<SwimmingPool, Double> lengthColumn;
    @FXML
    private TableColumn<SwimmingPool, Double> depthColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField lanesField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField lengthField;
    @FXML
    private TextField depthField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    
    private ObservableList<SwimmingPool> observableList;
    private List<SwimmingPool> poolList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SwimmingPoolDAO.setConnection(connection);
        loadTable();
        
        table.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectPool(newValue));
    }
    
    @FXML
    public void loadTable() {
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("averageAge"));
        maxColumn.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));
        widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        depthColumn.setCellValueFactory(new PropertyValueFactory<>("depth"));
        
        poolList = SwimmingPoolDAO.list();
        observableList = FXCollections.observableArrayList(poolList);
        table.setItems(observableList);
    }
    
    @FXML
    public void selectPool(SwimmingPool pool) {
        
        if (pool != null) {
            nameField.setText(pool.getName());
            ageField.setText(pool.getAverageAge().toString());
            maxField.setText(pool.getMaxCapacity().toString());
            lanesField.setText(pool.getLanesNumber().toString());
            widthField.setText(pool.getWidth().toString());
            lengthField.setText(pool.getLength().toString());
            depthField.setText(pool.getDepth().toString());
        }
        else {
            nameField.setText("");
            ageField.setText("");
            maxField.setText("");
            lanesField.setText("");
            widthField.setText("");
            lengthField.setText("");
            depthField.setText("");
        }
    }
    
    @FXML
    public void noSelectedItemAlert() {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Student not selected!");
        alert.setContentText("Choose a valid pool from the list...");
        alert.showAndWait();
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (nameField.getText().isEmpty() || nameField.getText() == null) {
            errorMessage += "Invalid Name\n";
        }
        if (ageField.getText().isEmpty() || ageField.getText() == null) {
            errorMessage += "Invalid Age\n";
        }
        if (maxField.getText().isEmpty() || maxField.getText() == null) {
            errorMessage += "Invalid Maximum Capacity\n";
        }
        if (lanesField.getText().isEmpty() || lanesField.getText() == null) {
            errorMessage += "Invalid Number of Lanes\n";
        }
        if (widthField.getText().isEmpty() || widthField.getText() == null) {
            errorMessage += "Invalid Width\n";
        }
        if (lengthField.getText().isEmpty() || lengthField.getText() == null) {
            errorMessage += "Invalid Lenght\n";
        }
        if (depthField.getText().isEmpty() || depthField.getText() == null) {
            errorMessage += "Invalid Depth\n";
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
            SwimmingPool pool = new SwimmingPool(
                nameField.getText(),
                Integer.valueOf(ageField.getText()),
                Integer.valueOf(maxField.getText()),
                Integer.valueOf(lanesField.getText()),
                Double.valueOf(widthField.getText()),
                Double.valueOf(lengthField.getText()),
                Double.valueOf(depthField.getText())
            );
            SwimmingPoolDAO.insert(pool);
            loadTable();
        }
    }
    
    @FXML
    public void handleClickUpdate() {
        
        SwimmingPool selectedPool = table.getSelectionModel().getSelectedItem();
        if (isValidInput()) {
            if (selectedPool != null) {
                SwimmingPool pool = new SwimmingPool(
                    selectedPool.getNumber(),
                    nameField.getText(),
                    Integer.valueOf(ageField.getText()),
                    Integer.valueOf(maxField.getText()),
                    Integer.valueOf(lanesField.getText()),
                    Double.valueOf(widthField.getText()),
                    Double.valueOf(lengthField.getText()),
                    Double.valueOf(depthField.getText())
                );
                SwimmingPoolDAO.update(pool);
                loadTable();
            }
            else {
                noSelectedItemAlert();
            }
        }
    }
    
    @FXML
    public void handleClickDelete() {
        
        SwimmingPool pool = table.getSelectionModel().getSelectedItem();
        if (pool != null) {
            SwimmingPoolDAO.delete(pool);
            loadTable();
        }
        else {
            noSelectedItemAlert();
        }
    }
}
