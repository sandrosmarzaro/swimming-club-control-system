package com.sccs.controller;

import com.sccs.model.dao.EnrollReportDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import com.sccs.model.domain.DayOfTheWeek;
import com.sccs.model.domain.EnrollReport;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ComboBox<DayOfTheWeek> dayCombo;
    @FXML
    private Button printButton;
    @FXML 
    private Button refreshButton;
    @FXML
    private Button clearButton;
    
    private List<EnrollReport> enrollReportList;
    private ObservableList<EnrollReport> enrollReportObservableList;
    private final DayOfTheWeek[] daysArray = DayOfTheWeek.values();
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        EnrollReportDAO.setConnection(connection);
        loadTable();
        loadDayCombo();
    }    
    
    @FXML
    public void loadTable() {
        
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        vacanciesColumn.setCellValueFactory(new PropertyValueFactory<>("vacancies"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        
        handleClickClear();
    }
    
    @FXML 
    public void loadDayCombo() {
        dayCombo.getItems().addAll(daysArray);
    }
    
    @FXML
    public Boolean isValidInput() {
        
        String errorMessage = "";
        if (dayCombo.getValue() == null ) {
            errorMessage += "Invalid Day Value\n";
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
           DayOfTheWeek day = dayCombo.getValue();
           enrollReportList = EnrollReportDAO.dayList(day);
           enrollReportObservableList = FXCollections.observableArrayList(enrollReportList);
           table.setItems(enrollReportObservableList);
       }
    }
    
    @FXML
    public void handleClickClear() {
        
        enrollReportList = EnrollReportDAO.allList();
        enrollReportObservableList = FXCollections.observableArrayList(enrollReportList);
        table.setItems(enrollReportObservableList);
    }
    
    @FXML
    public void handleClickPrint() {
        
        if (isValidInput()) {
            HashMap filter = new HashMap();
            filter.put("dayOfWeek", dayCombo.getValue().toString());
            URL url = getClass().getResource("/com/sccs/report/report.jasper");
            try {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filter, connection);
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(ReportControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
