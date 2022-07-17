package com.sccs.controller;

import com.sccs.model.domain.ConnectionRunnable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ConnectionControl implements Initializable {
    
    @FXML
    private Label numberLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Thread whileThread = new Thread(() -> {
            while (true) {
                try {
                    Thread requestNumberThread = new Thread(new ConnectionRunnable(numberLabel));
                    requestNumberThread.start();
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        whileThread.start();
    }
}
