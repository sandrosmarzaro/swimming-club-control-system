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
    Button enrollButton;
    @FXML
    Button studentButton;
    @FXML
    Button teacherButton;
    @FXML
    Button poolButton;
    @FXML
    Button employeeButton;
    @FXML
    Button graphicButton;
    @FXML
    Button reportButton;
    @FXML
    AnchorPane rightAnchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    public void handleClickEmployee() throws IOException {
        AnchorPane anchorPaneEmployee = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/sccs/view/EmployeeView.fxml"));
        rightAnchorPane.getChildren().setAll(anchorPaneEmployee);
    }
    
}
