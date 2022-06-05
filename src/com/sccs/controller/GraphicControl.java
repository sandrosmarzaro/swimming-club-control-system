package com.sccs.controller;

import com.sccs.model.dao.ClassroomDAO;
import com.sccs.model.dao.EnrollDAO;
import com.sccs.model.database.Database;
import com.sccs.model.database.SingletonDatabase;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GraphicControl implements Initializable {
    
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis classAxis;
    @FXML
    private NumberAxis countAxis;
    
    private ObservableList<String> classNamesObservableList;
    private List<String> classNamesList;
    
    private final Database database = SingletonDatabase.getDatabase("postgresql");
    private final Connection connection = database.connect();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ClassroomDAO.setConnection(connection);
        EnrollDAO.setConnection(connection);
        loadBarChart();
    }
    
    @FXML
    public void loadBarChart() {
        
        ClassroomDAO.list().stream().map((classroom) -> {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(classroom.getName());
            series.getData().add(new XYChart.Data<>(
                classroom.getName(),
                EnrollDAO.getCountStudentsByClass(classroom.getId())
            ));
            return series;
        }).forEachOrdered((series) -> {
            barChart.getData().add(series);
        });
    }
    
}
