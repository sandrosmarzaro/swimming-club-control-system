package com.sccs.controller;

import com.sccs.model.dao.EnrollReportDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.DayOfTheWeek;
import com.sccs.model.domain.EnrollReport;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReportControl implements Initializable {
    
    @FXML
    private TableView<EnrollReport>  table;
    @FXML
    private TableColumn<EnrollReport, Integer>  quantityColumn;
    @FXML
    private TableColumn<EnrollReport, Integer>  vacanciesColumn;
    @FXML
    private TableColumn<EnrollReport, Double>  averageColumn;
    @FXML
    private TableColumn<EnrollReport, DayOfTheWeek>  dayColumn;
    @FXML
    private Spinner<Integer> minSpinner = new Spinner<>();
    private Integer currentMin = 0;
    @FXML
    private Spinner<Integer> maxSpinner = new Spinner<>();
    private Integer currentMax = 100;
    @FXML
    Button printButton;
    @FXML 
    Button refreshButton;
    
    private SpinnerValueFactory<Integer> spinnerFactoryMin = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
    private SpinnerValueFactory<Integer> spinnerFactoryMax = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
    
    private List<EnrollReport> enrollReportList;
    private ObservableList<EnrollReport> enrollReportObservableList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        EnrollReportDAO.setConnection(connection);
        loadTable(0, 100);
        loadsSpinners();
    }    
    
    @FXML
    public void loadTable(Integer minAge, Integer maxAge) {
        
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        vacanciesColumn.setCellValueFactory(new PropertyValueFactory<>("vacancies"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        
        enrollReportList = EnrollReportDAO.list(minAge, maxAge);
        enrollReportObservableList = FXCollections.observableArrayList(enrollReportList);
        table.setItems(enrollReportObservableList);
    }
    
    @FXML
    public void loadsSpinners() {
        
        spinnerFactoryMin.setValue(0);
        minSpinner.setValueFactory(spinnerFactoryMin);
        spinnerFactoryMax.setValue(100);
        maxSpinner.setValueFactory(spinnerFactoryMax);
        
        minSpinner.valueProperty().addListener(
            (ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                currentMin = minSpinner.getValue();
        });
        maxSpinner.valueProperty().addListener(
            (ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                currentMax = maxSpinner.getValue();
        });
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (minSpinner == null || minSpinner.getValue() > maxSpinner.getValue()) {
            errorMessage += "Invalid Min Value\n";
        }
        if (maxSpinner == null || maxSpinner.getValue() < minSpinner.getValue()) {
            errorMessage += "Invalid Max Value\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Printer");
            alert.setHeaderText("Invalid Fields!");
            alert.setContentText("Enter all required fields to print...\n" + errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void handleClickRefresh() {
        
        if (isValidInput()) {
            loadTable(minSpinner.getValue(), maxSpinner.getValue());
        }
    }
    
    @FXML
    public void handleClickPrint() {
        
        if (isValidInput()) {
            URL url = getClass().getResource("/com/sccs/report/sccs-report.jasper");
            try {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(ReportControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
