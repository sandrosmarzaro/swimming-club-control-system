package com.sccs.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AppControl implements Initializable {

    @FXML
    private Button enrollButton;
    @FXML
    private Button studentButton;
    @FXML
    private Button teacherButton;
    @FXML
    private Button poolButton;
    @FXML
    private Button employeeButton;
    @FXML
    private Button graphicButton;
    @FXML
    private Button reportButton;
    @FXML
    private AnchorPane rightAnchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    public void handleClickEmployee() throws IOException {
        AnchorPane anchorPaneEmployee = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/sccs/view/EmployeeView.fxml"));
        rightAnchorPane.getChildren().setAll(anchorPaneEmployee);
    }
    
    @FXML
    public void handleClickTeacher() throws IOException {
        AnchorPane anchorPaneEmployee = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/sccs/view/TeacherView.fxml"));
        rightAnchorPane.getChildren().setAll(anchorPaneEmployee);
    }
    
    @FXML
    public void handleClickStudent() throws IOException {
        AnchorPane anchorPaneEmployee = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/sccs/view/StudentView.fxml"));
        rightAnchorPane.getChildren().setAll(anchorPaneEmployee);
    }
}
